//@@author A0144919W
package seedu.tasklist.logic.commands;

import seedu.tasklist.commons.core.Messages;
import seedu.tasklist.commons.core.UnmodifiableObservableList;
import seedu.tasklist.commons.exceptions.IllegalValueException;
import seedu.tasklist.model.task.EndTime;
import seedu.tasklist.model.task.Priority;
import seedu.tasklist.model.task.StartTime;
import seedu.tasklist.model.task.Task;
import seedu.tasklist.model.task.TaskDetails;
import seedu.tasklist.model.task.UniqueTaskList;

/**
 * Updates task information such as task details, start time, end time, priority or recurring frequency for a particular task specified by index
 */
public class UpdateCommand extends Command {

	public static final String COMMAND_WORD = "update";

	public static final String MESSAGE_USAGE = COMMAND_WORD
			+ ": Updates the task identified by the index number used in the last task listing.\n"
			+ "Parameters: INDEX [TASKNAME] [at/from [START_TIME][START_DATE]] [to/by [END_TIME][END_DATE]] [p/PRIORITY]\n"
			+ "Example: " + COMMAND_WORD + " 1 at 13/09/2016 5pm";

	public static final String MESSAGE_NOT_CHRONO_TASK = "The start time must be before the end time.";
	public static final String MESSAGE_UPDATE_TASK_SUCCESS = "Task successfully updated: %1$s";
	public static final String MESSAGE_ILLEGAL_VALUE = "Start or end time is invalid!";
    public static final String MESSAGE_OVERLAP = "There is an overlap with other existing task(s).";
    public static final String MESSAGE_DUPLICATE_TASK = "This task already exists in the to-do list.";

	private int targetIndex;
	private TaskDetails taskDetails;
	private String startTime;
	private String endTime;
	private Priority priority;
	private String recurringFrequency;

	public UpdateCommand(int targetIndex, String taskDetails, String startTime, String endTime, String priority, String frequency)
			throws IllegalValueException {
		this.targetIndex = targetIndex - 1;
		if (taskDetails != null)
			this.taskDetails = new TaskDetails(taskDetails.replace("\\", ""));
		if (startTime != null)
			this.startTime = startTime;
		if (endTime != null)
			this.endTime = endTime;
		if (priority != null)
			this.priority = new Priority(priority);
		if (frequency != null)
			this.recurringFrequency = frequency;
	}

	@Override
	public CommandResult execute() {
		UnmodifiableObservableList<Task> lastShownList = model.getListOfTasks();
		if (targetIndex >= lastShownList.size()) {
			return new CommandResult(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
		} else {
			Task taskToUpdate = lastShownList.get(targetIndex);
			try {
			    if (startTime!=null && endTime!=null)
			       isNotChronoTime(new StartTime(startTime), new EndTime(endTime));
			    else if (startTime!=null)
			       isNotChronoTime(new StartTime(startTime), taskToUpdate.getEndTime());
			    else if (endTime!=null)
			       isNotChronoTime(taskToUpdate.getStartTime(), new EndTime(endTime));
			}
			catch (IllegalValueException e) {
			    return new CommandResult(MESSAGE_NOT_CHRONO_TASK);
			}
			try {
			    Task stubTask = new Task(new TaskDetails(taskToUpdate.getTaskDetails().taskDetails), new StartTime(taskToUpdate.getStartTime().toString()), new EndTime(taskToUpdate.getEndTime().toString()), new Priority(taskToUpdate.getPriority().priorityLevel), taskToUpdate.getRecurringFrequency());
			    model.updateTaskUndo(stubTask, taskDetails, new StartTime(startTime), new EndTime(endTime), priority, recurringFrequency);
			    if (model.isDuplicate(stubTask)){
	                return new CommandResult(MESSAGE_DUPLICATE_TASK);
	            }
			    model.updateTask(taskToUpdate, taskDetails, startTime, endTime, priority, recurringFrequency);
			    if (model.isOverlapping(taskToUpdate)) {
	                model.updateFilteredListToShowOverlapping(taskToUpdate);
	                return new CommandResult(String.format(MESSAGE_UPDATE_TASK_SUCCESS + ". " + MESSAGE_OVERLAP, taskToUpdate.getTaskDetails()));
	            }
			    else return new CommandResult(String.format(MESSAGE_UPDATE_TASK_SUCCESS, taskToUpdate.getTaskDetails()));
			} catch (IllegalValueException e) {
				return new CommandResult(MESSAGE_ILLEGAL_VALUE);
			}
		}
	}
	//checks if start time is before end time
	public boolean isNotChronoTime(StartTime starttime, EndTime endtime) throws IllegalValueException{
        if(endtime.time.getTimeInMillis()==0){
            return false;
        }
        boolean finalres;
        finalres = starttime.getAsCalendar().after(endtime.getAsCalendar());
        if(finalres){
            throw new IllegalValueException(MESSAGE_NOT_CHRONO_TASK);
        }
        else{
            return false;
        }
    }
	
}