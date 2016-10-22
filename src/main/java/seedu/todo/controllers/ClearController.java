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

/**
 * Controller to clear CalendarItems.
 * 
 * @author Tiong Yaocong
 *
 */
public class ClearController implements Controller {
    
    private static final String NAME = "Clear";
    private static final String DESCRIPTION = "Clear all tasks/events or by specify date.";
    private static final String COMMAND_SYNTAX = "clear [task/event] [on date]";
    private static final String COMMAND_WORD = "clear";
    private static final String MESSAGE_CLEAR_NO_ITEM_FOUND = "No item found!";
    private static final String MESSAGE_CLEAR_SUCCESS = "A total of %s deleted!\n" + "To undo, type \"undo\".";;
    private static final String INVALID_DATE = null;
    private static final String NOT_FOUND = null;
    private static final String[] PARSED_RESULT_NOT_FOUND = null;
    private static final LocalDateTime DATE_NOT_FOUND = null;
    //Use by array access
    private static final int KEYWORD = 0;
    private static final int RESULT = 1;
    private static final int MAXIMUM_SIZE = 2;
    //Use by accessing date value
    private static final int DATE_ON = 0;
    private static final int DATE_FROM = 1;
    private static final int DATE_TO = 2;
    //Use by checking size
    private static final int EMPTY = 0;    

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
        LocalDateTime dateFrom = DATE_NOT_FOUND;
        LocalDateTime dateTo = DATE_NOT_FOUND;
        if (parsedDates != PARSED_RESULT_NOT_FOUND) {
            String naturalOn = parsedDates[DATE_ON];
            String naturalFrom = parsedDates[DATE_FROM];
            String naturalTo = parsedDates[DATE_TO];
            // if all are null = no date provided
            
            // Parse natural date using Natty.
            dateOn = naturalOn == INVALID_DATE ? DATE_NOT_FOUND : parseNatural(naturalOn);
            dateFrom = naturalFrom == INVALID_DATE ? DATE_NOT_FOUND : parseNatural(naturalFrom); 
            dateTo = naturalTo == INVALID_DATE ? DATE_NOT_FOUND : parseNatural(naturalTo);
        }
        
