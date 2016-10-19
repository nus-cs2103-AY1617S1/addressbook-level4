package seedu.address.logic.commands;

import java.time.LocalDateTime;
import java.util.Optional;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.UnmodifiableObservableList;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.task.Name;
import seedu.address.model.task.ReadOnlyTask;
import seedu.address.model.task.Task;
import seedu.address.model.task.UniqueTaskList.TaskNotFoundException;

/**
 * Edits a task identified using it's last displayed index from the task manager.
 */
//@@author A0139339W
public class EditCommand extends Command {

    public static final String COMMAND_WORD = "edit";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Edits the task identified by the index number used in the last task listing.\n"
            + "Parameters: INDEX (positive integer) ['NEW_NAME'] [from hh::mm to hh:mm | by hh: mm] [dd-mm-yy] [done| not-done] \n"
            + "Example: " + COMMAND_WORD + " 1 'chill for the day'";
 
    public static final String MESSAGE_EDIT_TASK_SUCCESS = "Edited Task: %1$s";
    public static final String MESSAGE_DUPLICATE_TASK = "Edit will result in duplicate tasks in task manager";


    public final int targetIndex;
    private final Optional<Name> newName;
    private final Optional<LocalDateTime> newStartDateTime;
    private final Optional<LocalDateTime> newEndDateTime;

    /**
     * For editing name of task
     * @throws IllegalValueException 
     */
    public EditCommand(int targetIndex, String name, LocalDateTime startDateTime, LocalDateTime endDateTime)
    		throws IllegalValueException {
        this.targetIndex = targetIndex;
        if(name.equals("")) {
        	this.newName = Optional.empty();
        } else {
        	this.newName = Optional.ofNullable(new Name(name));
        }
        this.newStartDateTime = Optional.ofNullable(startDateTime);
        this.newEndDateTime = Optional.ofNullable(endDateTime);
    }


    @Override
    public CommandResult execute() {

        model.saveState();
        
        UnmodifiableObservableList<ReadOnlyTask> lastShownList = model.getFilteredTaskList();

        if (lastShownList.size() < targetIndex) {
            indicateAttemptToExecuteIncorrectCommand();
            return new CommandResult(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
        }

        ReadOnlyTask taskToEdit = lastShownList.get(targetIndex - 1);

        try {
        	Task postEdit = new Task(taskToEdit);
        	
        	if(newName.isPresent()) {
        		postEdit.setName(newName.get());
        	}
        	
        	if(newStartDateTime.isPresent()) {
        		postEdit.setStartDate(newStartDateTime.get());
        	}
        	
        	if(newEndDateTime.isPresent()) {
        		postEdit.setEndDate(newEndDateTime.get());
        	}
        	
        	if(lastShownList.contains(postEdit)) {
        		return new CommandResult(MESSAGE_DUPLICATE_TASK);
        	}
        	
            model.editTask(targetIndex, postEdit);
            
        } catch (TaskNotFoundException tnfe) {
            assert false : "The target task cannot be missing";
        }
        
        return new CommandResult(String.format(MESSAGE_EDIT_TASK_SUCCESS, taskToEdit));
    }

}