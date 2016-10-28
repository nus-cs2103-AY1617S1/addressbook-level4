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
            + "Parameters: INDEX (positive integer) ['NEW_NAME'] [from TIME DATE] [by TIME DATE]\n"
            + "Example: " + COMMAND_WORD + " 1 'chill for the day' from 12am today by 11pm today";
 
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
    public EditCommand(int targetIndex, Optional<Name> name, Optional<LocalDateTime> newStartDate, Optional<LocalDateTime> newEndDate)
    		throws IllegalValueException {
        this.targetIndex = targetIndex;
        this.newName = name;
        this.newStartDateTime = newStartDate;
        this.newEndDateTime = newEndDate;
    }


    @Override
    public CommandResult execute() {

        model.saveState();
        
        UnmodifiableObservableList<ReadOnlyTask> lastShownList = model.getFilteredTaskList();
        UnmodifiableObservableList<ReadOnlyTask> fullList = model.getUnfilteredTaskList();

        if (lastShownList.size() < targetIndex) {
            model.loadPreviousState();
            indicateAttemptToExecuteIncorrectCommand();
            return new CommandResult(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
        }

        ReadOnlyTask taskToEdit = lastShownList.get(targetIndex - 1);

        try {
            Task postEdit = new Task(taskToEdit);
            int index = fullList.indexOf(postEdit);
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
                model.loadPreviousState();
                return new CommandResult(MESSAGE_DUPLICATE_TASK);
            }
        	
            model.editTask(index, postEdit);
            
        } catch (UnsupportedOperationException uoe) {
            model.loadPreviousState();
            return new CommandResult(uoe.getMessage());
        } catch (TaskNotFoundException tnfe) {
            model.loadPreviousState();
            assert false : "The target task cannot be missing";
        }
        
        return new CommandResult(String.format(MESSAGE_EDIT_TASK_SUCCESS, taskToEdit));
    }

}