        //invokey destroy command
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
        if (dateOn == DATE_NOT_FOUND && dateFrom == DATE_NOT_FOUND && dateTo == DATE_NOT_FOUND && deleteAll) {
            displayErrorMessage(input, parsedDate, deleteAll, isTask);
        }
        else if (dateOn != null) {
            destroyBySelectedDate(db, dateOn, deleteAll, isTask);
            return;
        } else {
            if (!deleteAll && parsedDate != PARSED_RESULT_NOT_FOUND && dateFrom == DATE_NOT_FOUND
                    && dateTo == DATE_NOT_FOUND && dateOn == DATE_NOT_FOUND) { //date provided is invalid
                displayErrorMessage(input, parsedDate, deleteAll, isTask);
                return;
            } else {
                if (parsedDate != null) {
                    if (parsedDate[DATE_FROM] != null && parsedDate[DATE_TO] != null 
                            && (dateFrom == DATE_NOT_FOUND || dateTo == DATE_NOT_FOUND)) {
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
        if (dateFrom == DATE_NOT_FOUND) {
            dateFrom = LocalDateTime.MIN;
        } 
        
        if (dateTo == DATE_NOT_FOUND) {
            dateTo = LocalDateTime.MAX;
        }
        
        int numTasks = db.getTaskByRange(dateFrom, dateTo).size();
        int numEvents = db.getEventByRange(dateFrom, dateTo).size();
        
        //if no tasks or events are been found
        if (numTasks == EMPTY && numEvents == EMPTY) {
            Renderer.renderIndex(db, MESSAGE_CLEAR_NO_ITEM_FOUND);
            return;
        }
        
        //if CalendarItem type not specified
        if (deleteAll) {
            db.destroyAllEventByRange(dateFrom, dateTo);
            db.destroyAllTaskByRange(dateFrom, dateTo);
        } else if (isTask) {
            // no task is been found
            if (numTasks == EMPTY) {
                Renderer.renderIndex(db, MESSAGE_CLEAR_NO_ITEM_FOUND);
                return;
            }
            db.destroyAllTaskByRange(dateFrom, dateTo);
            numEvents = EMPTY;
        } else {
            // no event is been found
            if (numEvents == EMPTY) {
                Renderer.renderIndex(db, MESSAGE_CLEAR_NO_ITEM_FOUND);
                return;
            }
            db.destroyAllEventByRange(dateFrom, dateTo);
            numTasks = EMPTY;
        }
        
        //save and render
        db.save();
        Renderer.renderIndex(db, String.format(MESSAGE_CLEAR_SUCCESS, formatSuccessMessage(numTasks, numEvents)));
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
        if (numTasks == EMPTY && numEvents == EMPTY) {
            Renderer.renderIndex(db, MESSAGE_CLEAR_NO_ITEM_FOUND);
            return;
        }
        
        // task or event is not specified
        if (deleteAll) {
            db.destroyAllEventByDate(givenDate);
            db.destroyAllTaskByDate(givenDate);
        } else if (isTask) { //deleting task
            if (numTasks == EMPTY) { //if no task is found
                Renderer.renderIndex(db, MESSAGE_CLEAR_NO_ITEM_FOUND);
                return;
            }
            db.destroyAllTaskByDate(givenDate);
            numEvents = EMPTY;
        } else { //deleting events
            if (numEvents == EMPTY) { //if no event is found
                Renderer.renderIndex(db, MESSAGE_CLEAR_NO_ITEM_FOUND);
                return;
            }
            db.destroyAllEventByDate(givenDate);
            numTasks = EMPTY;
        }
        
        //save and render
        db.save();
        Renderer.renderIndex(db, String.format(MESSAGE_CLEAR_SUCCESS, formatSuccessMessage(numTasks, numEvents)));
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
        return parsedResult.get("default")[RESULT] == NOT_FOUND;
    }

     /**
     * Extracts the date without any keyword from parsedResult.
     * 
     * @param parsedResult
     * @return LocalDatetime date if found, or null if no date found
     */    
    private LocalDateTime parseDateWithNoKeyword(Map<String, String[]> parsedResult) {
        if (parsedResult.get("default").length == MAXIMUM_SIZE) { // user enter more than 1 date with no keyword
            if (parsedResult.get("default")[RESULT] != NOT_FOUND) {
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
        String naturalFrom = NOT_FOUND;
        String naturalTo = NOT_FOUND;
        String naturalOn = NOT_FOUND;
        
        if (parsedResult.get("time") == PARSED_RESULT_NOT_FOUND) {
            if (parsedResult.get("timeFrom") != PARSED_RESULT_NOT_FOUND) {
                naturalFrom = parsedResult.get("timeFrom")[RESULT];
            }
            if (parsedResult.get("timeTo") != PARSED_RESULT_NOT_FOUND) {
                naturalTo = parsedResult.get("timeTo")[RESULT];
            }
        } else {
            naturalOn = parsedResult.get("time")[RESULT];
        }
        
        if (naturalFrom != NOT_FOUND || naturalTo != NOT_FOUND || naturalOn != NOT_FOUND) {
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
        return !(parsedResult.get("eventType") != PARSED_RESULT_NOT_FOUND);
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

    /*
     * Format the number of events found based on the events found
     * 
     *  @param numEvents 
     *          the number of events based on the filtered result
     */
    private String formatEventMessage (int numEvents) {
        return String.format("%d %s", numEvents, StringUtil.pluralizer(numEvents, "event", "events"));
    }
    
    /*
     * Format the number of tasks found based on the tasks found
     * 
     *  @param numTasks 
     *          the number of tasks based on the filtered result
     */
    private String formatTaskMessage (int numTasks) {
        return String.format("%d %s", numTasks, StringUtil.pluralizer(numTasks, "task", "tasks"));
    }

    /*
     * Format the display message depending on the number of tasks and events 
     * 
     * @param numTasks
     *          the number of tasks based on the filtered result
     * @param numEvents
     *          the number of events based on the filtered result   
     *        
     * @return the display message for console message output           
     */    
    private String formatSuccessMessage (int numTasks, int numEvents) {
        if (numTasks != EMPTY && numEvents != EMPTY) {
            return String.format("%s and %s", formatTaskMessage(numTasks), formatEventMessage(numEvents));
        } else if (numTasks != EMPTY) {
            return formatTaskMessage(numTasks);
        } else {
            return formatEventMessage(numEvents);
        }
    }

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
