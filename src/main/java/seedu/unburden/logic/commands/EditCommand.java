package seedu.unburden.logic.commands;

import java.util.List;

import seedu.unburden.commons.core.Messages;
import seedu.unburden.commons.exceptions.*;
import seedu.unburden.commons.core.UnmodifiableObservableList;
import seedu.unburden.model.tag.UniqueTagList;
import seedu.unburden.model.tag.UniqueTagList.DuplicateTagException;
import seedu.unburden.model.task.Date;
import seedu.unburden.model.task.Name;
import seedu.unburden.model.task.ReadOnlyTask;
import seedu.unburden.model.task.Task;
import seedu.unburden.model.task.TaskDescription;
import seedu.unburden.model.task.Time;
import seedu.unburden.model.task.UniqueTaskList.*;

//@@author A0139714B=======

/*
 * edit any field of the task\
 * @@author A0139714B
 */

public class EditCommand extends Command {
    
    public static final String COMMAND_WORD = "edit";
    
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Edits the task identified by the index number used in the last task listing.\n"
            + "Parameters: INDEX (must be a positive integer) TASKNAME i/TASKDESCRIPTION d/DATE s/STARTTIME e/ENDTIME"
            + "Example: " + COMMAND_WORD 
            + " 1 meeting with boss i/project presentation d/23-12-2016 s/1200 e/1300" ;
    
    public static final String MESSAGE_EDIT_TASK_SUCCESS = "Updated Task: %1$s\n";
    
    public static final String MESSAGE_EDIT_FAIL = "Editing has failed. Please check the details and try again";
    
    private final int targetIndex;
    
    private final Task toEdit;
    
    public static boolean removeTaskDescription = false;
    public static boolean removeDate = false;
    public static boolean removeStartTime = false;
    public static boolean removeEndTime = false;
    
    private final String newName, newTaskDescription, newDate, newStartTime, newEndTime;
    
    public EditCommand(int index, String newName, String newTaskDescription, String newDate, String newStartTime, String newEndTime) 
    		throws IllegalValueException {
        this.targetIndex = index;
        
        if (newName == null) {
        	this.newName = ""; //dummy value
        }
        else {
        	this.newName = newName;
        }
        
        if (newTaskDescription == null) {
        	this.newTaskDescription = ""; //dummy value
        }
        else if (newTaskDescription.compareTo("rm") == 0) {
        	this.removeTaskDescription = true;
        	this.newTaskDescription = "";
        }
        else {
        	this.newTaskDescription = newTaskDescription;
        }
        
        if (newDate == null) {
        	this.newDate = ""; //dummy value
        }
        else if (newDate.compareTo("rm") == 0) {
        	this.removeDate = true;
        	this.newDate = "";
        }
        else {
        	this.newDate = newDate;
        }
        
        if (newStartTime == null) {
        	this.newStartTime = ""; //dummy value
        }
        else if (newStartTime.compareTo("rm") == 0) {
        	this.removeStartTime = true;
        	this.newStartTime = "";
        }
        else {
        	this.newStartTime = newStartTime;
        }
        
        if (newEndTime == null) {
        	this.newEndTime = ""; //dummy value
        }
        else if (newEndTime.compareTo("rm") == 0) {
        	this.removeEndTime = true;
        	this.newEndTime = "";
        }
        else {
        	this.newEndTime = newEndTime;
        }
        
        this.toEdit = new Task(new Name(this.newName), new TaskDescription(this.newTaskDescription), new Date(this.newDate),
				new Time(this.newStartTime), new Time(this.newEndTime), new UniqueTagList());
        
    }
    
    public static void reset() {
    	removeTaskDescription = false;
    	removeDate = false;
    	removeEndTime = false;
    	removeStartTime = false;
    	
    }
    
