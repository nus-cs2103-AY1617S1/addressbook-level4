package seedu.dailyplanner.logic.commands;

import seedu.dailyplanner.commons.core.EventsCenter;
import seedu.dailyplanner.commons.core.Messages;
import seedu.dailyplanner.commons.core.UnmodifiableObservableList;
import seedu.dailyplanner.commons.events.ui.JumpToListRequestEvent;
import seedu.dailyplanner.model.task.ReadOnlyTask;
import seedu.dailyplanner.model.task.UniqueTaskList.PersonNotFoundException;

/**
 * Selects a person identified using it's last displayed index from the address book.
 */
public class UnpinCommand extends Command {

    public final int targetIndex;

    public static final String COMMAND_WORD = "unpin";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Pins the task identified by the index number used in the last task listing on the pin board.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_COMPLETED_TASK_SUCCESS = "Pinned Task: %1$s";

    public UnpinCommand(int targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute() {

	UnmodifiableObservableList<ReadOnlyTask> lastShownList = model.getFilteredPersonList();

        if (lastShownList.size() < targetIndex) {
            indicateAttemptToExecuteIncorrectCommand();
            return new CommandResult(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }
        
        ReadOnlyTask taskToUnpin = lastShownList.get(targetIndex - 1);

        try {
            model.unpinTask(targetIndex-1);
        } catch (PersonNotFoundException pnfe) {
            assert false : "The target task cannot be missing";
        }
        return new CommandResult(String.format(MESSAGE_COMPLETED_TASK_SUCCESS, taskToUnpin));
    }

}
