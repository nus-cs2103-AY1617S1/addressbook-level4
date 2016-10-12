package seedu.tasklist.logic.commands;

import seedu.tasklist.commons.core.Messages;
import seedu.tasklist.commons.core.UnmodifiableObservableList;
import seedu.tasklist.commons.exceptions.IllegalValueException;
import seedu.tasklist.model.task.EndTime;
import seedu.tasklist.model.task.Priority;
import seedu.tasklist.model.task.StartTime;
import seedu.tasklist.model.task.Task;
import seedu.tasklist.model.task.TaskDetails;

public class UpdateCommand extends Command {

    public static final String COMMAND_WORD = "update";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Updates the task identified by the index number used in the last task listing.\n"
            + "Parameters: INDEX [TASKNAME] [at/from [START_TIME][START_DATE]] [to/by [END_TIME][END_DATE]] [p/PRIORITY]\n"
            + "Example: " + COMMAND_WORD + " 1 at 13/09/2016 5pm";

    public static final String MESSAGE_UPDATE_TASK_SUCCESS = "Task successfully updated: %1$s";

    private int targetIndex;
    private TaskDetails taskDetails;
    private StartTime startTime;
    // private StartDate startDate;
    private EndTime endTime;
    // private EndDate endDate;
    private Priority priority;

    public UpdateCommand(int targetIndex, String taskDetails, String startTime,
            // String startDate,
            String endTime,
            // String endDate,
            String priority) throws IllegalValueException {
        this.targetIndex = targetIndex-1;
        if (taskDetails != null)
            this.taskDetails = new TaskDetails(taskDetails);
        if (startTime != null)
            this.startTime = new StartTime(startTime);
        // if (startDate != null)
        // this.startDate = new StartDate(startDate);
        if (endTime != null)
            this.endTime = new EndTime(endTime);
        // if (endDate != null)
        // this.endDate = new EndDate(endDate);
        if (priority != null)
            this.priority = new Priority(priority);
    }

    @Override
    public CommandResult execute() {
        UnmodifiableObservableList<Task> lastShownList = model.getModifiableTaskList();
        if (targetIndex >= lastShownList.size()) {
            return new CommandResult(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        } else {
            Task taskToUpdate = lastShownList.get(targetIndex);
/*
            if (!(startTime==null && endTime==null)) {
                taskToUpdate.setStartTime(null);
                taskToUpdate.setEndTime(null);
            }
            */
            if (taskDetails != null)
                taskToUpdate.setTaskDetails(taskDetails);
            if (startTime != null)
                taskToUpdate.setStartTime(startTime);
            // if (startDate != null)
            // taskToUpdate.setStartDate(startDate);
            if (endTime != null)
                taskToUpdate.setEndTime(endTime);
            // if (endDate != null)
            // taskToUpdate.setEndDate(endTime);
            if (priority != null)
                taskToUpdate.setPriority(priority);
            model.updateFilteredList();
            return new CommandResult(String.format(MESSAGE_UPDATE_TASK_SUCCESS, taskToUpdate));
        }
    }
}