package seedu.todo.controllers;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.joestelmach.natty.DateGroup;
import com.joestelmach.natty.Parser;

import seedu.todo.commons.EphemeralDB;
import seedu.todo.models.Task;
import seedu.todo.models.TodoListDB;
import seedu.todo.ui.UiManager;
import seedu.todo.ui.views.IndexView;

public class UpdateController implements Controller {

    @Override
    public float inputConfidence(String input) {
        // TODO
        return (input.startsWith("update")) ? 1 : 0;
    }

    @Override
    public void process(String args) {
        // TODO: Example of last minute work
        
        args = args.replaceFirst("update", "").trim();
        
        // Get index.
        System.out.println(args);
        
        Matcher matcher = Pattern.compile("^\\d+").matcher(args);
        matcher.find();
        String indexStr = matcher.group();
        int index = Integer.decode(indexStr.trim());
        
        // Parse name and date.
        args = args.replaceFirst(indexStr, "").trim();
        String[] splitted = args.split("( at | by )", 2);
        String name = splitted[0].trim();
        String naturalDate = splitted[1].trim();
        
        // Parse natural date using Natty.
        Parser parser = new Parser();
        List<DateGroup> groups = parser.parse(naturalDate);
        Date date = groups.get(0).getDates().get(0);
        LocalDateTime ldt = LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
        
        // Get record
        EphemeralDB edb = EphemeralDB.getInstance();
        Task task = edb.getTaskByDisplayedId(index);
        TodoListDB db = TodoListDB.getInstance();
        if (task != null) {
            task.setName(name);
            task.setCalendarDT(ldt);
            db.save();
        }
        
        // Re-render
        UiManager ui = UiManager.getInstance();
        IndexView view = ui.loadView(IndexView.class);
        view.tasks = db.getAllTasks();
        view.render();
        
    }
}
