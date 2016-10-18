package seedu.todo.controllers.concerns;

import seedu.todo.models.TodoListDB;
import seedu.todo.ui.UiManager;
import seedu.todo.ui.views.ConfigView;
import seedu.todo.ui.views.IndexView;

/**
 * Class to store rendering methods to be shared across controllers.
 * 
 * @author louietyj
 *
 */
public class Renderer {
    
    /**
     * Renders an error message in both the console and the input field, leave null or empty string if not needed.
     * 
     * @param consoleMessage       Message to be rendered in the console
     * @param consoleInputValue    Value to display in the input field
     */
    public static void showConsoleError(String consoleMessage, String consoleInputValue) {
        if (consoleMessage != null && consoleMessage.length() > 0) {
            UiManager.updateConsoleMessage(consoleMessage);
        }
        
        if (consoleInputValue != null && consoleInputValue.length() > 0) {
            UiManager.updateConsoleInputValue(consoleInputValue);
        }
    }
    
    /**
     * Renders the indexView.
     * 
     * @param db
     * @param consoleMessage to be rendered in console, leave null if not needed
     */
    public static void renderIndex(TodoListDB db, String consoleMessage) {
        IndexView view = UiManager.loadView(IndexView.class);
        view.tasks = db.getAllTasks();
        view.events = db.getAllEvents();
        UiManager.renderView(view);
        
        if (consoleMessage != null) {
            UiManager.updateConsoleMessage(consoleMessage);
        }
    }
    
    /**
     * Renders the ConfigView.
     * 
     * @param consoleMessage to be rendered in console, leave null if not needed
     */
    public static void renderConfig(String consoleMessage) {
        ConfigView view = UiManager.loadView(ConfigView.class);
        UiManager.renderView(view);
        
        if (consoleMessage != null) {
            UiManager.updateConsoleMessage(consoleMessage);
        }
    }

}
