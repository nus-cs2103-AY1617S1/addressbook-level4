package seedu.menion.logic.commands;

import java.util.Set;

import seedu.menion.commons.core.Messages;
import seedu.menion.commons.core.UnmodifiableObservableList;
import seedu.menion.model.activity.Activity;
import seedu.menion.model.activity.ReadOnlyActivity;
import seedu.menion.model.activity.UniqueActivityList.TaskNotFoundException;

/**
 * Finds and lists all persons in address book whose name contains any of the argument keywords.
 * Keyword matching is case sensitive.
 */
public class CompleteCommand extends Command {

    public static final String COMMAND_WORD = "complete";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Completes an activity using their type and index: "
            + "\n"
            + "Parameters: [Activity_Type] + [Activity_Index] \n"
            + "Example: " + COMMAND_WORD + " " + Activity.EVENT_TYPE + " 1";
    
    public static final String MESSAGE_COMPLETED_ACTIVITY_SUCCESS = "Completed Activity: %1$s";
    
    public final int targetIndex;
    public final String targetType;
    
    
    public CompleteCommand(String[] splited) {
        this.targetType = splited[1];
        this.targetIndex = Integer.valueOf(splited[2]);
    }

    @Override
    public CommandResult execute() {
        
        UnmodifiableObservableList<ReadOnlyActivity> lastShownList;
        
        if (targetType.equals(Activity.FLOATING_TASK_TYPE)) {
            lastShownList = model.getFilteredFloatingTaskList();
        }
        else if (targetType.equals(Activity.TASK_TYPE)) {
            lastShownList = model.getFilteredTaskList();
        }
        else {
            lastShownList = model.getFilteredEventList();
        }
        
        if (lastShownList.size() < targetIndex) {
            indicateAttemptToExecuteIncorrectCommand();
            return new CommandResult(Messages.MESSAGE_INVALID_ACTIVITY_DISPLAYED_INDEX);
        }

        ReadOnlyActivity activityToComplete = lastShownList.get(targetIndex - 1);

        model.completeActivity(activityToComplete);
        
        return new CommandResult(String.format(MESSAGE_COMPLETED_ACTIVITY_SUCCESS, activityToComplete));
    }

    /*
     * Complete command supports undo
     */
    @Override
    public boolean undo() {
        return true;
    }

}
