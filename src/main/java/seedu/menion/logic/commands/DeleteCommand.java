package seedu.menion.logic.commands;

import seedu.menion.commons.core.Messages;
import seedu.menion.commons.core.UnmodifiableObservableList;
import seedu.menion.model.ActivityManager;
import seedu.menion.model.ReadOnlyActivityManager;
import seedu.menion.model.activity.Activity;
import seedu.menion.model.activity.ReadOnlyActivity;
import seedu.menion.model.activity.UniqueActivityList;
import seedu.menion.model.activity.UniqueActivityList.DuplicateTaskException;
import seedu.menion.model.activity.UniqueActivityList.ActivityNotFoundException;

//@@author A0146752B
/**
 * Deletes a person identified using it's last displayed index from the address book.
 */
public class DeleteCommand extends Command {

    public static final String COMMAND_WORD = "delete";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the activity identified by the activity type followed by the index number used in the last activity listing.\n"
            + "Parameters: ACTIVITY_TYPE(task,event,floating) INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " floating" + " 1"
            + "Example: " + COMMAND_WORD + " task" + " 2"
            + "Example: " + COMMAND_WORD + " event" + " 3";

    public static final String MESSAGE_DELETE_ACTIVITY_SUCCESS = "Deleted Activity: %1$s";

    public final int targetIndex;
    public final String targetType;
    
    private Activity toBeDeleted;
    
    public DeleteCommand(String targetType, int targetIndex) {
        this.targetIndex = targetIndex;
        this.targetType = targetType.trim();
    }


    @Override
    public CommandResult execute() {
    	assert model != null;
    	
    	storePreviousState();
    	
        UnmodifiableObservableList<ReadOnlyActivity> lastShownList;
        if (targetType.equals(Activity.TASK_TYPE)) {
            lastShownList = model.getFilteredTaskList();
        }
        else if (targetType.equals(Activity.FLOATING_TASK_TYPE)) {
            lastShownList = model.getFilteredFloatingTaskList();
        }
        else if (targetType.equals(Activity.EVENT_TYPE)) {
            lastShownList = model.getFilteredEventList();
        }
        else {
            lastShownList = null;
            indicateAttemptToExecuteIncorrectCommand();
        }

        if (lastShownList.size() < targetIndex) {
            indicateAttemptToExecuteIncorrectCommand();
            return new CommandResult(Messages.MESSAGE_INVALID_ACTIVITY_DISPLAYED_INDEX);
        }

        ReadOnlyActivity activityToDelete = lastShownList.get(targetIndex - 1);
        toBeDeleted = (Activity)activityToDelete;
    	
        try {
            if (targetType.equals(Activity.TASK_TYPE)){
                model.deleteTask(activityToDelete);
            }
            else if (targetType.equals(Activity.EVENT_TYPE)){
                model.deleteEvent(activityToDelete);
            }
            else {
                model.deleteFloatingTask(activityToDelete);
            }
        } catch (ActivityNotFoundException pnfe) {
            assert false : "The target activity cannot be missing";
        }

        return new CommandResult(String.format(MESSAGE_DELETE_ACTIVITY_SUCCESS, activityToDelete));
    }

    //@@author A0139515A
    /**
     * Delete command will store previous activity manager to support undo command
     *
     */
    public void storePreviousState() {
        assert model != null;

        ReadOnlyActivityManager beforeState = new ActivityManager(model.getActivityManager());
    	model.addStateToUndoStack(beforeState);
    }
    //@@author
}
