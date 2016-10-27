package seedu.address.logic.commands;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.UnmodifiableObservableList;
import seedu.address.model.activity.Activity;
import seedu.address.model.activity.ReadOnlyActivity;
import seedu.address.model.activity.UniqueActivityList.DuplicateTaskException;
import seedu.address.model.activity.UniqueActivityList.TaskNotFoundException;
//@@author A0125097A
/**
 * Marks the completion of a task identified using it's last displayed index
 * from the Lifekeeper.
 */
public class DoneCommand extends Command {

	public static final String COMMAND_WORD = "done";

	public static final String MESSAGE_USAGE = COMMAND_WORD
			+ ": Marks the completion of the task identified by the index number shown in the last task listing.\n"
			+ "Parameters: INDEX (must be a positive integer)\n" + "Example: " + COMMAND_WORD + " 1";

	public static final String MESSAGE_DONE_TASK_SUCCESS = "Marked task as Completed: %1$s";
	public static final String MESSAGE_TASK_COMPLETED = "Task is already Completed";
	public static final String MESSAGE_EVENT_INVALID = "Events cannot be marked as completed";
    
	
	public final int targetIndex;

	public DoneCommand(int targetIndex) {
		this.targetIndex = targetIndex;
	}

	@Override
	public CommandResult execute() {

		UnmodifiableObservableList<ReadOnlyActivity> lastShownList = model.getFilteredTaskList();

		if (lastShownList.size() < targetIndex) {
			indicateAttemptToExecuteIncorrectCommand();
			return new CommandResult(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
		}

		ReadOnlyActivity taskToMark = lastShownList.get(targetIndex - 1);

		if(taskToMark.getClass().getSimpleName().equalsIgnoreCase("event")){
		    return new CommandResult(MESSAGE_EVENT_INVALID);
		}
		    
		
		if (taskToMark.getCompletionStatus() == false) {

			Activity unmarkedTask = Activity.create(taskToMark);
			
			boolean isComplete = true;
			try {
				model.markTask(unmarkedTask, isComplete);

				PreviousCommand doneCommand = new PreviousCommand(COMMAND_WORD, unmarkedTask);
				PreviousCommandsStack.push(doneCommand);

			} catch (TaskNotFoundException tnfe) {
				assert false : "The target task cannot be missing";
			}
			return new CommandResult(String.format(MESSAGE_DONE_TASK_SUCCESS, taskToMark));

		} else {
			return new CommandResult(String.format(MESSAGE_TASK_COMPLETED));
		}
	}
}

