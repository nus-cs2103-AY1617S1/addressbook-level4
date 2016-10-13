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

    @Override
    public float inputConfidence(String input) {
        // TODO
        return (input.startsWith("add")) ? 1 : 0;
    }
    
    private int splitByKeyword(String args, boolean isTask) {
        int indexOfBy = args.lastIndexOf("by");
        int indexOfOn = args.lastIndexOf("on");
        
        // support for event from date to date
        if (!isTask) {
            int indexOfFrom = args.lastIndexOf("from");
            int indexOfTo = args.lastIndexOf("to");
            if (indexOfTo == -1 || indexOfFrom == -1) {
                indexOfFrom = -1;
                indexOfTo = -1;
            } else {
                // to be updated to supported pair value of from and to
                return indexOfFrom;
            }
        }
        
        return Math.max(indexOfBy, indexOfOn);
        
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
        int splitValue = splitByKeyword(args, isTask);
        String naturalDate = "Today";
        String name = args;
        if (splitValue != -1) {
            name = args.substring(0, splitValue);
            naturalDate = args.substring(splitValue);
        }
        
        
        
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
        view.render();
        
    }
    
}
