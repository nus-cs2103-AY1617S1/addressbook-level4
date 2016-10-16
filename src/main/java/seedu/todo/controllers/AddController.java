package seedu.todo.controllers;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.joestelmach.natty.*;

import seedu.todo.commons.exceptions.UnmatchedQuotesException;
import seedu.todo.models.Event;
import seedu.todo.models.Task;
import seedu.todo.models.TodoListDB;
import seedu.todo.ui.UiManager;
import seedu.todo.ui.views.IndexView;

public class AddController implements Controller {
    
    private static String NAME = "Add";
    private static String DESCRIPTION = "Adds a task / event to the to-do list.";
    private static String COMMAND_SYNTAX = "add <task> by <deadline> || add <event> at <time>";
    
    private static final String MESSAGE_ADD_SUCCESS = "Item successfully added!";
    
    private static CommandDefinition commandDefinition =
            new CommandDefinition(NAME, DESCRIPTION, COMMAND_SYNTAX); 

    public static CommandDefinition getCommandDefinition() {
        return commandDefinition;
    }

    @Override
    public float inputConfidence(String input) {
        // TODO
        return (input.startsWith("add")) ? 1 : 0;
    }
    
    private static Map<String, String[]> getTokenDefinitions() {
        Map<String, String[]> tokenDefinitions = new HashMap<String, String[]>();
        tokenDefinitions.put("default", new String[] {"add"});
        tokenDefinitions.put("eventType", new String[] { "event", "task" });
        tokenDefinitions.put("time", new String[] { "at", "by", "on", "before", "time" });
        tokenDefinitions.put("timeFrom", new String[] { "from" });
        tokenDefinitions.put("timeTo", new String[] { "to" });
        return tokenDefinitions;
    }

    @Override
    public void process(String input) {
        
        Map<String, String[]> parsedResult;
        try {
            parsedResult = Tokenizer.tokenize(getTokenDefinitions(), input);            
        } catch (UnmatchedQuotesException e) {
            System.out.println("Unmatched quote!");
            return;
        }
        
        // Task or event?
        boolean isTask = true;
        if (parsedResult.get("eventType") != null && parsedResult.get("eventType")[0].equals("event"))
            isTask = false;
        
        // Name - Disambiguate if null.
        String name = null;
        if (parsedResult.get("default") != null && parsedResult.get("default")[1] != null)
            name = parsedResult.get("default")[1];
        if (parsedResult.get("eventType") != null && parsedResult.get("eventType")[1] != null)
            name = parsedResult.get("eventType")[1];
        if (name == null) {
            renderDisambiguation(parsedResult);
            return;
        }
        
        // Time - Disambiguate if "to" without "from" OR "task" and two timings.
        String naturalFrom = null;
        String naturalTo = null;
        setTime: {
            if (parsedResult.get("time") != null && parsedResult.get("time")[1] != null) {
                naturalFrom = parsedResult.get("time")[1];
                break setTime;
            }
            if (parsedResult.get("timeFrom") != null && parsedResult.get("timeFrom")[1] != null)
                naturalFrom = parsedResult.get("timeFrom")[1];
            if (parsedResult.get("timeTo") != null && parsedResult.get("timeTo")[1] != null)
                naturalTo = parsedResult.get("timeTo")[1];
        }
        if ((naturalFrom == null && naturalTo != null) || (isTask && naturalTo != null)) {
            renderDisambiguation(parsedResult);
            return;
        }
        
        // Parse natural date using Natty.
        LocalDateTime dateFrom = naturalFrom == null ? null : parseNatural(naturalFrom); 
        LocalDateTime dateTo = naturalTo == null ? null : parseNatural(naturalTo);
        
        // Create and persist task / event.
        TodoListDB db = TodoListDB.getInstance();
        if (isTask) {
            Task newTask = db.createTask();
            newTask.setName(name);
            newTask.setDueDate(dateFrom);
        } else {
            Event newEvent = db.createEvent();
            newEvent.setName(name);
            newEvent.setStartDate(dateFrom);
            newEvent.setEndDate(dateTo);
        }
        db.save();
        
        // Re-render
        IndexView view = UiManager.loadView(IndexView.class);
        view.tasks = db.getAllTasks();
        view.events = db.getAllEvents();
        UiManager.renderView(view);
        UiManager.updateConsoleMessage(MESSAGE_ADD_SUCCESS);
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
        return ldt;
    }
    
    private void renderDisambiguation(Map<String, String[]> parsedResult) {
        System.out.println("Disambiguate!");
    }
    
}
