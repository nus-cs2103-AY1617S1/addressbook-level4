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
        tokenDefinitions.put("eventType", new String[] { "event", "task"});
        tokenDefinitions.put("status", new String[] { "complete" , "completed", "uncomplete", "uncompleted"});
        tokenDefinitions.put("time", new String[] { "at", "by", "on", "time" });
        tokenDefinitions.put("timeFrom", new String[] { "from" });
        tokenDefinitions.put("timeTo", new String[] { "to", "before" });
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
        
        // Task or event?
        boolean isTask = true;
        boolean isEvent = true;
        boolean listAll = true;
        boolean isCompleted = true;
        boolean listAllStatus = true;
        String naturalFrom = null;
        String naturalTo = null;
        String naturalOn = null;
        
        //check if required to list all or just task or event
        if (parsedResult.get("eventType") != null) {
            listAll = false;
            if (parsedResult.get("eventType")[0].equals("event")) {
                isTask = false;
            } else {
                isEvent = false;
            }
        }
        
        //check if required to list only completed or uncomplete
        if (parsedResult.get("status") != null) {
            listAllStatus = false;
            if (parsedResult.get("status")[0].equals("uncomplete")) {
                isCompleted = false;
            }
        } 
        
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

        // Parse natural date using Natty.
        LocalDateTime dateOn = naturalOn == null ? null : parseNatural(naturalOn); 
        LocalDateTime dateFrom = naturalFrom == null ? null : parseNatural(naturalFrom); 
        LocalDateTime dateTo = naturalTo == null ? null : parseNatural(naturalTo);
        TodoListDB db = TodoListDB.getInstance();
        IndexView view = UiManager.loadView(IndexView.class);
        // isTask and isEvent = true, list all type
        if (listAll) {
            //no event or task keyword found
            isTask = false;
            isEvent = false;
            //check for date and complete
            if (dateFrom == null && dateTo == null && dateOn == null) {
                view.tasks = db.getAllTasks();
                view.events = db.getAllEvents();
            } else if (dateOn != null) { //by keyword found
                view.tasks = db.getTaskByDate(dateOn, isCompleted, listAllStatus);
                view.events = db.getEventbyDate(dateOn);
            } else {
                view.tasks = db.getTaskByRange(dateFrom, dateTo, isCompleted, listAllStatus);
                view.events = db.getEventByRange(dateFrom, dateTo);
            }
        }
        
        if (isTask) {
            // no date provided
            if (dateFrom == null && dateTo == null && dateOn == null) {
                view.tasks = db.getAllTasks();
            } else if (dateOn != null) { //by keyword found
                view.tasks = db.getTaskByDate(dateOn, isCompleted, listAllStatus);
            } else {
                view.tasks = db.getTaskByRange(dateFrom, dateTo, isCompleted, listAllStatus);
            }
        }
        
        if (isEvent) {
            
        }
        
//        view.tasks = db.getAllTasks();
//        view.events = db.getAllEvents();
        UiManager.renderView(view);
        
        // Update console message
        int numTasks = db.getAllTasks().size();
        int numEvents = db.getAllEvents().size();
        String consoleMessage = String.format(MESSAGE_LISTING_SUCCESS, 
                numTasks, StringUtil.pluralizer(numTasks, "task", "tasks"), 
                numEvents, StringUtil.pluralizer(numEvents, "event", "events"));
        UiManager.updateConsoleMessage(consoleMessage);
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
