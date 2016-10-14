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
        tokenDefinitions.put("time", new String[] { "at", "by", "before", "time" });
        tokenDefinitions.put("timeFrom", new String[] { "from" });
        tokenDefinitions.put("timeTo", new String[] { "to" });
        return tokenDefinitions;
    }

    @Override
    public void process(String input) {
        
        
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
        view.render();
    }
    
}
