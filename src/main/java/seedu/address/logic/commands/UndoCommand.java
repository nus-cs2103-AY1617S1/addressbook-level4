package seedu.address.logic.commands;

import java.util.Stack;

import seedu.address.model.activity.Activity;
import seedu.address.model.activity.UniqueActivityList;
import seedu.address.model.activity.UniqueActivityList.TaskNotFoundException;

/**
 * Undo previous add, delete and edit commands.
 */
public class UndoCommand extends Command {

	public static final String COMMAND_WORD = "undo";

	public static final String MESSAGE_USAGE = COMMAND_WORD;

	public static final String MESSAGE_SUCCESS = "Undo Command: %1$s";
	public static final String MESSAGE_END_OF_UNDO = "There is no more commands to undo";

	public static final String MESSAGE_UNDO_ADD_SUCCESS = "Undo: Adding of new task: %1$s";
	public static final String MESSAGE_UNDO_DELETE_SUCCESS = "Undo: Deleting task: %1$s";
    public static final String MESSAGE_UNDO_EDIT_SUCCESS = "Undo: Editting task from: %1$s\nto: %2$s";
    public static final String MESSAGE_UNDO_DONE_SUCCESS = "Undo: Marked task as Completed: %1$s";
	   
	public static final String MESSAGE_DUPLICATE_TASK = "This task already exists in the Lifekeeper";
	
	@Override
	public CommandResult execute() {

		if (!PreviousCommandsStack.empty()) {
			PreviousCommand toUndo = PreviousCommandsStack.pop();

			switch (toUndo.getCommand()) {

			case AddCommand.COMMAND_WORD:
				return undoAdd(toUndo);

			case DeleteCommand.COMMAND_WORD:
				return undoDelete(toUndo);
				
            case EditCommand.COMMAND_WORD:
                return undoEdit(toUndo);
                
            case DoneCommand.COMMAND_WORD:
                return undoDone(toUndo);
			}

		}
		
			return new CommandResult(MESSAGE_END_OF_UNDO);
	}


	/**
	 * Undo Add command which was previously called
	 */
	private CommandResult undoAdd(PreviousCommand toUndo) {
		Activity taskToDelete = toUndo.getUpdatedTask();

		try {
			model.deleteTask(taskToDelete);
		} catch (TaskNotFoundException tnfe) {
			assert false : "The target task cannot be missing";
		}

		return new CommandResult(String.format(MESSAGE_UNDO_ADD_SUCCESS, taskToDelete));
	}

	/**
	 * Undo Delete command which was previously called
	 */
	private CommandResult undoDelete(PreviousCommand toUndo) {
		Activity taskToAdd = toUndo.getUpdatedTask();

		try {
			model.addTask(taskToAdd);
			
		} catch (UniqueActivityList.DuplicateTaskException e) {
            return new CommandResult(MESSAGE_DUPLICATE_TASK);
        }

		return new CommandResult(String.format(MESSAGE_UNDO_DELETE_SUCCESS, taskToAdd));
	}

	/**
     * Undo Edit command which was previously called
     */
    private CommandResult undoEdit(PreviousCommand toUndo) {
        Activity taskToEdit = toUndo.getUpdatedTask();
        Activity edittedTask = toUndo.getOldTask();
        Activity taskAfterEdit = new Activity(taskToEdit);
        
        try {
            Activity taskBeforeEdit = model.undoEditTask(taskToEdit,edittedTask);
            
            return new CommandResult(String.format(MESSAGE_UNDO_EDIT_SUCCESS, taskBeforeEdit, taskAfterEdit));
        } catch (TaskNotFoundException tnfe) {
            assert false : "The target task to be edited cannot be missing";
        return new CommandResult("not found");
        } catch (UniqueActivityList.DuplicateTaskException e) {
            assert false : "The unedited task should not be a duplicate of the edited task";
        return new CommandResult("duplicate");
        } 
    }

	/**
     * Undo Edit command which was previously called
     */
	private CommandResult undoDone(PreviousCommand toUndo) {
		boolean isComplete = false;
		Activity unmarkedTask = toUndo.getUpdatedTask();
        try {
    		model.markTask(unmarkedTask, isComplete);
    		
            return new CommandResult(String.format(MESSAGE_UNDO_DONE_SUCCESS, unmarkedTask));
        } catch (TaskNotFoundException tnfe) {
            assert false : "The target task to be edited cannot be missing";
        }
		return null;
	}

	
}
