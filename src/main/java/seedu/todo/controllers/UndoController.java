package seedu.todo.controllers;

import seedu.todo.models.TodoListDB;
import seedu.todo.ui.UiManager;
import seedu.todo.ui.views.IndexView;

public class UndoController implements Controller {
    
    private static final String MESSAGE_SUCCESS = "Successfully undid last command!\nTo redo, type \"redo\".";
    private static final String MESSAGE_FAILURE = "There is no command to undo!";

    @Override
    public float inputConfidence(String input) {
        return input.startsWith("undo") ? 1 : 0;
    }

    @Override
    public void process(String input) {
        TodoListDB db = TodoListDB.getInstance();
        if (!db.undo()) {
            UiManager.updateConsoleMessage(MESSAGE_FAILURE);
            return;
        }
        db = TodoListDB.getInstance();
        
        // Render
        IndexView view = UiManager.loadView(IndexView.class);
        view.tasks = db.getAllTasks();
        view.events = db.getAllEvents();
        UiManager.renderView(view);
        
        // Update console message
        UiManager.updateConsoleMessage(MESSAGE_SUCCESS);
    }
}
