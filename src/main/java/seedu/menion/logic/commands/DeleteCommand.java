package seedu.menion.logic.commands;

import seedu.menion.commons.core.Messages;
import seedu.menion.commons.core.UnmodifiableObservableList;
import seedu.menion.model.activity.Activity;
import seedu.menion.model.activity.ReadOnlyActivity;
import seedu.menion.model.activity.UniqueActivityList;
import seedu.menion.model.activity.UniqueActivityList.DuplicateTaskException;
import seedu.menion.model.activity.UniqueActivityList.TaskNotFoundException;

/**
 * Deletes a person identified using it's last displayed index from the address book.
 */
public class DeleteCommand extends Command {

    public static final String COMMAND_WORD = "delete";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the activity identified by the index number used in the last activity listing.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_DELETE_ACTIVITY_SUCCESS = "Deleted Activity: %1$s";

    public final int targetIndex;
    public final String targetType;
    
    private Activity toBeDeleted;
    
    public DeleteCommand(String targetType, int targetIndex) {
        this.targetIndex = targetIndex;
        this.targetType = targetType;
    }


    @Override
    public CommandResult execute() {

        UnmodifiableObservableList<ReadOnlyActivity> lastShownList;
        
        System.out.println("what is this activity's target type? It is " + targetType);
        
        if (targetType.equals(" task")) {
            System.out.println("deleting a task");
            lastShownList = model.getFilteredTaskList();
        }
        else if (targetType.equals(" floatingTask")) {
            System.out.println("deleting a floatingTask");
            lastShownList = model.getFilteredFloatingTaskList();
        }
        else if (targetType.equals(" event")) {
            System.out.println("deleting an event");
            lastShownList = model.getFilteredEventList();
        }
        else {
            System.out.println("problem?");
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
            if (targetType.equals(" task")){
                model.deleteTask(activityToDelete);
            }
            else if (targetType.equals(" event")){
                model.deleteEvent(activityToDelete);
            }
            else {
                model.deleteFloatingTask(activityToDelete);
            }
        } catch (TaskNotFoundException pnfe) {
            assert false : "The target activity cannot be missing";
        }

        return new CommandResult(String.format(MESSAGE_DELETE_ACTIVITY_SUCCESS, activityToDelete));
    }

    
    /*
     * undo add back the deleted activity previously
     */
	@Override
	public boolean undo() {
		assert model != null;
        try {
			model.addTask(toBeDeleted);
			return true;
		} catch (DuplicateTaskException e) {
			// there will not be a duplicate task in this case
			return false;
		}
	}

}
