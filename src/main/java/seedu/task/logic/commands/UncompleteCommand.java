//@@author A0153467Y
package seedu.task.logic.commands;

import seedu.task.commons.core.Messages;
import seedu.task.commons.core.UnmodifiableObservableList;
import seedu.task.commons.exceptions.IllegalValueException;
import seedu.task.model.task.ReadOnlyTask;
import seedu.task.model.task.Task;

public class UncompleteCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "uncomplete";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Unmark the completed task identified by the index number used in the last task listing as not completed.\n"
            + "Parameters: INDEX (must be a positive integer)\n" + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_UNCOMPLETE_TASK_SUCCESS = "Uncompleted Task: %1$s";
    public static final String MESSAGE_ROLLBACK_SUCCESS = "Undo action on un-complete task was executed successfully!";

    public final int targetIndex;

    public UncompleteCommand(int targetIndex) {
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
            Task uncompletedTask = new Task(orginialTask);
            if(uncompletedTask.getComplete()) {
                uncompletedTask.setIsCompleted(false);
            }else {
                return new CommandResult(false, Messages.MESSAGE_INVALID_UNCOMPLETE_TASK);
            }
            
            model.uncompleteTask(orginialTask, uncompletedTask);
        } catch (IllegalValueException e) {
            assert false : "Impossible";
        }
        
        return new CommandResult(true, String.format(MESSAGE_UNCOMPLETE_TASK_SUCCESS, orginialTask));
    }

    @Override
    public CommandResult rollback() {
        assert model != null;
        
        model.rollback();
        
        return new CommandResult(true, MESSAGE_ROLLBACK_SUCCESS);
    }
}
