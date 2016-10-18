package seedu.todo.controllers;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.joestelmach.natty.DateGroup;
import com.joestelmach.natty.Parser;

import seedu.todo.commons.exceptions.UnmatchedQuotesException;
import seedu.todo.commons.util.DateUtil;
import seedu.todo.commons.util.StringUtil;
import seedu.todo.controllers.concerns.Renderer;
import seedu.todo.controllers.concerns.Tokenizer;
import seedu.todo.models.TodoListDB;

public class ClearController implements Controller {
    
    private static final String NAME = "Clear";
    private static final String DESCRIPTION = "Clear all tasks/events or by specify date.";
    private static final String COMMAND_SYNTAX = "clear";
    
    private static final String MESSAGE_CLEAR_SUCCESS = "A total of %s tasks and events have been deleted!";
    private String invalidDate = null;
    
    private static CommandDefinition commandDefinition =
            new CommandDefinition(NAME, DESCRIPTION, COMMAND_SYNTAX); 

    public static CommandDefinition getCommandDefinition() {
        return commandDefinition;
    }

    @Override
    public float inputConfidence(String input) {
        return (input.toLowerCase().startsWith(COMMAND_SYNTAX)) ? 1 : 0;
    }
    

    /**
     * Get the token definitions for use with <code>tokenizer</code>.<br>
     * This method exists primarily because Java does not support HashMap
     * literals...
     * 
     * @return tokenDefinitions
     */
    private static Map<String, String[]> getTokenDefinitions() {
        Map<String, String[]> tokenDefinitions = new HashMap<String, String[]>();
        tokenDefinitions.put("default", new String[] {"clear"});
        tokenDefinitions.put("eventType", new String[] { "event", "task" });
        tokenDefinitions.put("time", new String[] { "at", "by", "on", "before", "time" });
        tokenDefinitions.put("timeFrom", new String[] { "from" });
        tokenDefinitions.put("timeTo", new String[] { "to" });
        return tokenDefinitions;
    }
    
    @Override
    public void process(String input) {
        TodoListDB db = TodoListDB.getInstance();

        Map<String, String[]> parsedResult;
        try {
            parsedResult = Tokenizer.tokenize(getTokenDefinitions(), input);
        } catch (UnmatchedQuotesException e) {
            System.out.println("Unmatched quote!");
            return ;
        }
        
        String[] parsedDates = parseDates(parsedResult);
        
        //no dates provided
        if (parsedDates == null && parseExactClearCommand(parsedResult)) {
            destroyAll(db);
            return;
        } else {
            displayErrorMessage(input, parsedDates);
        }
        
        String naturalOn = parsedDates[0];
        String naturalFrom = parsedDates[1];
        String naturalTo = parsedDates[2];
        // if all are null = no date provided
        
        // Parse natural date using Natty.
        LocalDateTime dateOn = naturalOn == null ? null : parseNatural(naturalOn); 
        LocalDateTime dateFrom = naturalFrom == null ? null : parseNatural(naturalFrom); 
        LocalDateTime dateTo = naturalTo == null ? null : parseNatural(naturalTo);
        
        destroyByDate(db, parsedDates, dateOn, dateFrom, dateTo, input);

    }

    /**
     * Clear all tasks and events by a single or a range of date that exist in the database.
     * 
     * @param db
     *            TodoListDB object
     * @param naturalOn, naturalFrom, naturalTo
     *            null if not entered, or natural date that user has entered
     * @param dateOn, dateFrom
     *            null if parsing failed or Due date for Task or start date for Event
     * @param dateTo
     *            null if parsing failed or End date for Event
     */
    private void destroyByDate(TodoListDB db, String[] parsedDate, LocalDateTime dateOn, 
            LocalDateTime dateFrom, LocalDateTime dateTo, String input) {
        if (dateOn != null) {
            destroyByDate(db, dateOn);
            return;
        } else if (dateFrom != null || dateTo != null) {
            destroyByRange(db, dateFrom, dateTo);
            return;
        } else { //natty deem all dates as invalid
            displayErrorMessage(input, parsedDate);
        }
    }

