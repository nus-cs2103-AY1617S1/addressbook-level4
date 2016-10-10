package seedu.tasklist.logic.commands;

import seedu.tasklist.commons.core.Messages;
import seedu.tasklist.commons.core.UnmodifiableObservableList;
import seedu.tasklist.model.task.EndTime;
import seedu.tasklist.model.task.Priority;
import seedu.tasklist.model.task.ReadOnlyTask;
import seedu.tasklist.model.task.StartTime;
import seedu.tasklist.model.task.Task;
import seedu.tasklist.model.task.TaskDetails;
import seedu.tasklist.model.task.UniqueTaskList.PersonNotFoundException;

public class UpdateCommand extends Command {

    public static final String COMMAND_WORD = "update";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Updates the task identified by the index number used in the last task listing.\n"
            + "Parameters: INDEX [TASKNAME] [at/from [START_TIME][START_DATE]] [to/by [END_TIME][END_DATE]] [p/PRIORITY]\n" + "Example: " + COMMAND_WORD + " 1 at 13/09/2016 5pm";

    
    public static final String MESSAGE_UPDATE_TASK_SUCCESS = "Task successfully updated: %1$s";
    
    
    private int targetIndex;
    private TaskDetails taskDetails;
    private StartTime startTime;
//  private StartDate startDate;
    private EndTime endTime;
//  private EndDate endDate;
    private Priority priority;
    
    public UpdateCommand(int targetIndex, 
            TaskDetails taskDetails, 
            StartTime startTime, 
            //StartDate startDate,
            EndTime endTime,
            //EndDate endDate,
            Priority priority){
        this.targetIndex = targetIndex;
        this.taskDetails = taskDetails;
        this.startTime = startTime;
//      this.startDate = startDate;
        this.endTime = endTime;
//      this.endDate = endDate;
        this.priority = priority;
    }
    
    @Override
    public CommandResult execute() {
        UnmodifiableObservableList<Task> lastShownList = model.getModifiableTaskList();
        if(targetIndex >= lastShownList.size() || targetIndex < 1){
            return new CommandResult(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }
        else{
            Task taskToUpdate = lastShownList.get(targetIndex);
            taskToUpdate.setTaskDetails(taskDetails);
            taskToUpdate.setStartTime(startTime);
//          taskToUpdate.setStartDate(startDate);
            taskToUpdate.setEndTime(endTime);
//          taskToUpdate.setEndDate(endTime);
            taskToUpdate.setPriority(priority);
            return new CommandResult(String.format(MESSAGE_UPDATE_TASK_SUCCESS, taskToUpdate));
        }
    }
}