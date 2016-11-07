package seedu.todo.controllers;

import seedu.todo.commons.EphemeralDB;
import seedu.todo.commons.core.CommandDefinition;
import seedu.todo.controllers.concerns.Renderer;
import seedu.todo.models.CalendarItem;
import seedu.todo.models.Task;
import seedu.todo.models.TodoListDB;

// @@author A0139812A
/**
 * Controller to mark a task as uncompleted.
 */
public class UncompleteTaskController extends Controller {
    
    private static final String NAME = "Uncomplete Task";
    private static final String DESCRIPTION = "Marks a task as incomplete, by listed index";
    private static final String COMMAND_SYNTAX = "uncomplete <index>";
    private static final String COMMAND_KEYWORD = "uncomplete";

    public static final String MESSAGE_SUCCESS = "Task marked as incomplete!";
    public static final String MESSAGE_MISSING_INDEX = "Please specify the index of the item to delete.";
    public static final String MESSAGE_INDEX_NOT_NUMBER = "Index has to be a number!";
    public static final String MESSAGE_INVALID_ITEM = "Could not mark task as incomplete: Invalid index provided!";
    public static final String MESSAGE_CANNOT_UNCOMPLETE_EVENT = "An event cannot be marked as incomplete!";
    public static final String MESSAGE_ALREADY_INCOMPLETE = "Could not mark task as incomplete: Task is not completed!";
    public static final String MESSAGE_COULD_NOT_SAVE = "Could not mark task as incomplete: An error occured while saving the database file.";
    
    
    private static CommandDefinition commandDefinition =
            new CommandDefinition(NAME, DESCRIPTION, COMMAND_SYNTAX, COMMAND_KEYWORD);

    @Override
    public CommandDefinition getCommandDefinition() {
        return commandDefinition;
    }

    @Override
    public void process(String args) {
        // Get index.
        String param = args.replaceFirst(COMMAND_KEYWORD, "").trim();
        
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
            Renderer.renderIndex(db, MESSAGE_INVALID_ITEM);
            return;
        }
        
        if (!(calendarItem instanceof Task)) {
            Renderer.renderIndex(db, MESSAGE_CANNOT_UNCOMPLETE_EVENT);
            return;
        }
        
        Task task = (Task) calendarItem;
        
        if (!task.isCompleted()) {
            Renderer.renderIndex(db, MESSAGE_ALREADY_INCOMPLETE);
            return;
        }
        
        // Set task as completed
        task.setIncomplete();
        boolean hadSaved = db.save();
        
        if (!hadSaved) {
            task.setCompleted();
            Renderer.renderIndex(db, MESSAGE_COULD_NOT_SAVE);
            return;
        }
        
        // Show success message
        Renderer.renderIndex(db, MESSAGE_SUCCESS);
    }

}
