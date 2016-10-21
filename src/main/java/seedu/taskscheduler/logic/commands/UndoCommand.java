package seedu.taskscheduler.logic.commands;

import java.util.EmptyStackException;

import seedu.taskscheduler.commons.core.Messages;
import seedu.taskscheduler.commons.core.UnmodifiableObservableList;
import seedu.taskscheduler.model.Undo;
import seedu.taskscheduler.model.task.ReadOnlyTask;
import seedu.taskscheduler.model.task.Task;
import seedu.taskscheduler.model.task.UniqueTaskList.DuplicateTaskException;
import seedu.taskscheduler.model.task.UniqueTaskList.TaskNotFoundException;

/**
 * Undo a previous task from the task scheduler.
 */

public class UndoCommand extends Command{
	
	public static final String COMMAND_WORD = "undo";

	public static final String MESSAGE_SUCCESS = "Undid %s command: %s";

	public static final String MESSAGE_FAILURE = "There is no previous command to undo!";
   
    @Override
	public CommandResult execute() {
    	
    	assert model != null;
        UnmodifiableObservableList<ReadOnlyTask> lastShownList = model.getFilteredTaskList();
        
    	try {
    		final Undo toUndo = CommandHistory.getMutateCmd();
    		switch (toUndo.getCommandKey()) {
    			case AddCommand.COMMAND_WORD:
    			    performUndoAdd(toUndo);
    				break;
    			case DeleteCommand.COMMAND_WORD:
    			    performUndoDelete(toUndo);
    				break;
    			case RecurCommand.COMMAND_WORD:
    			    performUndoRecur(toUndo);
    			    break;
    			case ClearCommand.COMMAND_WORD:
    			    performUndoClear(toUndo);
    			    break;
    			case MarkCommand.COMMAND_WORD:
    			case EditCommand.COMMAND_WORD:
    			    performUndoModification(lastShownList, toUndo);
    				break;
    		}
            return new CommandResult(String.format(MESSAGE_SUCCESS, toUndo.getCommandKey(), toUndo.getArrayString()));
        } catch (TaskNotFoundException e) {
        	assert false: Messages.MESSAGE_TASK_CANNOT_BE_MISSING;
        	return new CommandResult(Messages.MESSAGE_TASK_CANNOT_BE_MISSING);
		} catch (EmptyStackException e) {
            return new CommandResult(MESSAGE_FAILURE);
		} catch (DuplicateTaskException e) {
            return new CommandResult(MESSAGE_DUPLICATE_TASK);
        }
	}

    private void performUndoModification(UnmodifiableObservableList<ReadOnlyTask> lastShownList, final Undo toUndo)
            throws TaskNotFoundException {
        ReadOnlyTask taskToUndo = lastShownList.get(toUndo.getIndex() - 1);
        model.replaceTask((Task)taskToUndo, toUndo.getTask());
    }

    private void performUndoRecur(final Undo toUndo) throws TaskNotFoundException {
        model.deleteTask(toUndo.getTaskArray());
    }

    private void performUndoDelete(final Undo toUndo) throws TaskNotFoundException {
        model.insertTask(toUndo.getIndex(), toUndo.getTask());
    }

    private void performUndoAdd(final Undo toUndo) throws TaskNotFoundException {
        model.deleteTask(toUndo.getTask());
    }
    
    private void performUndoClear(final Undo toUndo) throws TaskNotFoundException, DuplicateTaskException {
        model.addTask(toUndo.getTaskArray());
    }


}
