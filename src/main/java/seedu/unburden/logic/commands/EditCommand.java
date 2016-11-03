package seedu.unburden.logic.commands;

import seedu.unburden.commons.core.Messages;
import seedu.unburden.commons.exceptions.*;
import seedu.unburden.commons.core.UnmodifiableObservableList;
import seedu.unburden.model.tag.UniqueTagList;
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
        else {
        	this.newTaskDescription = newTaskDescription;
        }
        
        if (newDate == null) {
        	this.newDate = ""; //dummy value
        }
        else {
        	this.newDate = newDate;
        }
        
        if (newStartTime == null) {
        	this.newStartTime = ""; //dummy value
        }
        else {
        	this.newStartTime = newStartTime;
        }
        
        if (newEndTime == null) {
        	this.newEndTime = ""; //dummy value
        }
        else {
        	this.newEndTime = newEndTime;
        }
        
        this.toEdit = new Task(new Name(this.newName), new TaskDescription(this.newTaskDescription), new Date(this.newDate),
				new Time(this.newStartTime), new Time(this.newEndTime), new UniqueTagList());
        
    }
    
    @Override
    public CommandResult execute() {
        UnmodifiableObservableList<ReadOnlyTask> lastShownList = model.getFilteredTaskList();

        if (lastShownList.size() < targetIndex) {
            indicateAttemptToExecuteIncorrectCommand();
            return new CommandResult(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
        }

        ReadOnlyTask taskToEdit = lastShownList.get(targetIndex - 1);
        
        try {

        	model.saveToPrevLists();
            model.editTask(taskToEdit, toEdit);
            
            return new CommandResult(String.format(MESSAGE_EDIT_TASK_SUCCESS, toEdit));
        } catch (TaskNotFoundException ee) {
            return new CommandResult(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
        } catch (IllegalValueException e) {
        	return new CommandResult(MESSAGE_EDIT_FAIL);
        }
        
        
    }
}
 