    @Override
    public CommandResult execute() throws IllegalValueException{
        UnmodifiableObservableList<ReadOnlyTask> lastShownList = model.getFilteredTaskList();
        
        if (lastShownList.size() < targetIndex) {
            indicateAttemptToExecuteIncorrectCommand();
            return new CommandResult(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
        }

        ReadOnlyTask taskToEdit = lastShownList.get(targetIndex - 1);
        
        Task targetTask = (Task) taskToEdit;
        
        
        try {

        	model.saveToPrevLists();
        	EditValidation.checkIfCanEditEndTime(targetTask, toEdit);
        	EditValidation.checkIfCanEditStartTime(targetTask, toEdit);
        	EditValidation.checkIfCanRemoveEndTime(targetTask, toEdit);
        	EditValidation.checkIfCanRemoveDate(targetTask, toEdit);
        	EditValidation.checkIfStartTimeLaterThanEndTime(targetTask, toEdit);
        	
            model.editTask(taskToEdit, toEdit);
            
            return new CommandResult(String.format(MESSAGE_EDIT_TASK_SUCCESS, toEdit));
        } catch (TaskNotFoundException ee) {
            return new CommandResult(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
        } catch (IllegalValueException e) {
        	return new CommandResult(MESSAGE_EDIT_FAIL);
        } catch (CannotAddEndTimeWithoutDateException eee) {
        	return new CommandResult(Messages.MESSAGE_CANNOT_ADD_ENDTIME_WITH_NO_DATE);
        } catch (CannotAddStartTimeWithoutEndTimeException eeee) {
        	return new CommandResult(Messages.MESSAGE_CANNOT_ADD_STARTTIME_WITH_NO_ENDTIME);
        } catch (CannotRemoveEndTimeWhenThereIsStartTimeException abc) {
        	return new CommandResult(Messages.MESSAGE_CANNOT_REMOVE_ENDTIME_WHEN_THERE_IS_STARTTIME);
        } catch (CannotRemoveDateWhenThereIsStartTimeAndEndTimeException def) {
        	return new CommandResult(Messages.MESSAGE_CANNOT_REMOVE_DATE_WHEN_THERE_IS_STARTTIME_AND_ENDTIME);
        } catch (CannotHaveStartTimeLaterThanEndTimeException ghi) {
        	return new CommandResult(Messages.MESSAGE_STARTTIME_AFTER_ENDTIME);
        }
        
    }

    /*
     * helper class to check
     */
    private static class EditValidation {
    
    	static void checkIfCanEditEndTime(Task targetTask, Task toEdit) throws CannotAddEndTimeWithoutDateException {
    		if (targetTask.getDate().getFullDate() == "" && toEdit.getEndTime().getFullTime() != "") {
    			throw new CannotAddEndTimeWithoutDateException();
    		}	
    	 }
    	 
    	 static void checkIfCanEditStartTime(Task targetTask, Task toEdit) throws CannotAddStartTimeWithoutEndTimeException {
    		 if (targetTask.getEndTime().getFullTime() == "" && toEdit.getStartTime().getFullTime() != "") {
    			 throw new CannotAddStartTimeWithoutEndTimeException();
    		 }
    	 }
    	 
    	 static void checkIfCanRemoveEndTime(Task targetTask, Task toEdit) throws CannotRemoveEndTimeWhenThereIsStartTimeException {
    		 if (targetTask.getStartTime().getFullTime() != "" && removeEndTime == true) {
    			 throw new CannotRemoveEndTimeWhenThereIsStartTimeException();
    		 }
    	 }
    	 
    	 static void checkIfCanRemoveDate(Task targetTask, Task toEdit) throws CannotRemoveDateWhenThereIsStartTimeAndEndTimeException {
    		 if (targetTask.getStartTime().getFullTime() != "" && targetTask.getEndTime().getFullTime() != "" && removeDate == true) {
    			 throw new CannotRemoveDateWhenThereIsStartTimeAndEndTimeException();
    		 }
    	 }
    	 
    	 static void checkIfStartTimeLaterThanEndTime(Task targetTask, Task toEdit) throws CannotHaveStartTimeLaterThanEndTimeException {
    		 if (targetTask.getEndTime().getFullTime() != "" && toEdit.getStartTime().getFullTime() != "" && 
    				 toEdit.getStartTime().getFullTime().compareTo(targetTask.getEndTime().getFullTime()) > 0) {
    			 throw new CannotHaveStartTimeLaterThanEndTimeException();
    		 }
    	 }
    	 
    }
    
}
 