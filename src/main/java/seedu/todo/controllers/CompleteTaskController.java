package seedu.todo.controllers;

import seedu.todo.commons.EphemeralDB;
import seedu.todo.controllers.concerns.Renderer;
import seedu.todo.models.CalendarItem;
import seedu.todo.models.Task;
import seedu.todo.models.TodoListDB;

/**
 * Controller to mark a task as completed.
 * 
 * @@author A0139812A
 */
public class CompleteTaskController implements Controller {
    
    private static final String NAME = "Complete Task";
    private static final String DESCRIPTION = "Marks a task as completed, by listed index";
    private static final String COMMAND_SYNTAX = "complete <index>";
    
    private static final String MESSAGE_SUCCESS = "Task marked as complete!";
    private static final String MESSAGE_INVALID_ITEM = "Could not mark task as complete: Invalid index provided!";
    private static final String MESSAGE_CANNOT_COMPLETE_EVENT = "An event cannot be marked as complete!";
    private static final String MESSAGE_ALREADY_COMPLETED = "Could not mark task as complete: Task is already complete!";
    private static final String MESSAGE_COULD_NOT_SAVE = "Could not mark task as complete: An error occured while saving the database file.";
    
    private static CommandDefinition commandDefinition =
            new CommandDefinition(NAME, DESCRIPTION, COMMAND_SYNTAX);

    public static CommandDefinition getCommandDefinition() {
        return commandDefinition;
    }

    @Override
    public float inputConfidence(String input) {
        // TODO
        return input.toLowerCase().startsWith("complete") ? 1 : 0;
    }

    @Override
    public void process(String args) {
        // TODO: Example of last minute work
        
        // Get index.
        int index = Integer.decode(args.replaceFirst("complete", "").trim());
        
        // Get record
        EphemeralDB edb = EphemeralDB.getInstance();
        CalendarItem calendarItem = edb.getCalendarItemsByDisplayedId(index);
        TodoListDB db = TodoListDB.getInstance();
        
        if (calendarItem == null) {
            Renderer.renderIndex(db, MESSAGE_INVALID_ITEM);
            return;
        }
        
        if (!(calendarItem instanceof Task)) {
            Renderer.renderIndex(db, MESSAGE_CANNOT_COMPLETE_EVENT);
            return;
        }
        
        Task task = (Task) calendarItem;
        
        if (task.isCompleted()) {
            Renderer.renderIndex(db, MESSAGE_ALREADY_COMPLETED);
            return;
        }
        
        // Set task as completed
        task.setCompleted();
        boolean hadSaved = db.save();
        
        if (!hadSaved) {
            task.setIncomplete();
            Renderer.renderIndex(db, MESSAGE_COULD_NOT_SAVE);
            return;
        }
        
        // Show success
        Renderer.renderIndex(db, MESSAGE_SUCCESS);
    }

}
