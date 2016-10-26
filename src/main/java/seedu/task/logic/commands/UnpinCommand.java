//@@author A0153467Y
package seedu.task.logic.commands;

import seedu.task.commons.core.Messages;
import seedu.task.commons.core.UnmodifiableObservableList;
import seedu.task.commons.exceptions.IllegalValueException;
import seedu.task.model.task.ReadOnlyTask;
import seedu.task.model.task.Task;

public class UnpinCommand extends UndoableCommand {
    public static final String COMMAND_WORD = "unpin";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Unpin the pinned task identified by the index number used in the last task listing.\n"
            + "Parameters: INDEX (must be a positive integer)\n" + "Example: " + COMMAND_WORD + " 1 ";

    public static final String MESSAGE_UNPIN_TASK_SUCCESS = "Unpinned Task: %1$s";
    public static final String MESSAGE_ROLLBACK_SUCCESS = "Undo action on unpin task was executed successfully!";

    public final int targetIndex;

    public UnpinCommand(int targetIndex) {
        this.targetIndex = targetIndex;
    }
    
    @Override
    public CommandResult execute() {

        UnmodifiableObservableList<ReadOnlyTask> lastShownList = model.getFilteredTaskList();

        if (lastShownList.size() < targetIndex) {
            indicateAttemptToExecuteIncorrectCommand();
            return new CommandResult(false, Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
        }

        ReadOnlyTask orginialTask = lastShownList.get(targetIndex - 1);
        try {
            Task taskToUnpin = new Task(orginialTask);
            if(taskToUnpin.getImportance()){
                taskToUnpin.setIsImportant(false);
                model.unpinTask(orginialTask, taskToUnpin);
            }else{
                return new CommandResult(false, Messages.MESSAGE_INVALID_UNPIN_TASK);
            }
        } catch (IllegalValueException e) {
            assert false : "Not possible for task on list to have illegal value";
        }

        return new CommandResult(true, String.format(MESSAGE_UNPIN_TASK_SUCCESS, orginialTask));
    }
    
    @Override
    public CommandResult rollback() {
        assert model != null;
        
        model.rollback();
        
        return new CommandResult(true, MESSAGE_ROLLBACK_SUCCESS);
    }
}
