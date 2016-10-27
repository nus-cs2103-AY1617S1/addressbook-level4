package seedu.todo.controllers;

import java.util.HashMap;
import java.util.Map;

import seedu.todo.commons.exceptions.ParseException;
import seedu.todo.commons.util.StringUtil;
import seedu.todo.controllers.concerns.Renderer;
import seedu.todo.controllers.concerns.Tokenizer;
import seedu.todo.models.TodoListDB;
import seedu.todo.ui.UiManager;

/**
 * Controller to redo a database commit.
 * 
 * @@author A0093907W
 */
public class RedoController implements Controller {
    
    private static final String MESSAGE_SUCCESS = "Successfully redid %s %s!\nTo undo, type \"undo\".";
    private static final String MESSAGE_MULTIPLE_FAILURE = "We cannot redo %s %s! At most, you can redo %s %s.";
    private static final String MESSAGE_FAILURE = "There is no command to redo!";

    @Override
    public float inputConfidence(String input) {
        return input.toLowerCase().startsWith("redo") ? 1 : 0;
    }
    
    /**
     * Get the token definitions for use with <code>tokenizer</code>.<br>
     * This method exists primarily because Java does not support HashMap
     * literals...
     * 
     * @return tokenDefinitions
     */
    private static Map<String, String[]> getTokenDefinitions() {
        Map<String, String[]> tokenDefinitions = new HashMap<String, String[]>();
        tokenDefinitions.put("default", new String[] {"redo"});
        return tokenDefinitions;
    }

    @Override
    public void process(String input) throws ParseException {
        
        Map<String, String[]> parsedResult;
        parsedResult = Tokenizer.tokenize(getTokenDefinitions(), input);
        
        int numRedo = 1;
        if (parsedResult.get("default")[1] != null) {
            numRedo = Integer.parseInt(parsedResult.get("default")[1]);
        }
        
        // We don't really have a nice way to support SQL transactions, so yeah >_<
        TodoListDB db = TodoListDB.getInstance();
        if (numRedo <= 0 || db.redoSize() <= 0) {
            UiManager.updateConsoleMessage(MESSAGE_FAILURE);
            return;
        }
        if (db.redoSize() < numRedo) {
            UiManager.updateConsoleMessage(String.format(MESSAGE_MULTIPLE_FAILURE,
                    numRedo, StringUtil.pluralizer(numRedo, "command", "commands"),
                    db.redoSize(), StringUtil.pluralizer(db.redoSize(), "command", "commands")));
            return;
        }
        for (int i = 0; i < numRedo; i++) {
            if (!db.redo()) {
                UiManager.updateConsoleMessage(MESSAGE_FAILURE);
                return;
            }
        }
        db = TodoListDB.getInstance();
        
        // Render
        Renderer.renderIndex(db, String.format(MESSAGE_SUCCESS, numRedo,
                StringUtil.pluralizer(numRedo, "command", "commands")));
    }
}