    /**
     * clear all tasks and events of given date range that exist in the database.
     * 
     * @param TodoListDB
     * @param dateFrom
     *            null if parsing failed or Due date for Task or start date for Event
     * @param dateTo
     *            null if parsing failed or End date for Event
     */
    private void destroyByRange(TodoListDB db, LocalDateTime dateFrom, LocalDateTime dateTo) {
        int totalCalendarItems = db.getEventByRange(dateFrom, dateTo).size() + db.getTaskByRange(dateFrom, dateTo).size();
        db.destroyAllEventByRange(dateFrom, dateTo);
        db.destroyAllTaskByRange(dateFrom, dateTo);
        Renderer.renderIndex(db, String.format(MESSAGE_CLEAR_SUCCESS, totalCalendarItems));
        
    }

    /**
     * display error message due to invalid clear command
     * 
     * @param input
     *            based on user input
     * @param parsedDate            
     *            the date entered by the user      
     */
    private void displayErrorMessage(String input, String[] parsedDate) {
        String consoleDisplayMessage = String.format("You have entered : %s.",input);
        String commandLineMessage;
        if (parsedDate == null) {
            commandLineMessage = COMMAND_SYNTAX;
        } else if (parsedDate[0] != null) {
            commandLineMessage = String.format("%s by <date>", COMMAND_SYNTAX);
        } else if (parsedDate[1] != null && parsedDate[2] != null) {
            commandLineMessage = String.format("%s from <date> to <date>", COMMAND_SYNTAX);
        } else if (parsedDate[1] != null) {
            commandLineMessage = String.format("%s from <date>", COMMAND_SYNTAX);
        } else {
            commandLineMessage = String.format("%s to <date>", COMMAND_SYNTAX);
        }
        Renderer.renderDisambiguation(commandLineMessage, consoleDisplayMessage);
    }
    
    /**
     * clear all tasks and events of the date that exist in the database.
     * 
     * @param TodoListDB
     * @param givenDate
     *            null if parsing failed or Due date for Task or start date for Event
     */
    private void destroyByDate(TodoListDB db, LocalDateTime givenDate) {
        int totalCalendarItems = db.getEventByDate(givenDate).size() + db.getTaskByDate(givenDate).size();
        db.destroyAllEventByDate(givenDate);
        db.destroyAllTaskByDate(givenDate);
        Renderer.renderIndex(db, String.format(MESSAGE_CLEAR_SUCCESS, totalCalendarItems));
    }

    /**
     * clear all tasks and events that exist in the database.
     * 
     * @param TodoListDB
     */
    private void destroyAll(TodoListDB db) {
        int totalCalendarItems = db.getAllEvents().size() + db.getAllTasks().size();
        db.destroyAllEvent();
        db.destroyAllTask();
        Renderer.renderIndex(db, String.format(MESSAGE_CLEAR_SUCCESS, totalCalendarItems));
    }
    
    private boolean parseExactClearCommand(Map<String, String[]> parsedResult) {
        return parsedResult.get("default")[1] == null;
    }
    
    /**
     * Parse a natural date into a LocalDateTime object.
     * 
     * @param natural
     * @return LocalDateTime object
     */
    private LocalDateTime parseNatural(String natural) {
        Parser parser = new Parser();
        List<DateGroup> groups = parser.parse(natural);
        Date date = null;
        try {
            date = groups.get(0).getDates().get(0);
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Error!"); // TODO
            if (invalidDate != null) {
                invalidDate = String.format("from %s to %s", invalidDate, natural);
            } else {
                invalidDate = natural;
            }
            return null;
        }
        LocalDateTime ldt = LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
        return DateUtil.floorDate(ldt);
    }
    
    /**
     * Extracts the natural dates from parsedResult.
     * 
     * @param parsedResult
     * @return { naturalOn, naturalFrom, naturalTo } or null if no date provided
     */
    private String[] parseDates(Map<String, String[]> parsedResult) {
        String naturalFrom = null;
        String naturalTo = null;
        String naturalOn = null;
        
        if (parsedResult.get("time") == null) {
            if (parsedResult.get("timeFrom") != null) {
                naturalFrom = parsedResult.get("timeFrom")[1];
            }
            if (parsedResult.get("timeTo") != null) {
                naturalTo = parsedResult.get("timeTo")[1];
            }
        } else {
            naturalOn = parsedResult.get("time")[1];
        }
        
        if (naturalFrom != null || naturalTo != null || naturalOn != null) {
            return new String[] { naturalOn, naturalFrom, naturalTo };
        } else {
            return null;
        }
    }

}
