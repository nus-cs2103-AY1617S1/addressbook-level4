package seedu.todo.controllers;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import com.joestelmach.natty.*;

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

    @Override
    public void process(String args) {
        // TODO: Example of last minute work
        
        args = args.replaceFirst("add", "").trim();
        
        // Task or event
        boolean isTask = true; // Default to task.
        if (args.startsWith("task")) {
            isTask = true;
            args = args.replaceFirst("task", "").trim();
        } else if (args.startsWith("event")) {
            isTask = false;
            args = args.replaceFirst("event", "").trim();
        }
        
        // Parse name and date.
        String[] splitted = args.split("( at | by )", 2);
        String name = splitted[0].trim();
        String naturalDate = splitted[1].trim();
        
        // Parse natural date using Natty.
        Parser parser = new Parser();
        List<DateGroup> groups = parser.parse(naturalDate);
        Date date = groups.get(0).getDates().get(0);
        LocalDateTime ldt = LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
        
        // Create and persist task / event.
        TodoListDB db = TodoListDB.getInstance();
        if (isTask) {
            Task newTask = db.createTask();
            newTask.setName(name);
            newTask.setDueDate(ldt);
            db.save();
        } else {
            Event newEvent = db.createEvent();
            newEvent.setName(name);
            newEvent.setStartDate(ldt);
            newEvent.setEndDate(ldt); // TODO
            db.save();
        }
        
        // Re-render
        IndexView view = UiManager.loadView(IndexView.class);
        view.tasks = db.getAllTasks();
        view.events = db.getAllEvents();
        view.render();
        
    }
    
}
