package seedu.task.logic.commands;

import seedu.task.model.item.ReadOnlyTask;
import seedu.task.model.item.UniqueTaskList.*;
import seedu.taskcommons.core.Messages;
import seedu.taskcommons.core.UnmodifiableObservableList;

/**
 * Deletes a Task identified using it's last displayed index from the address book.
 * @author Tiankai
 */
public class DeleteTaskCommand extends DeleteCommand {

    public static final String MESSAGE_DELETE_TASK_SUCCESS = "Deleted Task: %1$s";

    public DeleteTaskCommand(int targetIndex) {
        this.targetIndex = targetIndex;
    }


    @Override
    public CommandResult execute() {

        UnmodifiableObservableList<ReadOnlyTask> lastShownList = model.getFilteredTaskList();

        if (lastShownList.size() < targetIndex) {
            indicateAttemptToExecuteIncorrectCommand();
            return new CommandResult(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
        }

        ReadOnlyTask taskToDelete = lastShownList.get(targetIndex - 1);

        try {
            model.deleteTask(taskToDelete);
        } catch (TaskNotFoundException tnfe) {
            assert false : "The target task cannot be missing";
        }

        return new CommandResult(String.format(MESSAGE_DELETE_TASK_SUCCESS, taskToDelete));
    }

}
