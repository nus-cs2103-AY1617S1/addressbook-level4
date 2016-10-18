package seedu.todo.controllers;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.joestelmach.natty.DateGroup;
import com.joestelmach.natty.Parser;

import seedu.todo.commons.exceptions.UnmatchedQuotesException;
import seedu.todo.commons.util.DateUtil;
import seedu.todo.commons.util.StringUtil;
import seedu.todo.models.TodoListDB;
import seedu.todo.ui.UiManager;
import seedu.todo.ui.views.IndexView;

public class ListController implements Controller {
    
    private static String NAME = "List";
    private static String DESCRIPTION = "Lists all tasks and events.";
    private static String COMMAND_SYNTAX = "list";
    
    private static final String MESSAGE_LISTING_SUCCESS = "Listing a total of %d %s and %d %s.";
    
    private static CommandDefinition commandDefinition =
            new CommandDefinition(NAME, DESCRIPTION, COMMAND_SYNTAX); 

    public static CommandDefinition getCommandDefinition() {
        return commandDefinition;
    }

    @Override
    public float inputConfidence(String input) {
        return (input.startsWith("list")) ? 1 : 0;
    }
    
    private static Map<String, String[]> getTokenDefinitions() {
        Map<String, String[]> tokenDefinitions = new HashMap<String, String[]>();
        tokenDefinitions.put("default", new String[] {"list"});
        tokenDefinitions.put("eventType", new String[] { "event", "events", "task", "tasks"});
        tokenDefinitions.put("status", new String[] { "complete" , "completed", "uncomplete", "uncompleted"});
        tokenDefinitions.put("time", new String[] { "at", "by", "on", "time" });
        tokenDefinitions.put("timeFrom", new String[] { "from" });
        tokenDefinitions.put("timeTo", new String[] { "to", "before" });
        return tokenDefinitions;
    }
    
    private boolean parseListAllType (Map<String, String[]> parsedResult) {
        if (parsedResult.get("eventType") != null) {
            return false;
        } else {
            return true;
        }
    }
    
    private boolean parseListAllStatus (Map<String, String[]> parsedResult) {
        if (parsedResult.get("status") != null) {
            return false;
        } else {
            return true;
        }
    }
    
    private boolean parseIsUncomplete (Map<String, String[]> parsedResult) {
        return parsedResult.get("status")[0].contains("uncomplete");
    }
    
    private boolean parseIsTask (Map<String, String[]> parsedResult) {
        return parsedResult.get("eventType")[0].equals("task");
    }
    
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
        
        return new String[] { naturalOn, naturalFrom, naturalTo };
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
        
        // Task or event?
        boolean listAll = parseListAllType(parsedResult);
        
        boolean isTask = true; //default
        //if listing all type , set isTask and isEvent true
        if (!listAll) {
            isTask = parseIsTask(parsedResult);
        }
        
        boolean listAllStatus = parseListAllStatus(parsedResult);
        boolean isCompleted = false; //default 
        //if listing all status, isCompleted will be ignored, listing both complete and uncomplete
        if (!listAllStatus) {
            isCompleted = !parseIsUncomplete(parsedResult);
        }
        
        String[] parsedDates = parseDates(parsedResult);
        String naturalOn = parsedDates[0];
        String naturalFrom = parsedDates[1];
        String naturalTo = parsedDates[2];

        // Parse natural date using Natty.
        LocalDateTime dateOn = naturalOn == null ? null : parseNatural(naturalOn); 
        LocalDateTime dateFrom = naturalFrom == null ? null : parseNatural(naturalFrom); 
        LocalDateTime dateTo = naturalTo == null ? null : parseNatural(naturalTo);
        
        //setting up view
        IndexView view = setupView(isTask, listAll, isCompleted, listAllStatus, dateOn, dateFrom, dateTo);
        
        // Update console message
        int numTasks = view.tasks.size();
        int numEvents = view.events.size();
        String consoleMessage = String.format(MESSAGE_LISTING_SUCCESS, 
                numTasks, StringUtil.pluralizer(numTasks, "task", "tasks"), 
                numEvents, StringUtil.pluralizer(numEvents, "event", "events"));
        UiManager.updateConsoleMessage(consoleMessage);
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
     *            true if user request completed item
     * @param listAllStatus
     *            true if user did not request any status, isCompleted is ignored
     * @param dateOn
     *            Date if user specify for a certain date
     * @param dateFrom
     *            Due date for Task or start date for Event
     * @param dateTo
     *            End date for Event
     */
    private IndexView setupView(boolean isTask, boolean listAll, boolean isCompleted,
            boolean listAllStatus, LocalDateTime dateOn, LocalDateTime dateFrom, LocalDateTime dateTo) {
        TodoListDB db = TodoListDB.getInstance();
        IndexView view = UiManager.loadView(IndexView.class);
        // isTask and isEvent = true, list all type
        if (listAll) {
            //no event or task keyword found
            isTask = false;
            setupTaskView(isCompleted, listAllStatus, dateOn, dateFrom, dateTo, db, view);
            setupEventView(isCompleted, listAllStatus, dateOn, dateFrom, dateTo, db, view);
        }
        
        if (isTask) {
            setupTaskView(isCompleted, listAllStatus, dateOn, dateFrom, dateTo, db, view);
        } else {
            setupEventView(isCompleted, listAllStatus, dateOn, dateFrom, dateTo, db, view);
        }
        
        UiManager.renderView(view);
        return view;
    }

    private void setupEventView(boolean isCompleted, boolean listAllStatus, LocalDateTime dateOn, LocalDateTime dateFrom, 
            LocalDateTime dateTo, TodoListDB db, IndexView view) {
        if (dateFrom == null && dateTo == null && dateOn == null) {
            if (listAllStatus) {
                view.events = db.getAllEvents();
            } else if (isCompleted) {
                view.events = db.getEventByRange(null, LocalDateTime.now());
            } else {
                view.events = db.getEventByRange(LocalDateTime.now(), null);
            }
        } else if (dateOn != null) { //by keyword found
            view.events = db.getEventbyDate(dateOn);
        } else {
            view.events = db.getEventByRange(dateFrom, dateTo);
        }
    }

    private void setupTaskView(boolean isCompleted, boolean listAllStatus, LocalDateTime dateOn, LocalDateTime dateFrom,
            LocalDateTime dateTo, TodoListDB db, IndexView view) {
        if (dateFrom == null && dateTo == null && dateOn == null) {
            if (listAllStatus) {
                view.tasks = db.getAllTasks();
            } else {
                view.tasks = db.getTaskByRange(dateFrom, dateTo, isCompleted, listAllStatus);
            }
        } else if (dateOn != null) { //by keyword found
            view.tasks = db.getTaskByDate(dateOn, isCompleted, listAllStatus);
        } else {
            view.tasks = db.getTaskByRange(dateFrom, dateTo, isCompleted, listAllStatus);
        }
    }
    
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

}
