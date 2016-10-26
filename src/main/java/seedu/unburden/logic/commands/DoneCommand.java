package seedu.unburden.logic.commands;

import seedu.unburden.commons.core.Messages;
import seedu.unburden.commons.core.UnmodifiableObservableList;
import seedu.unburden.model.task.ReadOnlyTask;
import seedu.unburden.model.task.UniqueTaskList.TaskNotFoundException;

/**
 * Deletes a person identified using it's last displayed index from the address book.
 */
public class DoneCommand extends Command {

    public static final String COMMAND_WORD = "done";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Marks the task identified by the index number used in the last task listing as done.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_DELETE_TASK_SUCCESS = "Well done! Task Done!";

    public final int targetIndex;

    public DoneCommand(int targetIndex) {
        this.targetIndex = targetIndex;
    }


    @Override
    public CommandResult execute() {

        UnmodifiableObservableList<ReadOnlyTask> lastShownList = model.getFilteredTaskList();

        if (lastShownList.size() < targetIndex) {
            indicateAttemptToExecuteIncorrectCommand();
            return new CommandResult(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
        }

        ReadOnlyTask taskToDone = lastShownList.get(targetIndex - 1);

        model.saveToPrevLists();
        model.doneTask(taskToDone, true);

        return new CommandResult(String.format(MESSAGE_DELETE_TASK_SUCCESS, taskToDone));
    }

}
