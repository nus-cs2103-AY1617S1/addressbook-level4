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
        tokenDefinitions.put("taskStatus", new String[] { "complete" , "completed", "incomplete", "incompleted"});
        tokenDefinitions.put("eventStatus", new String[] { "over" , "ongoing", "prior", "schedule" , "scheduled"});
        tokenDefinitions.put("time", new String[] { "at", "by", "on", "time" });
        tokenDefinitions.put("timeFrom", new String[] { "from" });
        tokenDefinitions.put("timeTo", new String[] { "to", "before", "until" });
        return tokenDefinitions;
    }

    @Override
    public void process(String input) {
        
        Map<String, String[]> parsedResult;
        try {
            parsedResult = Tokenizer.tokenize(getTokenDefinitions(), input);
        } catch (UnmatchedQuotesException e) {
            System.out.println("Unmatched quote!");
            return ;
        }
        
        boolean isExactCommand = parseExactListCommand(parsedResult);
        
        // Task or event?
        boolean listAll = parseListAllType(parsedResult);
        
        boolean isTask = true; //default
        //if listing all type , set isTask and isEvent true
        if (!listAll) {
            isTask = parseIsTask(parsedResult);
        }
        
        boolean listAllTaskStatus = parseListAllTaskStatus(parsedResult);
        boolean isCompleted = false; //default 
        //if listing all task status, isCompleted will be ignored, listing both complete and incomplete
        if (!listAllTaskStatus) {
            isCompleted = !parseIsIncomplete(parsedResult);
        }
        
        boolean listAllEventStatus = parseListAllEventStatus(parsedResult);
        boolean isOver = false; //default
        //if listing all event status, isOver will be ignored, listing both prior and schedule events
        if(!listAllEventStatus) {
            isOver = parseIsOverEvents(parsedResult);
        }
        
        String[] parsedDates = parseDates(parsedResult);
        boolean isDateProvided = true;
        LocalDateTime dateOn = null;
        LocalDateTime dateFrom = null;
        LocalDateTime dateTo = null;
        
        if (parsedDates == null) {
            isDateProvided = false;
        } else {
            String naturalOn = parsedDates[0];
            String naturalFrom = parsedDates[1];
            String naturalTo = parsedDates[2];
    
            // Parse natural date using Natty.
            dateOn = naturalOn == null ? null : parseNatural(naturalOn); 
            dateFrom = naturalFrom == null ? null : parseNatural(naturalFrom); 
            dateTo = naturalTo == null ? null : parseNatural(naturalTo);
            
            //setting up view
            
        }
        setupView(isTask, listAll, isCompleted, listAllTaskStatus, dateOn, dateFrom, dateTo, isDateProvided, 
                parsedDates, isExactCommand, listAllEventStatus, isOver, input);
        
    }

    /**
     * Setting up the view 
     * 
     * @param isTask
     *            true if CalendarItem should be a Task, false if Event
     * @param isEvent
     *            true if CalendarItem should be a Event, false if Task  
     * @param listAll
     *            true if listing all type, isTask or isEvent are ignored          
     * @param isCompleted
     *            true if user request completed task
     * @param listAllTaskStatus
     *            true if user did not request any task status, isCompleted is ignored
     * @param listAllEventStatus
     *            true if user did not request any event status, isOver is ignored
     * @param isOver
     *            true if user request over events
     * @param dateOn
     *            Date if user specify for a certain date
     * @param dateFrom
     *            Due date for Task or start date for Event
     * @param dateTo
     *            End date for Event
     */
    private void setupView(boolean isTask, boolean listAll, boolean isCompleted,
            boolean listAllTaskStatus, LocalDateTime dateOn, LocalDateTime dateFrom, 
            LocalDateTime dateTo, boolean isDateProvided, String[] parsedDates, boolean isExactCommand, 
            boolean listAllEventStatus, boolean isOver, String input) {
        TodoListDB db = TodoListDB.getInstance();
        List<Task> tasks = null;
        List<Event> events = null;
        // isTask and isEvent = true, list all type
        if (listAll) { //task or event not specify
            if (listAllTaskStatus && listAllEventStatus) { // listAllEventStatus No keyword found in the input
                tasks = setupTaskView(isCompleted, listAllEventStatus, listAllTaskStatus, dateOn, dateFrom, dateTo, isDateProvided,
                        isExactCommand, listAll, db);
                events = setupEventView(isOver, listAllTaskStatus, listAllEventStatus, dateOn, dateFrom, dateTo, isDateProvided,
                        isExactCommand, listAll, db);
            } else {
                if (!listAllEventStatus) {
                    events = setupEventView(isOver, listAllTaskStatus, listAllEventStatus, dateOn, dateFrom, dateTo, isDateProvided,
                            isExactCommand, listAll, db);
                }
                
                if (!listAllTaskStatus) {
                    tasks = setupTaskView(isCompleted, listAllEventStatus, listAllTaskStatus, dateOn, dateFrom, dateTo, isDateProvided,
                            isExactCommand, listAll, db);
                }
            }
        } else {
            if (isTask) {
                tasks = setupTaskView(isCompleted, listAllEventStatus, listAllTaskStatus, dateOn, dateFrom, dateTo, isDateProvided,
                        isExactCommand, listAll, db);
            } else {
                events = setupEventView(isOver, listAllTaskStatus, listAllEventStatus, dateOn, dateFrom, dateTo, isDateProvided,
                        isExactCommand, listAll, db);
            }
        }
        
        if (tasks == null && events == null) {
            displayErrorMessage(input, listAll, listAllEventStatus, isOver, listAllTaskStatus, isCompleted, isTask, parsedDates);
            return ; //display error message
        }
        
        // Update console message
        int numTasks = 0;
        int numEvents = 0;
        
        if (tasks != null) {
            numTasks = tasks.size();
        }
        
        if(events != null) {
            numEvents = events.size();
        }
        
        String consoleMessage = "";
        if (numTasks != 0 || numEvents != 0) {
            consoleMessage = String.format(MESSAGE_LISTING_SUCCESS, formatDisplayMessage(numTasks, numEvents));
        } else {
            consoleMessage = MESSAGE_LISTING_FAILURE;
        }
        
        Renderer.renderSelected(db, consoleMessage, tasks, events);
       
    }
    
    /**
     * display error message due to invalid clear command
     * 
     * @param input
     *            based on user input
     * @param parsedDate            
     *            the date entered by the user      
     */
    private void displayErrorMessage(String input, boolean listAll, boolean listAllEventStatus, boolean isOver,
            boolean listAllTaskStatus, boolean isCompleted, boolean isTask, String[] parsedDates) {
        String consoleDisplayMessage = String.format("You have entered : %s.",input);
        String commandLineMessage = COMMAND_WORD;
        String commandLineCompleteSuggestMessage = "complete";
        String commandLineIncompleteSuggestMessage = "incomplete";
        String commandLineOngoingSuggestMessage = "ongoing";
        String commandLineOverSuggestMessage = "over";
        String commandLineTaskSuggestMessage = "task";
        String commandLineEventSuggestMessage = "event";
        
        if (!listAll) {
            if (isTask) {
                commandLineMessage = String.format("%s %s", commandLineMessage, commandLineTaskSuggestMessage);
            } else {
                commandLineMessage = String.format("%s %s", commandLineMessage, commandLineEventSuggestMessage);
            }
        }
        
        if (!listAllTaskStatus && !listAllEventStatus) {
            if (isCompleted) {
                commandLineMessage = String.format("%s %s", commandLineMessage, commandLineCompleteSuggestMessage);
            } else {
                commandLineMessage = String.format("%s %s", commandLineMessage, commandLineIncompleteSuggestMessage);
            }
        }
        
        if (!listAllEventStatus && !listAllTaskStatus) {
            if (isOver) {
                commandLineMessage = String.format("%s %s", commandLineMessage, commandLineOverSuggestMessage);
            } else {
                commandLineMessage = String.format("%s %s", commandLineMessage, commandLineOngoingSuggestMessage);
            }
        }
        
        if (parsedDates != null) {
            if (parsedDates[0] != null) {
                commandLineMessage = String.format("%s by <date>", commandLineMessage);
            } else {
                commandLineMessage = String.format("%s from <date> to <date>", commandLineMessage);
            } 
        }
        
        Renderer.renderDisambiguation(commandLineMessage, consoleDisplayMessage);
    }
    
    private String formatDisplayMessage (int numTasks, int numEvents) {
        if (numTasks != 0 && numEvents != 0) {
            return String.format("%s and %s.", formatTaskMessage(numTasks), formatEventMessage(numEvents));
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
    
    private List<Event> setupEventView(boolean isCompleted, boolean listAllTaskStatus, boolean listAllEventStatus, 
            LocalDateTime dateOn, LocalDateTime dateFrom, LocalDateTime dateTo, boolean isDateProvided, 
            boolean isExactCommand, boolean listAll, TodoListDB db) {
        if (!listAllTaskStatus) {
            return null;
        }
        
        if (dateFrom == null && dateTo == null && dateOn == null) {
            if (listAllEventStatus) { // not specify
                if (isExactCommand && isDateProvided == false) {
                    if (listAll) {
                        return db.getAllCurrentEvents();
                    } else {
                        return db.getAllEvents();
                    }
                } else {
                    return null;
                }
            } else if (isCompleted) {
                return db.getEventByRange(null, LocalDateTime.now());
            } else {
                return db.getEventByRange(LocalDateTime.now(), null);
            }
        } else if (dateOn != null) { //by keyword found
            return db.getEventByDate(dateOn);
        } else {
            return db.getEventByRange(dateFrom, dateTo);
        }
    }

    private List<Task> setupTaskView(boolean isCompleted, boolean listAllEventStatus, boolean listAllTaskStatus, LocalDateTime dateOn, LocalDateTime dateFrom,
            LocalDateTime dateTo, boolean isDateProvided, boolean isExactCommand, boolean listAll, TodoListDB db) {
        if (!listAllEventStatus) {
            return null;
        }
        if (dateFrom == null && dateTo == null && dateOn == null) {
            if (listAllTaskStatus) { // not specify
                if (isExactCommand && isDateProvided == false) {
                    if (listAll) {
                        return db.getIncompleteTasksAndTaskFromTodayDate();
                    } else {
                        return db.getAllTasks();
                    }
                } else {
                    return null;
                }
            } else {
                return db.getTaskByRangeWithStatus(dateFrom, dateTo, isCompleted, listAllTaskStatus);
            }
        } else if (dateOn != null) { //by keyword found
            return db.getTaskByDateWithStatus(dateOn, isCompleted, listAllTaskStatus);
        } else {
            return db.getTaskByRangeWithStatus(dateFrom, dateTo, isCompleted, listAllTaskStatus);
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
    
    private boolean parseExactListCommand(Map<String, String[]> parsedResult) {
        return parsedResult.get("default")[1] == null;
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
     * @return true if any keyword in task status token definition is given
     */
    private boolean parseListAllTaskStatus (Map<String, String[]> parsedResult) {
        return !(parsedResult.get("taskStatus") != null);
    }
    
    /**
     * Extracts the intended CalendarItem status from parsedResult.
     * 
     * @param parsedResult
     * @return true if incomplete, false if complete
     */
    private boolean parseIsIncomplete (Map<String, String[]> parsedResult) {
        return parsedResult.get("taskStatus")[0].contains("incomplete");
    }
    
    /**
     * Extracts the intended status type specify from parsedResult.
     * 
     * @param parsedResult
     * @return true if any keyword in event status token defintion is given
     */
    private boolean parseListAllEventStatus (Map<String, String[]> parsedResult) {
        return !(parsedResult.get("eventStatus") != null);
    }
    
    /**
     * Extracts the intended CalendarItem status from parsedResult.
     * 
     * @param parsedResult
     * @return true if over or prior, false if not
     */
    private boolean parseIsOverEvents (Map<String, String[]> parsedResult) {        
        boolean isPriorFound = parsedResult.get("eventStatus")[0].contains("prior");
        boolean isOverFound = parsedResult.get("eventStatus")[0].contains("over");
        return isPriorFound || isOverFound;
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
