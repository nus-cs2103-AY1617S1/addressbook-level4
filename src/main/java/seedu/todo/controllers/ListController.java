package seedu.todo.controllers;

import seedu.todo.commons.util.StringUtil;
import seedu.todo.models.TodoListDB;
import seedu.todo.ui.UiManager;
import seedu.todo.ui.views.IndexView;

/**
 * Controller to list CalendarItems.
 * 
 * @author louietyj
 *
 */
public class ListController implements Controller {
    
    private static String NAME = "List";
    private static String DESCRIPTION = "Lists all tasks and events.";
    private static String COMMAND_SYNTAX = "list";
    
    private static final String MESSAGE_LISTING_SUCCESS = "Listing a total of %d %s and %d %s.";
    
    private static CommandDefinition commandDefinition =
            new CommandDefinition(NAME, DESCRIPTION, COMMAND_SYNTAX); 

    public static CommandDefinition getCommandDefinition() {
        return commandDefinition;
    }

    @Override
    public float inputConfidence(String input) {
        return (input.startsWith("list")) ? 1 : 0;
    }

    @Override
    public void process(String input) {
        TodoListDB db = TodoListDB.getInstance();
        
        // Render
        IndexView view = UiManager.loadView(IndexView.class);
        view.tasks = db.getAllTasks();
        view.events = db.getAllEvents();
        UiManager.renderView(view);

        // Update console message
        int numTasks = db.getAllTasks().size();
        int numEvents = db.getAllEvents().size();
        String consoleMessage = String.format(MESSAGE_LISTING_SUCCESS,
                numTasks, StringUtil.pluralizer(numTasks, "task", "tasks"),
                numEvents, StringUtil.pluralizer(numEvents, "event", "events"));
        UiManager.updateConsoleMessage(consoleMessage);
    }

}
