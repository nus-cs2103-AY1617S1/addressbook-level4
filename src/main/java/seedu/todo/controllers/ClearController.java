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

import seedu.todo.commons.exceptions.ParseException;
import seedu.todo.commons.util.DateUtil;
import seedu.todo.commons.util.StringUtil;
import seedu.todo.controllers.concerns.Renderer;
import seedu.todo.controllers.concerns.Tokenizer;
import seedu.todo.models.TodoListDB;

/**
 * Controller to clear task/event by type or status
 * 
 * @@author Tiong YaoCong A0139922Y
 *
 */
public class ClearController implements Controller {
    
    private static final String NAME = "Clear";
    private static final String DESCRIPTION = "Clear all tasks/events or by specify date.";
    private static final String COMMAND_SYNTAX = "clear [task/event] [on date]";
    private static final String COMMAND_WORD = "clear";
    private static final String MESSAGE_CLEAR_NO_ITEM_FOUND = "No item found!";
    private static final String MESSAGE_CLEAR_SUCCESS = "A total of %s deleted!\n" + "To undo, type \"undo\".";

    //Use by array access
    private static final int KEYWORD = 0;
    private static final int RESULT = 1;
    private static final int MAXIMUM_SIZE = 2;
    //Use by accessing date value
    private static final int INDEX_DATE_ON = 0;
    private static final int INDEX_DATE_FROM = 1;
    private static final int INDEX_DATE_TO = 2;

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
    public void process(String input) throws ParseException {
        TodoListDB db = TodoListDB.getInstance();

        Map<String, String[]> parsedResult;
        parsedResult = Tokenizer.tokenize(getTokenDefinitions(), input);
        
        String[] parsedDates = parseDates(parsedResult);
        
        // Task or event specified?
        boolean deleteAll = parseDeleteAllType(parsedResult);
        
        // Task or event?
        boolean isTask = true; //default
        
        // task or event keyword is been found
        if (!deleteAll) {
            isTask = parseIsTask(parsedResult);
        }
        
        //no dates provided and input is exactly the same as COMMAND_WORD
        if (parsedDates == null && parseExactClearCommand(parsedResult) && deleteAll) {
            destroyAll(db);
            return;
        } else {
            //invalid date provide by user with no date keywords parsed by natty
            if (deleteAll && !parseExactClearCommand(parsedResult) && parsedDates == null) { //no item type and date provided
                LocalDateTime date = parseDateWithNoKeyword(parsedResult);
                if (date == null) {
                    displayErrorMessage(input, parsedDates, deleteAll, isTask);
                    return;
                } 
            }
        }
        
        //parsing of dates with keywords with natty
        LocalDateTime dateOn = parseDateWithNoKeyword(parsedResult);
        LocalDateTime dateFrom = (LocalDateTime) null;
        LocalDateTime dateTo = (LocalDateTime) null;
        if (parsedDates != null) {
            String naturalOn = parsedDates[INDEX_DATE_ON];
            String naturalFrom = parsedDates[INDEX_DATE_FROM];
            String naturalTo = parsedDates[INDEX_DATE_TO];
            // if all are null = no date provided
            
            // Parse natural date using Natty.
            dateOn = naturalOn == null ? (LocalDateTime) null : parseNatural(naturalOn);
            dateFrom = naturalFrom == null ? (LocalDateTime) null : parseNatural(naturalFrom); 
            dateTo = naturalTo == null ? (LocalDateTime) null : parseNatural(naturalTo);
        }
        
        //invoke destroy command
        destroyByDate(db, parsedDates, dateOn, dateFrom, dateTo, deleteAll, isTask, input);
    }

    /** ================ DESTROY TASKS/EVENTS WITH FILTERED KEYWORDS ================== **/

