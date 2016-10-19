package seedu.menion.logic.commands;

import seedu.menion.commons.core.Messages;
import seedu.menion.commons.core.UnmodifiableObservableList;
import seedu.menion.model.activity.Activity;
import seedu.menion.model.activity.ReadOnlyActivity;
import seedu.menion.model.activity.UniqueActivityList.DuplicateTaskException;

/**
 * 
 * @author Marx A0139164A
 * Edits the variables of an activity given the index and it's activtyType, and the changes.
 */
public class EditCommand extends Command {

    public static final String COMMAND_WORD = "edit";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": edit an activity using their type, index, [Parameters to change] and new changes: "
            + "\n" + "Parameters: [Activity_Type] + [Activity_Index] ['name', 'n:', 'by (Date & Time)', 'from (Date & Time) - to (Date & Time)], + [Changes]\n" 
            + "Example: " + COMMAND_WORD + "task 1 by 10-10-2016 1900 \n"
            + "Example: " + COMMAND_WORD + "task 1 n: write in red ink";

    public static final String MESSAGE_COMPLETED_ACTIVITY_SUCCESS = "Edited Activity to: %1$s";

    public final int targetIndex;
    public final String targetType;
    public final String changes;
    
    ReadOnlyActivity activityToComplete;

    public EditCommand(String[] splited) {
        this.targetType = splited[1];
        this.targetIndex = Integer.valueOf(splited[2]);
        this.changes = splited[3];
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

        return new CommandResult();
    }

    /*
     * Complete command supports undo
     */
    @Override
    public boolean undo() {
        assert model != null;
        return true;
    }

}
