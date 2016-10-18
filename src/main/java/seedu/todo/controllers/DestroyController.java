package seedu.todo.controllers;

import seedu.todo.commons.EphemeralDB;
import seedu.todo.controllers.concerns.Renderer;
import seedu.todo.models.CalendarItem;
import seedu.todo.models.Event;
import seedu.todo.models.Task;
import seedu.todo.models.TodoListDB;

/**
 * Controller to destroy a CalendarItem.
 * 
 * @author louietyj
 *
 */
public class DestroyController implements Controller {
    
    private static final String NAME = "Destroy";
    private static final String DESCRIPTION = "Destroys a task/event by listed index";
    private static final String COMMAND_SYNTAX = "destroy <index>";
    
    private static final String MESSAGE_DELETE_SUCCESS = "Item deleted successfully!\n" + "To undo, type \"undo\".";
    private static final String MESSAGE_INDEX_OUT_OF_RANGE = "Could not delete task/event: Invalid index provided!";
    private static final String MESSAGE_MISSING_INDEX = "Please specify the index of the item to delete.";
    private static final String MESSAGE_INDEX_NOT_NUMBER = "Index has to be a number!";
    
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
        
        // Extract param
        String param = args.replaceFirst("(delete|destroy|remove)", "").trim();
        
        if (param.length() <= 0) {
            Renderer.renderDisambiguation(COMMAND_SYNTAX, MESSAGE_MISSING_INDEX);
            return;
        }
        
        assert param.length() > 0;
        
        // Get index.
        int index = 0;
        try {
            index = Integer.decode(param);
        } catch (NumberFormatException e) {
            Renderer.renderDisambiguation(COMMAND_SYNTAX, MESSAGE_INDEX_NOT_NUMBER);
            return;
        }
        
        // Get record
        EphemeralDB edb = EphemeralDB.getInstance();
        CalendarItem calendarItem = edb.getCalendarItemsByDisplayedId(index);
        TodoListDB db = TodoListDB.getInstance();
        
        if (calendarItem == null) {
            Renderer.renderDisambiguation(String.format("destroy %d", index), MESSAGE_INDEX_OUT_OF_RANGE);
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
