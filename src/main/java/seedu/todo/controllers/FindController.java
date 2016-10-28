package seedu.todo.controllers;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
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
 * Controller to find task/event by keyword
 * 
 * @@author Tiong YaoCong A0139922Y
 *
 */
public class FindController implements Controller {
    
    private static final String NAME = "Find";
    private static final String DESCRIPTION = "Find all tasks and events based on the provided keywords.\n" + 
    "This command will be search with non-case sensitive keywords.";
    private static final String COMMAND_SYNTAX = "find [name] or/and [on date]";
    private static final String COMMAND_WORD = "find";
    
    private static final String MESSAGE_LISTING_SUCCESS = "A total of %s found!";
    private static final String MESSAGE_LISTING_FAILURE = "No task or event found!";
    
    private static CommandDefinition commandDefinition =
            new CommandDefinition(NAME, DESCRIPTION, COMMAND_SYNTAX); 

    public static CommandDefinition getCommandDefinition() {
        return commandDefinition;
    }

    @Override
    public float inputConfidence(String input) {
        return (input.toLowerCase().startsWith(COMMAND_WORD)) ? 1 : 0;
    }
    
    private static Map<String, String[]> getTokenDefinitions() {
        Map<String, String[]> tokenDefinitions = new HashMap<String, String[]>();
        tokenDefinitions.put("default", new String[] {"find"});
        tokenDefinitions.put("eventType", new String[] { "event", "events", "task", "tasks"});
        tokenDefinitions.put("status", new String[] { "complete" , "completed", "uncomplete", "uncompleted"});
        tokenDefinitions.put("time", new String[] { "at", "by", "on", "time" });
        tokenDefinitions.put("timeFrom", new String[] { "from" });
        tokenDefinitions.put("timeTo", new String[] { "to", "before" });
        tokenDefinitions.put("name", new String[] { "name" });
        tokenDefinitions.put("tag", new String [] { "tag" }); //TODO
        return tokenDefinitions;
    }

