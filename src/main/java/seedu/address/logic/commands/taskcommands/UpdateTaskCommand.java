package seedu.address.logic.commands.taskcommands;

import java.util.Date;

import javafx.collections.ObservableList;
import seedu.address.commons.collections.UniqueItemCollection.DuplicateItemException;
import seedu.address.commons.collections.UniqueItemCollection.ItemNotFoundException;
import seedu.address.commons.core.Messages;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.CommandResult;
import seedu.address.model.task.DeadlineTask;
import seedu.address.model.task.Description;
import seedu.address.model.task.EventTask;
import seedu.address.model.task.FloatingTask;
import seedu.address.model.task.Task;

/**
 * Updates a task identified using it's last displayed index from TaskManager.
 */
public class UpdateTaskCommand extends TaskCommand {

	public static final String COMMAND_WORD = "update";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Updates the task identified by the index number used in the last task listing.\n"
            + "Depending on whether 'task', 'description' or 'date' is stated, the task will be updated accordingly.\n"
            + "1) Parameters: INDEX (must be a positive integer) task UPDATED_VALUE\n"
            + "Example: " + COMMAND_WORD + " 1 task Meeting from Oct 31 to Nov 1\n"
            + "2) Parameters: INDEX (must be a positive integer) task UPDATED_VALUE\n"
            + "Example: " + COMMAND_WORD + " 1 description Meeting in town\n"
            + "3) Parameters: INDEX (must be a positive integer) task UPDATED_VALUE\n"
            + "Example: " + COMMAND_WORD + " 1 date Oct 31 to Nov 1";

    public static final String MESSAGE_UPDATE_TASK_SUCCESS = "Updated task: %1$s";
    public static final String MESSAGE_INVALID_UPDATE = "Invalid update to the task";

    public final int targetIndex;
    private Task updatedTask;
    
    // Values that are to be updated. If it is not supposed to be updated, it will be null
    private Task newTask;
    private Description newDescription;
    private Date newDeadline;
    private Date newStartDate;
    private Date newEndDate;

    /**
     * This constructor is called by the user enters a command to replace the entire task.
     * 
     * Example: update 1 task Homework by 31 Oct 2016
     * (Replaces whatever task at index 1 to be a DeadlineTask with description as "Homework" and deadline by 31 Oct 2016)
     */
    public UpdateTaskCommand(int targetIndex, Task newTask) {
        this.targetIndex = targetIndex;
        this.newTask = newTask;
    }
    
    /**
     * This constructor is called by the user enters a command to update the description of a task.
     * 
     * Example: update 1 description Meeting
     * (Changes the description of the task at index 1 to be "Meeting")
     */
    public UpdateTaskCommand(int targetIndex, Description newDescription) {
        this.targetIndex = targetIndex;
        this.newDescription = newDescription;
    }
    
    /**
     * This constructor is called by the user enters a command to update the deadline of a task.
     * Pre-Cond: Targeted task to update has to be a DeadlineTask
     * 
     * Example: update 1 date 31 Oct
     * (Changes the task (deadline task) at index 1 to have a deadline of 31 Oct 2016)
     */
    public UpdateTaskCommand(int targetIndex, Date newDeadline) {
        this.targetIndex = targetIndex;
        this.newDeadline = newDeadline;
    }
    
    /**
     * This constructor is called by the user enters a command to update the start date and end date of a task.
     * Pre-Cond: Targeted task to update has to be an EventTask
     * 
     * Example: update 1 date 31 Oct to 1 Nov
     * (Changes the task (event task) at index 1 to have a start date of 31 Oct and end date of 1 Nov)
     */
    public UpdateTaskCommand(int targetIndex, Date newStartDate, Date newEndDate) {
        this.targetIndex = targetIndex;
        this.newStartDate = newStartDate;
        this.newEndDate = newEndDate;
    }
    
