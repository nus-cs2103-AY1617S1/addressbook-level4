package seedu.todo.controllers;

import java.util.HashMap;
import java.util.Map;

import seedu.todo.commons.exceptions.UnmatchedQuotesException;
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
    
    private static Map<String, String[]> getTokenDefinitions() {
        Map<String, String[]> tokenDefinitions = new HashMap<String, String[]>();
        tokenDefinitions.put("default", new String[] {"undo"});
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
        
        int numUndo = 1;
        if (parsedResult.get("default") != null) {
            numUndo = Integer.parseInt(parsedResult.get("default")[1]);
        }   
        
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
