package seedu.todo.controllers;

import seedu.todo.commons.EphemeralDB;
import seedu.todo.models.Task;
import seedu.todo.models.TodoListDB;
import seedu.todo.ui.UiManager;
import seedu.todo.ui.views.IndexView;

public class DestroyController implements Controller {
    
    private static String NAME = "Destroy";
    private static String DESCRIPTION = "Destroys a task/event by listed index";
    private static String COMMAND_SYNTAX = "destroy <index>";
    
    private static CommandDefinition commandDefinition =
            new CommandDefinition(NAME, DESCRIPTION, COMMAND_SYNTAX); 

    public static CommandDefinition getCommandDefinition() {
        return commandDefinition;
    }

    @Override
    public float inputConfidence(String input) {
        // TODO
        return (input.startsWith("delete") || input.startsWith("destroy")) ? 1 : 0;
    }

    @Override
    public void process(String args) {
        // TODO: Example of last minute work
        
        // Get index.
        int index = Integer.decode(args.replaceFirst("(delete|destroy)", "").trim());
        
        // Get record
        EphemeralDB edb = EphemeralDB.getInstance();
        Task task = edb.getTaskByDisplayedId(index);
        TodoListDB db = TodoListDB.getInstance();
        if (task != null) {
            db.destroyTask(task);
        }
        
        // Re-render
        IndexView view = UiManager.loadView(IndexView.class);
        view.tasks = db.getAllTasks();
        view.render();
    }

}
