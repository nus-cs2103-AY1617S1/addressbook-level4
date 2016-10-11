package seedu.taskman.logic.commands;

import seedu.taskman.commons.core.Messages;
import seedu.taskman.commons.core.UnmodifiableObservableList;
import seedu.taskman.model.event.Activity;
import seedu.taskman.model.event.UniqueActivityList.ActivityNotFoundException;

/**
 * Deletes a task identified using it's last displayed index from the task man.
 */
public class DeleteCommand extends Command {

    public static final String COMMAND_WORD = "delete";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the task or event identified by the index number used in the last activity listing.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_DELETE_PERSON_SUCCESS = "Deleted Task: %1$s";

    public final int targetIndex;

    public DeleteCommand(int targetIndex) {
        this.targetIndex = targetIndex;
    }


    @Override
    public CommandResult execute() {

        UnmodifiableObservableList<Activity> lastShownList = model.getFilteredActivityList();

        if (lastShownList.size() < targetIndex) {
            indicateAttemptToExecuteIncorrectCommand();
            return new CommandResult(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Activity activityToDelete = lastShownList.get(targetIndex - 1);

        try {
            model.deleteActivity(activityToDelete);
        } catch (ActivityNotFoundException pnfe) {
            assert false : "The target task cannot be missing";
        }

        return new CommandResult(String.format(MESSAGE_DELETE_PERSON_SUCCESS, activityToDelete));
    }

}
