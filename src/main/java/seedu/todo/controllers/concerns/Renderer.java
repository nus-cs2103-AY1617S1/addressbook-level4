package seedu.todo.controllers.concerns;

import java.util.List;

import seedu.todo.models.Event;
import seedu.todo.models.Task;
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
     * Renders the indexView.
     * 
     * @param db
     * @param consoleMessage to be rendered in console, leave null if not needed
     */
    public static void renderIndex(TodoListDB db, String consoleMessage, List<Task> tasks, List<Event> events) {
        IndexView view = UiManager.loadView(IndexView.class);
        
        if (tasks != null) {
            view.tasks = tasks;
        }
        
        if (events != null) {
            view.events = events;
        }
        
        UiManager.renderView(view);
        
        if (consoleMessage != null) {
            UiManager.updateConsoleMessage(consoleMessage);
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