    @Override
    public void process(String input) throws ParseException {
        
        Map<String, String[]> parsedResult;
        parsedResult = Tokenizer.tokenize(getTokenDefinitions(), input);
        
        HashSet<String> itemNameList = new HashSet<String>();
        
        parseExactFindCommand(parsedResult, itemNameList);
        
        parseName(parsedResult, itemNameList); //parse addtional name enter by user
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
        if (parsedDates == null && listAllStatus == true && listAll == true 
                && parsedResult.size() == 1 && itemNameList.size() == 0) {
            //display error message, no keyword provided
            String disambiguationString = String.format("%s %s %s %s", COMMAND_WORD, "<name>" , 
                    "<complete/incomplete>", "<task/event>");  
            Renderer.renderDisambiguation(disambiguationString, input);
            return ;
        }
        
        LocalDateTime dateOn = null;
        LocalDateTime dateFrom = null;
        LocalDateTime dateTo = null;
        
        if (parsedDates != null) {
            String naturalOn = parsedDates[0];
            String naturalFrom = parsedDates[1];
            String naturalTo = parsedDates[2];
    
            // Parse natural date using Natty.
            dateOn = naturalOn == null ? null : parseNatural(naturalOn); 
            dateFrom = naturalFrom == null ? null : parseNatural(naturalFrom); 
            dateTo = naturalTo == null ? null : parseNatural(naturalTo);
        }
        //setting up view
        setupView(isTask, listAll, isCompleted, listAllStatus, dateOn, dateFrom, dateTo, itemNameList);
        
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
    private void setupView(boolean isTask, boolean listAll, boolean isCompleted,
            boolean listAllStatus, LocalDateTime dateOn, LocalDateTime dateFrom,
            LocalDateTime dateTo, HashSet<String> itemNameList) {
        TodoListDB db = TodoListDB.getInstance();
        List<Task> tasks = null;
        List<Event> events = null;
        // isTask and isEvent = true, list all type
        if (listAll) {
            //no event or task keyword found
            isTask = false;
            tasks = setupTaskView(isCompleted, listAllStatus, dateOn, dateFrom, dateTo, itemNameList, db);
            events = setupEventView(isCompleted, listAllStatus, dateOn, dateFrom, dateTo, itemNameList, db);
        }
        
        if (isTask) {
            tasks = setupTaskView(isCompleted, listAllStatus, dateOn, dateFrom, dateTo, itemNameList, db);
        } else {
            events = setupEventView(isCompleted, listAllStatus, dateOn, dateFrom, dateTo, itemNameList, db);
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
        
        String consoleMessage = MESSAGE_LISTING_FAILURE;
        if (numTasks != 0 || numEvents != 0) {
            consoleMessage = String.format(MESSAGE_LISTING_SUCCESS, formatDisplayMessage(numTasks, numEvents));
        }
        
        Renderer.renderSelected(db, consoleMessage, tasks, events);
       
    }
    
    private String formatDisplayMessage (int numTasks, int numEvents) {
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
    
    private List<Event> setupEventView(boolean isCompleted, boolean listAllStatus, LocalDateTime dateOn, 
            LocalDateTime dateFrom, LocalDateTime dateTo, HashSet<String> itemNameList, TodoListDB db) {
        final LocalDateTime NO_DATE = null;
        if (dateFrom == null && dateTo == null && dateOn == null) {
            if (listAllStatus && itemNameList.size() == 0) {
                System.out.println("error"); //TODO : Nothing found
                return null;
            } else if (listAllStatus && itemNameList.size() != 0) {
                return db.getEventByName(db.getAllEvents(), itemNameList);
            }
            else if (isCompleted) {
                return db.getEventByRangeWithName(NO_DATE, LocalDateTime.now(), itemNameList);
            } else {
                return db.getEventByRangeWithName(LocalDateTime.now(), NO_DATE, itemNameList);
            } 
        } else if (dateOn != null) { //by keyword found
            return db.getEventbyDateWithName(dateOn, itemNameList);
        } else {
            return db.getEventByRangeWithName(dateFrom, dateTo, itemNameList);
        }
    }

    private List<Task> setupTaskView(boolean isCompleted, boolean listAllStatus, LocalDateTime dateOn, 
            LocalDateTime dateFrom, LocalDateTime dateTo, HashSet<String> itemNameList, TodoListDB db) {
        if (dateFrom == null && dateTo == null && dateOn == null) {
            if (listAllStatus && itemNameList.size() == 0) {
                System.out.println("error"); //TODO : Nothing found
                return null;
            } else {
                return db.getTaskByRangeWithName(dateFrom, dateTo, isCompleted, listAllStatus, itemNameList);
            }
        } else if (dateOn != null) { //by keyword found
            return db.getTaskByDateWithStatusAndName(dateOn, isCompleted, listAllStatus, itemNameList);
        } else {
            return db.getTaskByRangeWithName(dateFrom, dateTo, isCompleted, listAllStatus, itemNameList);
        }
    }
    
    /**
     * Extract the name keyword enter by the user and put in the hashset of name keywords
     * @param parsedResult
     */
    private void parseExactFindCommand(Map<String, String[]> parsedResult, HashSet<String> itemNameList) {
        if (parsedResult.get("default")[1] != null) {
            String[] result = parsedResult.get("default")[1].trim().split(" ");
            for (int i = 0; i < result.length; i ++) {
                itemNameList.add(result[i]);
            }
        } 
    }
    
    /**
     * Extract the name keyword enter by the user and put in the hashset of name keywords
     * @param parsedResult
     */
    
    private void parseName(Map<String, String[]> parsedResult, HashSet<String> itemNameList) {
        if (parsedResult.get("name") != null) {
            String[] result = parsedResult.get("name")[1].trim().split(",");
            for (int i = 0; i < result.length; i ++) {
                itemNameList.add(result[i].trim());
            }
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
     * @return true if uncomplete, false if complete
     */
    private boolean parseIsUncomplete (Map<String, String[]> parsedResult) {
        return parsedResult.get("status")[0].contains("uncomplete");
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
     * @return { naturalOn, naturalFrom, naturalTo }
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
        
        if (naturalFrom == null && naturalTo == null && naturalOn == null) {
            // no date found
            return null;
        }
        return new String[] { naturalOn, naturalFrom, naturalTo };
    }
    

}
