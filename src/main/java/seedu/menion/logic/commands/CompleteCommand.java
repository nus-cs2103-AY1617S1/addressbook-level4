package seedu.menion.logic.commands;

import seedu.menion.commons.core.Messages;
import seedu.menion.commons.core.UnmodifiableObservableList;
import seedu.menion.model.activity.Activity;
import seedu.menion.model.activity.ReadOnlyActivity;
import seedu.menion.model.activity.UniqueActivityList.DuplicateTaskException;

/**
 * 
 * @author Marx A0139164A
 * Completes an activity given the index and it's activtyType
 */
public class CompleteCommand extends Command {

    public static final String COMMAND_WORD = "complete";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Completes an activity using their type and index: "
            + "\n" + "Parameters: [Activity_Type] + [Activity_Index] \n" + "Example: " + COMMAND_WORD + " "
            + Activity.EVENT_TYPE + " 1";

    public static final String MESSAGE_COMPLETED_ACTIVITY_SUCCESS = "Completed Activity: %1$s";

    public final int targetIndex;
    public final String targetType;

    ReadOnlyActivity activityToComplete;

    public CompleteCommand(String[] splited) {
        this.targetType = splited[1];
        this.targetIndex = Integer.valueOf(splited[2]) - 1;
    }

    @Override
    public CommandResult execute() {

        UnmodifiableObservableList<ReadOnlyActivity> lastShownList;

        if (targetType.equals(Activity.FLOATING_TASK_TYPE)) {
            lastShownList = model.getFilteredFloatingTaskList();
        } else if (targetType.equals(Activity.TASK_TYPE)) {
            lastShownList = model.getFilteredTaskList();
        } else {
            lastShownList = model.getFilteredEventList();
        }

        if (lastShownList.size() < targetIndex) {
            indicateAttemptToExecuteIncorrectCommand();
            return new CommandResult(Messages.MESSAGE_INVALID_ACTIVITY_DISPLAYED_INDEX);
        }

        callCompleteActivity(targetType); // Calls the correct method depending
                                          // on type of activity.
        ReadOnlyActivity activityToComplete = lastShownList.get(targetIndex);


        return new CommandResult(String.format(MESSAGE_COMPLETED_ACTIVITY_SUCCESS, activityToComplete));
    }

    private void callCompleteActivity(String targetType) {

        if (targetType.equals(Activity.FLOATING_TASK_TYPE)) {
            model.completeFloatingTask(targetIndex);
        } else if (targetType.equals(Activity.TASK_TYPE)) {
            model.completeTask(targetIndex);
        } else {
            model.completeEvent(targetIndex);
        }
    }

    /*
     * Complete command supports undo
     */
    @Override
    public boolean undo() {
        assert model != null;

        callUnCompleteActivity(this.targetType);

        return true;
    }

    private void callUnCompleteActivity(String targetType) {

        if (targetType.equals(Activity.FLOATING_TASK_TYPE)) {
            model.UncompleteFloatingTask(targetIndex);
        } else if (targetType.equals(Activity.TASK_TYPE)) {
            model.UncompleteTask(targetIndex);
        } else {
            model.UncompleteEvent(targetIndex);
        }
    }

}