    /**
     * Given the task that is to be updated, retrieve the new values that have been assigned by constructors
     */
    public void prepareUpdatedTask(Task taskToUpdate) throws IllegalValueException {
    	if (newTask != null) {
    		// User wants to change the entire task
    		updatedTask = newTask;
    		
    	} else if (newDescription != null) {
    		// User wants to change just the description
    		updatedTask = prepareUpdatedDescriptionForTask(taskToUpdate);
    		
    	} else if (newDeadline != null) {
    		// User wants to change the deadline of a DeadlineTask (to check)
    		updatedTask = prepareUpdatedDeadlineForTask(taskToUpdate);

    	} else if ((newStartDate != null && newEndDate != null)) {
    		// User wants to change the start date and end date of an EventTask (to check)
    		updatedTask = prepareUpdatedStartEndDateForTask(taskToUpdate);
    		
    	} else {
    		assert false : "At least task, description or date should have new values";
    	}
    }
    
    public Task prepareUpdatedDescriptionForTask(Task taskToUpdate) throws IllegalValueException {
    	// Return a new Task based on the type of the task to be updated
		if (taskToUpdate instanceof FloatingTask) {
			return new FloatingTask(newDescription.getContent());
			
		} else if (taskToUpdate instanceof DeadlineTask) {
			DeadlineTask task = (DeadlineTask) taskToUpdate;
			return new DeadlineTask(newDescription.getContent(), task.getDeadline());
			
		} else if (taskToUpdate instanceof EventTask) {
			EventTask task = (EventTask) taskToUpdate;
			return new EventTask(newDescription.getContent(), task.getStartDate(), task.getEndDate());	
		
		} else {
			throw new IllegalValueException("Task's description cannot be updated");
		}
    }
    
    public Task prepareUpdatedDeadlineForTask(Task taskToUpdate) throws IllegalValueException {
    	// Check if it is indeed a DeadlineTask
		if (taskToUpdate instanceof DeadlineTask) {
			DeadlineTask task = (DeadlineTask) taskToUpdate;
			return new DeadlineTask(task.getDescription().getContent(), task.getDeadline());
			
		} else {
			throw new IllegalValueException("Deadline task required for update of deadline");
		}
    }
    
    public Task prepareUpdatedStartEndDateForTask(Task taskToUpdate) throws IllegalValueException {
    	// Check if it is indeed a EventTask
		if (taskToUpdate instanceof EventTask) {
			EventTask task = (EventTask) taskToUpdate;
			return new EventTask(task.getDescription().getContent(), newStartDate, newEndDate);
			
		} else {
			throw new IllegalValueException("Event task required for update of start date and end date");
		}
    }
    
    /**
     * Retrieve the details of the values to be updated for testing purposes
     */
    public String getTaskDetails() {
    	if (newTask != null) {
    		return String.format("[Update Task][Task: %s]", newTask);
    	} else if (newDescription != null) {
    		return String.format("[Update Task][Description: %s]", newDescription);
    	} else if (newDeadline != null) {
    		return String.format("[Update Task][Deadline: %s]", newDeadline);
    	} else if (newStartDate != null && newEndDate != null) {
    		return String.format("[Update Task][Start date: %s][End date: %s]", newStartDate, newEndDate);
    	} else {
    		return "Error";
    	}
    }


    @Override
    public CommandResult execute() {

	    ObservableList<Task> lastShownList = model.getCurrentFilteredTasks();

        if (lastShownList.size() < targetIndex || targetIndex <= 0) {
            indicateAttemptToExecuteIncorrectCommand();
            return new CommandResult(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
        }

        Task taskToUpdate = lastShownList.get(targetIndex - 1);

        try {
        	prepareUpdatedTask(taskToUpdate);
            model.updateTask(taskToUpdate, updatedTask);
        } catch (DuplicateItemException die) {
        	assert false : "Deletion of the original task (before addition of an updated task) has failed";
        } catch (IllegalValueException ive) {
        	return new CommandResult(MESSAGE_INVALID_UPDATE);
        } catch (ItemNotFoundException tnfe) {
            assert false : "The target item cannot be missing";
        } 

        return new CommandResult(String.format(MESSAGE_UPDATE_TASK_SUCCESS, taskToUpdate));
    }

}
