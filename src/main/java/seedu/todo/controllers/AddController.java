package seedu.todo.controllers;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import com.joestelmach.natty.*;

import seedu.todo.models.Task;
import seedu.todo.models.TodoListDB;

public class AddController implements Controller {

    @Override
    public float inputConfidence(String input) {
        // TODO
        return (input.startsWith("add")) ? 1 : 0;
    }

    @Override
    public void process(String args) {
        // TODO: Example of last minute work
        
        args = args.replaceFirst("add", "");
        
        // Parse name and date.
        String[] splitted = args.split("( at | by )", 2);
        String name = splitted[0].trim();
        String naturalDate = splitted[1].trim();
        
        // Parse natural date using Natty.
        Parser parser = new Parser();
        List<DateGroup> groups = parser.parse(naturalDate);
        Date date = groups.get(0).getDates().get(0);
        LocalDateTime ldt = LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
        
        // Create and persist task.
        TodoListDB db = TodoListDB.getInstance();
        Task newTask = db.createTask();
        newTask.setName(name);
        newTask.setCalendarDT(ldt);
        db.save();
        
        // TODO: Render view.
        
    }
    
}
