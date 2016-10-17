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

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Completes an activity using their index: "
            + "the Activity's index.\n"
            + "Parameters: Activity_index. \n"
            + "Example: " + COMMAND_WORD + "1";
    
    public static final String MESSAGE_COMPLETED_ACTIVITY_SUCCESS = "Completed Activity: %1$s";
    
    public final int targetIndex;

    private Activity toBeCompleted;
    
    public CompleteCommand(int targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute() {

        UnmodifiableObservableList<ReadOnlyActivity> lastShownList = model.getFilteredTaskList();

        if (lastShownList.size() < targetIndex) {
            indicateAttemptToExecuteIncorrectCommand();
            return new CommandResult(Messages.MESSAGE_INVALID_ACTIVITY_DISPLAYED_INDEX);
        }

        ReadOnlyActivity activityToComplete = lastShownList.get(targetIndex - 1);
        toBeCompleted = (Activity)activityToComplete;
        
        activityToComplete.setCompleted();

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
