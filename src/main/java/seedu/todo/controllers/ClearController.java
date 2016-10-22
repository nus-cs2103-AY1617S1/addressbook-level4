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
    private static final String COMMAND_SYNTAX = "clear [task/event] on [date]";
    private static final String COMMAND_WORD = "clear";
    private static final String MESSAGE_CLEAR_NO_ITEM_FOUND = "No item found!";
    private static final String MESSAGE_CLEAR_SUCCESS = "A total of %s deleted!\n" + "To undo, type \"undo\".";;
    private String invalidDate = null;
    
    private static CommandDefinition commandDefinition =
            new CommandDefinition(NAME, DESCRIPTION, COMMAND_SYNTAX); 

    public static CommandDefinition getCommandDefinition() {
        return commandDefinition;
    }

    @Override
    public float inputConfidence(String input) {
        return (input.toLowerCase().startsWith(COMMAND_WORD)) ? 1 : 0;
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
        tokenDefinitions.put("eventType", new String[] { "event", "events", "task", "tasks" });
        tokenDefinitions.put("time", new String[] { "at", "by", "on", "before", "time" });
        tokenDefinitions.put("timeFrom", new String[] { "from" });
        tokenDefinitions.put("timeTo", new String[] { "to", "until" });
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
        
        // Task or event?
        boolean deleteAll = parseDeleteAllType(parsedResult);
        
        boolean isTask = true; //default
        //if listing all type , set isTask and isEvent true
        if (!deleteAll) {
            isTask = parseIsTask(parsedResult);
        }
        
        //no dates provided
        if (parsedDates == null && parseExactClearCommand(parsedResult) && deleteAll) {
            destroyAll(db);
            return;
        } else {
            if (deleteAll && !parseExactClearCommand(parsedResult) && parsedDates == null) { //no item type and date provided
                LocalDateTime date = parseDateWithNoKeyword(parsedResult);
                if (date == null) {
                    displayErrorMessage(input, parsedDates, deleteAll, isTask);
                    return;
                } 
            }
        }
        
        LocalDateTime dateOn = parseDateWithNoKeyword(parsedResult);
        LocalDateTime dateFrom = null;
        LocalDateTime dateTo = null;
        if (parsedDates != null) {
            String naturalOn = parsedDates[0];
            String naturalFrom = parsedDates[1];
            String naturalTo = parsedDates[2];
            // if all are null = no date provided
            
            // Parse natural date using Natty.
            dateOn = naturalOn == null ? null : parseNatural(naturalOn);
            dateFrom = naturalFrom == null ? null : parseNatural(naturalFrom); 
            dateTo = naturalTo == null ? null : parseNatural(naturalTo);
        }
        
        destroyByDate(db, parsedDates, dateOn, dateFrom, dateTo, deleteAll, isTask, input);

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
            LocalDateTime dateFrom, LocalDateTime dateTo, boolean deleteAll,
            boolean isTask, String input) {
        if (dateOn == null && dateFrom == null && dateTo == null && deleteAll) {
            displayErrorMessage(input, parsedDate, deleteAll, isTask);
        }
        else if (dateOn != null) {
            destroyBySelectedDate(db, dateOn, deleteAll, isTask);
            return;
        } else {
            if (!deleteAll && parsedDate != null && dateFrom == null && dateTo == null && dateOn == null) { //date provided is invalid
                displayErrorMessage(input, parsedDate, deleteAll, isTask);
                return;
            } else {
                if (parsedDate != null) {
                    if (parsedDate[1] != null && parsedDate[2] != null && (dateFrom == null || dateTo == null)) {
                        displayErrorMessage(input, parsedDate, deleteAll, isTask);
                        return;
                    }
                }
                destroyByRange(db, dateFrom, dateTo, deleteAll, isTask);
                return;
            }
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
    private void destroyByRange(TodoListDB db, LocalDateTime dateFrom, LocalDateTime dateTo, 
            boolean deleteAll, boolean isTask) {
        if (dateFrom == null) {
            dateFrom = LocalDateTime.MIN;
        } 
        
        if (dateTo == null) {
            dateTo = LocalDateTime.MAX;
        }
        
        int numTasks = db.getTaskByRange(dateFrom, dateTo).size();
        int numEvents = db.getEventByRange(dateFrom, dateTo).size();
        if (numTasks == 0 && numEvents == 0) {
            Renderer.renderIndex(db, MESSAGE_CLEAR_NO_ITEM_FOUND);
            return;
        }
        
        if (deleteAll) {
            db.destroyAllEventByRange(dateFrom, dateTo);
            db.destroyAllTaskByRange(dateFrom, dateTo);
        } else if (isTask) {
            if (numTasks == 0) {
                Renderer.renderIndex(db, MESSAGE_CLEAR_NO_ITEM_FOUND);
                return;
            }
            db.destroyAllTaskByRange(dateFrom, dateTo);
            numEvents = 0;
        } else {
            if (numEvents == 0) {
                Renderer.renderIndex(db, MESSAGE_CLEAR_NO_ITEM_FOUND);
                return;
            }
            db.destroyAllEventByRange(dateFrom, dateTo);
            numTasks = 0;
        }
        db.save();
        Renderer.renderIndex(db, String.format(MESSAGE_CLEAR_SUCCESS, displaySuccessMessage(numTasks, numEvents)));
    }
    
    private String displaySuccessMessage (int numTasks, int numEvents) {
        if (numTasks != 0 && numEvents != 0) {
            return String.format("%s and %s", formatTaskMessage(numTasks), formatEventMessage(numEvents));
        } else if (numTasks != 0) {
            return formatTaskMessage(numTasks);
        } else {
            return formatEventMessage(numEvents);
        }
    }
    
    private String formatEventMessage (int numEvents) {
        return String.format("%d %s", numEvents, StringUtil.pluralizer(numEvents, "event", "events"));
    }
    
    private String formatTaskMessage (int numTasks) {
        return String.format("%d %s", numTasks, StringUtil.pluralizer(numTasks, "task", "tasks"));
    }

    /**
     * display error message due to invalid clear command
     * 
     * @param input
     *            based on user input
     * @param parsedDate            
     *            the date entered by the user      
     */
    private void displayErrorMessage(String input, String[] parsedDate, boolean deleteAll, boolean isTask) {
        String consoleDisplayMessage = String.format("You have entered : %s.",input);
        String commandLineMessage = COMMAND_WORD;
        if (!deleteAll) {
            if (isTask) {
                commandLineMessage = String.format("%s %s", commandLineMessage, "task");
            } else {
                commandLineMessage = String.format("%s %s", commandLineMessage, "event");
            }
        }
        if (parsedDate != null) {
            if (parsedDate[0] != null) {
                commandLineMessage = String.format("%s by <date>", commandLineMessage);
            } else if (parsedDate[1] != null && parsedDate[2] != null) {
                commandLineMessage = String.format("%s from <date> to <date>", commandLineMessage);
            } else if (parsedDate[1] != null) {
                commandLineMessage = String.format("%s from <date>", commandLineMessage);
            } else {
                commandLineMessage = String.format("%s to <date>", commandLineMessage);
            }
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
    private void destroyBySelectedDate(TodoListDB db, LocalDateTime givenDate, boolean deleteAll, boolean isTask) {
        int numTasks = db.getTaskByDate(givenDate).size();
        int numEvents = db.getEventByDate(givenDate).size();
        if (numTasks == 0 && numEvents == 0) {
            Renderer.renderIndex(db, MESSAGE_CLEAR_NO_ITEM_FOUND);
            return;
        }
        
        if (deleteAll) {
            db.destroyAllEventByDate(givenDate);
            db.destroyAllTaskByDate(givenDate);
        } else if (isTask) {
            if (numTasks == 0) {
                Renderer.renderIndex(db, MESSAGE_CLEAR_NO_ITEM_FOUND);
                return;
            }
            db.destroyAllTaskByDate(givenDate);
            numEvents = 0;
        } else {
            if (numEvents == 0) {
                Renderer.renderIndex(db, MESSAGE_CLEAR_NO_ITEM_FOUND);
                return;
            }
            db.destroyAllEventByDate(givenDate);
            numTasks = 0;
        }
        db.save();
        Renderer.renderIndex(db, String.format(MESSAGE_CLEAR_SUCCESS, displaySuccessMessage(numTasks, numEvents)));
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
        db.save();
        Renderer.renderIndex(db, String.format(MESSAGE_CLEAR_SUCCESS, totalCalendarItems));
    }
    
    private boolean parseExactClearCommand(Map<String, String[]> parsedResult) {
        return parsedResult.get("default")[1] == null;
    }
    
    private LocalDateTime parseDateWithNoKeyword(Map<String, String[]> parsedResult) {
        if (parsedResult.get("default").length == 2) { // user enter more than 1 date with no keyword
            if (parsedResult.get("default")[1] != null) {
                return parseNatural(parsedResult.get("default")[1]);
            } else {
                return null;
            }
        } else {
            return null;
        }
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
    
    /**
     * Extracts the intended CalendarItem type specify from parsedResult.
     * 
     * @param parsedResult
     * @return true if Task or event is not specify, false if either Task or Event specify
     */
    private boolean parseDeleteAllType (Map<String, String[]> parsedResult) {
        return !(parsedResult.get("eventType") != null);
    }
    
    /**
     * Extracts the intended CalendarItem type from parsedResult.
     * 
     * @param parsedResult
     * @return true if Task, false if Event
     */
    private boolean parseIsTask (Map<String, String[]> parsedResult) {
        return parsedResult.get("eventType")[0].contains("task");
    }

}
