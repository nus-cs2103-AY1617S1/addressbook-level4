package seedu.todo.controllers;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.joestelmach.natty.DateGroup;
import com.joestelmach.natty.Parser;

import seedu.todo.commons.exceptions.ParseException;
import seedu.todo.commons.util.DateUtil;
import seedu.todo.commons.util.StringUtil;
import seedu.todo.controllers.concerns.Tokenizer;
import seedu.todo.controllers.concerns.Renderer;
import seedu.todo.models.Event;
import seedu.todo.models.Task;
import seedu.todo.models.TodoListDB;

/**
 * Controller to list CalendarItems.
 * 
 * @author Tiong Yaocong
 *
 */
public class ListController implements Controller {
    
    private static final String NAME = "List";
    private static final String DESCRIPTION = "Lists all tasks and events.";
    private static final String COMMAND_SYNTAX = "list [task/event] [complete/incomplete] [on date] or [from date to date]";
    private static final String COMMAND_WORD = "list";
    
    private static final String MESSAGE_LISTING_SUCCESS = "Listing a total of %s";
    private static final String MESSAGE_LISTING_FAILURE = "No task or event found!";
    private static final String COMMANDLINE_COMPLETE_SUGGEST_MESSAGE = "complete";
    private static final String COMMANDLINE_INCOMPLETE_SUGGEST_MESSAGE = "incomplete";
    private static final String COMMANDLINE_TASK_SUGGEST_MESSAGE = "task";
    private static final String COMMANDLINE_EVENT_SUGGEST_MESSAGE = "event";
    
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
        return (input.toLowerCase().startsWith("list")) ? 1 : 0;
    }
    
    private static Map<String, String[]> getTokenDefinitions() {
        Map<String, String[]> tokenDefinitions = new HashMap<String, String[]>();
        tokenDefinitions.put("default", new String[] {"list"});
        tokenDefinitions.put("eventType", new String[] { "event", "events", "task", "tasks"});
        tokenDefinitions.put("status", new String[] { "complete" , "completed", "incomplete", "incompleted"});
        tokenDefinitions.put("time", new String[] { "at", "by", "on", "time" });
        tokenDefinitions.put("timeFrom", new String[] { "from" });
        tokenDefinitions.put("timeTo", new String[] { "to", "before" });
        return tokenDefinitions;
    }

    @Override
    public void process(String input) throws ParseException {
        
        Map<String, String[]> parsedResult;
        parsedResult = Tokenizer.tokenize(getTokenDefinitions(), input);
        
        boolean isExactCommand = parseExactListCommand(parsedResult);
        
        // Task or event?
        boolean listAll = parseListAllType(parsedResult);
        
        boolean isTask = true; //default
        
        // Task or Event specified by user, set the item type
        if (!listAll) {
            isTask = parseIsTask(parsedResult);
        }
        
        boolean listAllStatus = parseListAllStatus(parsedResult);
        boolean isCompleted = false; //default 
        
        //if complete or incomplete is specified by user, set the status
        if (!listAllStatus) {
            isCompleted = !parseIsIncomplete(parsedResult);
        }
        
        // parsing of dates
        String[] parsedDates = parseDates(parsedResult);
        boolean isDateProvided = true; //default
        LocalDateTime dateOn = parseDateWithNoKeyword(parsedResult);
        LocalDateTime dateFrom = null; //default
        LocalDateTime dateTo = null; //default
        
        //check if any date is provided by the user
        if (parsedDates == null) {
            isDateProvided = false;
        } else {
            String naturalOn = parsedDates[INDEX_DATE_ON];
            String naturalFrom = parsedDates[INDEX_DATE_FROM];
            String naturalTo = parsedDates[INDEX_DATE_TO];
    
            // Parse natural date using Natty.
            dateOn = naturalOn == null ? (LocalDateTime) null : parseNatural(naturalOn); 
            dateFrom = naturalFrom == null ? (LocalDateTime) null : parseNatural(naturalFrom); 
            dateTo = naturalTo == null ? (LocalDateTime) null : parseNatural(naturalTo);
        }
        
        //setting up view
        setupView(isTask, listAll, isCompleted, listAllStatus, dateOn, dateFrom, dateTo, isDateProvided, 
                parsedDates, isExactCommand, input);
    }

    /** ================ FORMATTING OF SUCCESS/ERROR MESSAGE ================== **/
    
    /**
     * Display error message due to invalid clear command
     * 
     * @param input
     *            based on user input
     * @param listAll
     *            true if no CalendarItem type provided, isTask will be ignored
     * @param listAllStatus
     *            true if no status provided, isCompleted will be ignored
     * @param isCompleted
     *            true if complete keyword, false if incomplete keyword is provided 
     * @param isTask
     *            true if task keyword, false if event keyword is provided                                            
     * @param parsedDate            
     *            the date entered by the user      
     */
    private void displayErrorMessage(String input, boolean listAll, boolean listAllStatus, boolean isCompleted,
            boolean isTask, String[] parsedDates) {
        String consoleDisplayMessage = String.format("You have entered : %s.",input);
        String commandLineMessage = COMMAND_WORD;
        
        //update command line message display according to the input
        if (!listAll) {
            if (isTask) {
                commandLineMessage = String.format("%s %s", commandLineMessage, COMMANDLINE_TASK_SUGGEST_MESSAGE);
            } else {
                commandLineMessage = String.format("%s %s", commandLineMessage, COMMANDLINE_EVENT_SUGGEST_MESSAGE);
            }
        }
        
        if (!listAllStatus) {
            if (isCompleted) {
                commandLineMessage = String.format("%s %s", commandLineMessage, COMMANDLINE_COMPLETE_SUGGEST_MESSAGE);
            } else {
                commandLineMessage = String.format("%s %s", commandLineMessage, COMMANDLINE_INCOMPLETE_SUGGEST_MESSAGE);
            }
        }
        
        if (parsedDates != null) {
            if (parsedDates[INDEX_DATE_ON] != null) {
                commandLineMessage = String.format("%s by <date>", commandLineMessage);
            } else {
                commandLineMessage = String.format("%s from <date> to <date>", commandLineMessage);
            } 
        }
        
        //render the view
        Renderer.renderDisambiguation(commandLineMessage, consoleDisplayMessage);
    }
    
    /** ================ SETTING UP VIEWS ================== **/
    
    /**
     * Setting up the view 
     * 
     * @param isTask
     *            true if CalendarItem should be a Task, false if Event
     * @param isEvent
     *            true if CalendarItem should be a Event, false if Task  
     * @param listAll
     *            true if CalendarItem Type not provided, isTask or isEvent values will be ignored          
     * @param listAllStatus
     *            true if Status is not provided, isCompleted values will be ignored     
     * @param isCompleted
     *            true if user request completed item
     * @param dateOn
     *            Date if user specify for a certain date
     * @param dateFrom
     *            Due date for Task or start date for Event
     * @param dateTo
     *            End date for Event
     * @param isDateProvided 
     *            true if date is provided is any part of the input
     * @param parsedDates
     *            natural dates that are been parsed by Natty
     * @param isExactCommand
     *            true if input is exactly the same as COMMAND_WORD
     * @param input
     *            String input by the user, to display for error message                                            
     */
    private void setupView(boolean isTask, boolean listAll, boolean isCompleted,
            boolean listAllStatus, LocalDateTime dateOn, LocalDateTime dateFrom, 
            LocalDateTime dateTo, boolean isDateProvided, String[] parsedDates, boolean isExactCommand, String input) {
        TodoListDB db = TodoListDB.getInstance();
        List<Task> tasks = null;
        List<Event> events = null;
        // isTask and isEvent = true, list all type
        if (listAll) { //task or event not specify
            //no event or task keyword found
            isTask = false;
            tasks = setupTaskView(isCompleted, listAllStatus, dateOn, dateFrom, dateTo, isDateProvided,
                    isExactCommand, listAll, db);
            events = setupEventView(isCompleted, listAllStatus, dateOn, dateFrom, dateTo, isDateProvided,
                    isExactCommand, listAll, db);
        }
        
        if (isTask) {
            tasks = setupTaskView(isCompleted, listAllStatus, dateOn, dateFrom, dateTo, isDateProvided,
                    isExactCommand, listAll, db);
        } else {
            events = setupEventView(isCompleted, listAllStatus, dateOn, dateFrom, dateTo, isDateProvided,
                    isExactCommand, listAll, db);
        }
        
        if (tasks == null && events == null) {
            displayErrorMessage(input, listAll, listAllStatus, isCompleted, isTask, parsedDates);
            return ; //display error message
        }
        
        // Update console message
        int numTasks = 0;
        int numEvents = 0;
        
        //if any tasks are been found, set the total count for task
        if (tasks != null) {
            numTasks = tasks.size();
        }
        
        //if any events are been found, set the total count for event
        if(events != null) {
            numEvents = events.size();
        }
        
        //default console message
        String consoleMessage = "";
        
        //if any tasks or events found, update console message to display found successfully
        //      else display failure message
        if (numTasks != 0 || numEvents != 0) {
            consoleMessage = String.format(MESSAGE_LISTING_SUCCESS, StringUtil.formatNumberOfTaskAndEventWithPuralizer(numTasks, numEvents));
        } else {
            consoleMessage = MESSAGE_LISTING_FAILURE;
        }
        
        //render the view as parsed result
        Renderer.renderSelected(db, consoleMessage, tasks, events);
       
    }
    
    /**
     * Setting up event view 
     * 
     * @param isCompleted
     *            true if user request completed item
     * @param listAllStatus
     *            true if Status is not provided, isCompleted values will be ignored     
     * @param dateOn
     *            Date if user specify for a certain date
     * @param dateFrom
     *            Due date for Task or start date for Event
     * @param dateTo
     *            End date for Event  
     * @param isDateProvided 
     *            true if date is provided is any part of the input            
     * @param isExactCommand
     *            true if input is exactly the same as COMMAND_WORD
     * @param listAll
     *            true if CalendarItem Type not provided 
     * @param db
     *            instance of TodoListDB db                              
     */
    private List<Event> setupEventView(boolean isCompleted, boolean listAllStatus, LocalDateTime dateOn,
            LocalDateTime dateFrom, LocalDateTime dateTo, boolean isDateProvided, 
            boolean isExactCommand, boolean listAll, TodoListDB db) {
        if (dateFrom == null && dateTo == null && dateOn == null) {
            if (listAllStatus) { // not specify
                if (isExactCommand && isDateProvided == false) {
                    if (listAll) {
                        return db.getAllCurrentEvents();
                    } else {
                        return db.getAllEvents();
                    }
                } else {
                    return (List<Event>) null;
                }
            } else if (isCompleted) {
                return db.getEventByRange((LocalDateTime) null, LocalDateTime.now());
            } else {
                return db.getEventByRange(LocalDateTime.now(), (LocalDateTime) null);
            }
        } else if (dateOn != null) { //by keyword found
            return db.getEventByDate(dateOn);
        } else {
            return db.getEventByRange(dateFrom, dateTo);
        }
    }

    /**
     * Setting up Task view 
     * 
     * @param isCompleted
     *            true if user request completed item
     * @param listAllStatus
     *            true if Status is not provided, isCompleted values will be ignored     
     * @param dateOn
     *            Date if user specify for a certain date
     * @param dateFrom
     *            Due date for Task or start date for Event
     * @param dateTo
     *            End date for Event  
     * @param isDateProvided 
     *            true if date is provided is any part of the input            
     * @param isExactCommand
     *            true if input is exactly the same as COMMAND_WORD
     * @param listAll
     *            true if CalendarItem Type not provided
     * @param db
     *            instance of TodoListDB db                              
     */
    private List<Task> setupTaskView(boolean isCompleted, boolean listAllStatus, LocalDateTime dateOn, LocalDateTime dateFrom,
            LocalDateTime dateTo, boolean isDateProvided, boolean isExactCommand, boolean listAll, TodoListDB db) {
        if (dateFrom == null && dateTo == null && dateOn == null) {
            if (listAllStatus) { // not specify
                if (isExactCommand && isDateProvided == false) {
                    if (listAll) {
                        return db.getIncompleteTasksAndTaskFromTodayDate();
                    } else {
                        return db.getAllTasks();
                    }
                } else {
                    return (List<Task>) null;
                }
            } else {
                return db.getTaskByRangeWithStatus(dateFrom, dateTo, isCompleted, listAllStatus);
            }
        } else if (dateOn != null) { //by keyword found
            return db.getTaskByDateWithStatus(dateOn, isCompleted, listAllStatus);
        } else {
            return db.getTaskByRangeWithStatus(dateFrom, dateTo, isCompleted, listAllStatus);
        }
    }
    
    /** ================ PARSING METHODS ================== **/
    
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
     * Extracts the intended COMMAND_WORD from parsedResult.
     * 
     * @param parsedResult
     * @return true if no String provided after command word, false if some String provided after command word 
     */    
    private boolean parseExactListCommand(Map<String, String[]> parsedResult) {
        return parsedResult.get("default")[RESULT] == null;
    }
    
    /**
     * Extracts the intended CalendarItem type specify from parsedResult.
     * 
     * @param parsedResult
     * @return true if Task or event is not specify, false if either Task or Event specify
     */
    private boolean parseListAllType (Map<String, String[]> parsedResult) {
        return !(parsedResult.get("eventType") != null);
    }
    
    /**
     * Extracts the intended status type specify from parsedResult.
     * 
     * @param parsedResult
     * @return true if Task or event is not specify, false if either Task or Event specify
     */
    private boolean parseListAllStatus (Map<String, String[]> parsedResult) {
        return !(parsedResult.get("status") != null);
    }
    
    /**
     * Extracts the intended CalendarItem status from parsedResult.
     * 
     * @param parsedResult
     * @return true if incomplete, false if complete
     */
    private boolean parseIsIncomplete (Map<String, String[]> parsedResult) {
        return parsedResult.get("status")[KEYWORD].contains("incomplete");
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
            return (String[]) null;
        }
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
                return (LocalDateTime) null;
            }
        } else {
            return (LocalDateTime) null;
        }
    }
    

}
