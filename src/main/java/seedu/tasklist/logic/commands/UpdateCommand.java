package seedu.tasklist.logic.commands;

import seedu.tasklist.commons.core.Messages;
import seedu.tasklist.commons.core.UnmodifiableObservableList;
import seedu.tasklist.commons.exceptions.IllegalValueException;
import seedu.tasklist.model.tag.UniqueTagList;
import seedu.tasklist.model.task.EndTime;
import seedu.tasklist.model.task.Priority;
import seedu.tasklist.model.task.StartTime;
import seedu.tasklist.model.task.Task;
import seedu.tasklist.model.task.TaskDetails;
import seedu.tasklist.model.task.UniqueTaskList;

public class UpdateCommand extends Command {

	public static final String COMMAND_WORD = "update";

	public static final String MESSAGE_USAGE = COMMAND_WORD
			+ ": Updates the task identified by the index number used in the last task listing.\n"
			+ "Parameters: INDEX [TASKNAME] [at/from [START_TIME][START_DATE]] [to/by [END_TIME][END_DATE]] [p/PRIORITY]\n"
			+ "Example: " + COMMAND_WORD + " 1 at 13/09/2016 5pm";


	public static final String MESSAGE_UPDATE_TASK_SUCCESS = "Task successfully updated: %1$s";
	public static final String MESSAGE_DUPLICATE_TASK = "This task already exists in the to-do list.";

	private int targetIndex;
	private TaskDetails taskDetails;
	private StartTime startTime;
	private EndTime endTime;
	private Priority priority;
	private UniqueTagList tags;

	public UpdateCommand(int targetIndex, String taskDetails, String startTime, String endTime, String priority, UniqueTagList tags)
			throws IllegalValueException {
		this.targetIndex = targetIndex - 1;
		if (taskDetails != null)
			this.taskDetails = new TaskDetails(taskDetails);
		if (startTime != null)
			this.startTime = new StartTime(startTime);
		if (endTime != null)
			this.endTime = new EndTime(endTime);
		if (priority != null)
			this.priority = new Priority(priority);
		this.tags = new UniqueTagList(tags);
	}

	@Override
	public CommandResult execute() {
		UnmodifiableObservableList<Task> lastShownList = model.getListOfTasks();
		if (targetIndex >= lastShownList.size()) {
			return new CommandResult(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
		} else {
			Task taskToUpdate = lastShownList.get(targetIndex);

			if(taskToUpdate.isFloating()){
				Task.floatCounter--;
			}
			if (endTime != null){
				if(taskToUpdate.getEndTime().endTime.before(endTime.endTime)){
					Task.overdueCounter--;
				}
			}
			try {
				model.updateTask(taskToUpdate, taskDetails, startTime, endTime, priority, tags);
				return new CommandResult(String.format(MESSAGE_UPDATE_TASK_SUCCESS, taskToUpdate));
			} catch (UniqueTaskList.DuplicateTaskException e) {
				return new CommandResult(MESSAGE_DUPLICATE_TASK);
			}
		}
	}
}