package seedu.todo.controllers;

import seedu.todo.commons.EphemeralDB;
import seedu.todo.controllers.concerns.Renderer;
import seedu.todo.models.CalendarItem;
import seedu.todo.models.Event;
import seedu.todo.models.Task;
import seedu.todo.models.TodoListDB;
import seedu.todo.ui.UiManager;
import seedu.todo.ui.views.IndexView;

/**
 * Controller to destroy a CalendarItem.
 * 
 * @author louietyj
 *
 */
public class DestroyController implements Controller {
    
    private static String NAME = "Destroy";
    private static String DESCRIPTION = "Destroys a task/event by listed index";
    private static String COMMAND_SYNTAX = "destroy <index>";
    
    private static String MESSAGE_DELETE_SUCCESS = "Item deleted successfully!\n" + "To undo, type \"undo\".";
    private static String MESSAGE_INVALID_CALENDARITEM = "Could not delete task/event: invalid index provided!";
    
    private static CommandDefinition commandDefinition =
            new CommandDefinition(NAME, DESCRIPTION, COMMAND_SYNTAX); 

    public static CommandDefinition getCommandDefinition() {
        return commandDefinition;
    }

    @Override
    public float inputConfidence(String input) {
        // TODO
        return (input.toLowerCase().startsWith("delete") || input.startsWith("destroy")) || input.startsWith("remove") ? 1 : 0;
    }

    @Override
    public void process(String args) {
        // TODO: Example of last minute work
        
        // Get index.
        int index = Integer.decode(args.replaceFirst("(delete|destroy|remove)", "").trim());
        
        // Get record
        EphemeralDB edb = EphemeralDB.getInstance();
        CalendarItem calendarItem = edb.getCalendarItemsByDisplayedId(index);
        TodoListDB db = TodoListDB.getInstance();
        
        if (calendarItem == null) {
            UiManager.updateConsoleMessage(MESSAGE_INVALID_CALENDARITEM);
            return;
        }
        
        assert calendarItem != null;
        
        if (calendarItem instanceof Task) {
            db.destroyTask((Task) calendarItem);
        } else {
            db.destroyEvent((Event) calendarItem);
        }
        
        // Re-render
        Renderer.renderIndex(db, MESSAGE_DELETE_SUCCESS);
    }

}
