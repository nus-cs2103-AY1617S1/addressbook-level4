package seedu.todo.controllers;

import seedu.todo.commons.util.StringUtil;
import seedu.todo.models.TodoListDB;
import seedu.todo.ui.UiManager;
import seedu.todo.ui.views.IndexView;

public class RedoController implements Controller {
    
    private static final String MESSAGE_SUCCESS = "Successfully redid last command!\nTo undo, type \"undo\".";
    private static final String MESSAGE_FAILURE = "There is no command to redo!";

    @Override
    public float inputConfidence(String input) {
        return input.startsWith("redo") ? 1 : 0;
    }

    @Override
    public void process(String input) {
        TodoListDB db = TodoListDB.getInstance();
        if (!db.redo()) {
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
