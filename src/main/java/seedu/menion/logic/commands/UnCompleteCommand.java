//@@author A0139164A
package seedu.menion.logic.commands;

import seedu.menion.commons.core.Messages;
import seedu.menion.commons.core.UnmodifiableObservableList;
import seedu.menion.model.ActivityManager;
import seedu.menion.model.ReadOnlyActivityManager;
import seedu.menion.model.activity.Activity;
import seedu.menion.model.activity.ReadOnlyActivity;
/**
 * Finds and lists all persons in address book whose name contains any of the argument keywords.
 * Keyword matching is case sensitive.
 */
public class UnCompleteCommand extends Command {

    public static final String COMMAND_WORD = "uncomplete";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": UnCompletes an activity using their type and index: "
            + "\n"
            + "Parameters: [Activity_Type] + [Activity_Index] \n"
            + "Example: " + COMMAND_WORD + " " + Activity.EVENT_TYPE + " 1";
    public static final String INDEX_MISSING_MESSAGE = "Oh no, your index is missing! Try: " + COMMAND_WORD + " [Activity Type] [Activity_index]" 
            + "\n" + "Example: " + COMMAND_WORD + " task 1";
    public static final String MESSAGE_UNCOMPLETED_ACTIVITY_SUCCESS = "UnCompleted Activity: %1$s";
    public static final String MESSAGE_ALREADY_UNCOMPLETED = "Menion has already uncompleted this activity!";
    
    public final int targetIndex;
    public final String targetType;
    ReadOnlyActivity activityToUncomplete;
    
    public UnCompleteCommand(String[] splited) {
        this.targetType = splited[1];
        this.targetIndex = Integer.valueOf(splited[2]) - 1;
    }

    @Override
    public CommandResult execute() {
        assert model != null;
        
        storePreviousState();
        
        UnmodifiableObservableList<ReadOnlyActivity> lastShownList;

        if (targetType.equals(Activity.FLOATING_TASK_TYPE)) {
            lastShownList = model.getFilteredFloatingTaskList();
            activityToUncomplete = lastShownList.get(targetIndex);
        }
        else if (targetType.equals(Activity.TASK_TYPE)) {
            lastShownList = model.getFilteredTaskList();
            activityToUncomplete = lastShownList.get(targetIndex);
        }
        else {
            lastShownList = model.getFilteredEventList();
            activityToUncomplete = lastShownList.get(targetIndex);
        }
        
        if (lastShownList.size() <= targetIndex || targetIndex < 0) {
            indicateAttemptToExecuteIncorrectCommand();
            return new CommandResult(Messages.MESSAGE_INVALID_ACTIVITY_DISPLAYED_INDEX);
        }
        if (!activityToUncomplete.getActivityStatus().status) {
            indicateAttemptToExecuteIncorrectCommand();
            return new CommandResult(MESSAGE_ALREADY_UNCOMPLETED);
        }
        callUnCompleteActivity(targetType); // Calls the correct method depending on type of activity.
        activityToUncomplete = lastShownList.get(targetIndex);
        
        return new CommandResult(String.format(MESSAGE_UNCOMPLETED_ACTIVITY_SUCCESS, activityToUncomplete));
    }

    private void callUnCompleteActivity(String targetType) {
        
        if (targetType.equals(Activity.FLOATING_TASK_TYPE)) {
            model.UncompleteFloatingTask(targetIndex);
        }
        else if (targetType.equals(Activity.TASK_TYPE)) {
            model.UncompleteTask(targetIndex);
        }
        else {
            model.UncompleteEvent(targetIndex);
        }
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
    
    //@@author A0139515A
    /**
     * Uncomplete command will store previous activity manager to support undo command
     *
     */
    public void storePreviousState() {
        assert model != null;

        ReadOnlyActivityManager beforeState = new ActivityManager(model.getActivityManager());
    	model.addStateToUndoStack(beforeState);
    }
    //@@author
}
