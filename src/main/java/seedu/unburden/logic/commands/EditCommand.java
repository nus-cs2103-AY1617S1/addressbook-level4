package seedu.unburden.logic.commands;

import seedu.unburden.commons.core.Messages;
import seedu.unburden.commons.exceptions.*;
import seedu.unburden.commons.core.UnmodifiableObservableList;
import seedu.unburden.model.task.ReadOnlyTask;
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
    
    public final int targetIndex;
    
    public final String args;
    
    
    public EditCommand(int index, String args) {
        this.targetIndex = index;
        this.args = args;
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
            model.editTask(taskToEdit, args);
            return new CommandResult(String.format(MESSAGE_EDIT_TASK_SUCCESS, taskToEdit));
        } catch (TaskNotFoundException | IllegalValueException ee) {
            return new CommandResult(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
        }
        
        
        
    }
}
 