    /**
     * Clear all tasks and events by a single or a range of date that exist in the database.
     * 
     * @param db
     *            TodoListDB object
     * @param parsedDate
     *            null if no date entered, or natural date that user has entered
     * @param dateOn, dateFrom
     *            null if parsing failed or Due date for Task or start date for Event
     * @param dateTo
     *            null if parsing failed or End date for Event
     * @param deleteAll
     *            true if no CalendarItem Type provided, false if "task" or "event" keyword found
     * @param isTask
     *            true if "task" keyword found, false if "event" keyword found
     * @param input
     *            the input the user have entered
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
            if (!deleteAll && parsedDate != null && dateFrom == null
                    && dateTo == null && dateOn == null) { //date provided is invalid
                displayErrorMessage(input, parsedDate, deleteAll, isTask);
                return;
            } else {
                if (parsedDate != null) {
                    if (parsedDate[INDEX_DATE_FROM] != null && parsedDate[INDEX_DATE_TO] != null 
                            && (dateFrom == null || dateTo == null)) {
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
     * @param deleteAll
     *            true if no CalendarItem Type provided, false if "task" or "event" keyword found
     * @param isTask
     *            true if "task" keyword found, false if "event" keyword found
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
        
        //if no tasks or events are been found
        if (numTasks == 0 && numEvents == 0) {
            Renderer.renderIndex(db, MESSAGE_CLEAR_NO_ITEM_FOUND);
            return;
        }
        
        //if CalendarItem type not specified
        if (deleteAll) {
            db.destroyAllEventByRange(dateFrom, dateTo);
            db.destroyAllTaskByRange(dateFrom, dateTo);
        } else if (isTask) {
            // no task is been found
            if (numTasks == 0) {
                Renderer.renderIndex(db, MESSAGE_CLEAR_NO_ITEM_FOUND);
                return;
            }
            db.destroyAllTaskByRange(dateFrom, dateTo);
            numEvents = 0;
        } else {
            // no event is been found
            if (numEvents == 0) {
                Renderer.renderIndex(db, MESSAGE_CLEAR_NO_ITEM_FOUND);
                return;
            }
            db.destroyAllEventByRange(dateFrom, dateTo);
            numTasks = 0;
        }
        
        //save and render
        db.save();
        Renderer.renderIndex(db, String.format(MESSAGE_CLEAR_SUCCESS, StringUtil.formatNumberOfTaskAndEventWithPuralizer(numTasks, numEvents)));
    }
    

    /**
     * clear all tasks and events of the date that exist in the database.
     * 
     * @param TodoListDB
     * @param givenDate
     *            null if parsing failed or Due date for Task or start date for Event
     * @param deleteAll
     *            true if no CalendarItem Type provided, false if "task" or "event" keyword found
     * @param isTask
     *            true if "task" keyword found, false if "event" keyword found
     */
    private void destroyBySelectedDate(TodoListDB db, LocalDateTime givenDate, boolean deleteAll, boolean isTask) {
        int numTasks = db.getTaskByDate(givenDate).size();
        int numEvents = db.getEventByDate(givenDate).size();
        
        // no tasks or events are been found
        if (numTasks == 0 && numEvents == 0) {
            Renderer.renderIndex(db, MESSAGE_CLEAR_NO_ITEM_FOUND);
            return;
        }
        
        // task or event is not specified
        if (deleteAll) {
            db.destroyAllEventByDate(givenDate);
            db.destroyAllTaskByDate(givenDate);
        } else if (isTask) { //deleting task
            if (numTasks == 0) { //if no task is found
                Renderer.renderIndex(db, MESSAGE_CLEAR_NO_ITEM_FOUND);
                return;
            }
            db.destroyAllTaskByDate(givenDate);
            numEvents = 0;
        } else { //deleting events
            if (numEvents == 0) { //if no event is found
                Renderer.renderIndex(db, MESSAGE_CLEAR_NO_ITEM_FOUND);
                return;
            }
            db.destroyAllEventByDate(givenDate);
            numTasks = 0;
        }
        
        //save and render
        db.save();
        Renderer.renderIndex(db, String.format(MESSAGE_CLEAR_SUCCESS, StringUtil.formatNumberOfTaskAndEventWithPuralizer(numTasks, numEvents)));
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
    
    /** ================ PARSING METHODS ================== **/

    /**
     * Extracts the intended COMMAND_WORD from parsedResult.
     * 
     * @param parsedResult
     * @return true if no String provided after command word, false if some String provided after command word 
     */ 
    private boolean parseExactClearCommand(Map<String, String[]> parsedResult) {
        return parsedResult.get("default")[RESULT] == null;
    }

     /**
     * Extracts the date without any keyword from parsedResult.
     * 
     * @param parsedResult
     * @return LocalDatetime date if found, or null if no date found
     */    
    private LocalDateTime parseDateWithNoKeyword(Map<String, String[]> parsedResult) {
        if (parsedResult.get("default").length == MAXIMUM_SIZE) { // user enter more than 1 date with no keyword
            if (parsedResult.get("default")[RESULT] != null) {
                return parseNatural(parsedResult.get("default")[RESULT]);
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
        String naturalFrom = (String) null;
        String naturalTo = (String) null;
        String naturalOn = (String) null;
        
        if (parsedResult.get("time") == null) {
            if (parsedResult.get("timeFrom") != null) {
                naturalFrom = parsedResult.get("timeFrom")[RESULT];
            }
            if (parsedResult.get("timeTo") != null) {
                naturalTo = parsedResult.get("timeTo")[RESULT];
            }
        } else {
            naturalOn = parsedResult.get("time")[RESULT];
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
        return parsedResult.get("eventType")[KEYWORD].contains("task");
    }

    /** ================ FORMATTING OF SUCCESS/ERROR MESSAGE ================== **/

    /**
     * display error message due to invalid clear command
     * 
     * @param input
     *            based on user input
     * @param parsedDate            
     *            the date entered by the user
     * @param deleteAll
     *            true if no CalendarItem type provided, isTask will be ignored
     * @param isTask
     *            true if task keyword, false if event keyword is provided         
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

}
