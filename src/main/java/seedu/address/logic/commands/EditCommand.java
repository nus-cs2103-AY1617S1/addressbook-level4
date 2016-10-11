package seedu.address.logic.commands;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.UnmodifiableObservableList;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.tag.Tag;
import seedu.address.model.tag.UniqueTagList;
import seedu.address.model.task.*;
import seedu.address.model.task.UniquePersonList.PersonNotFoundException;

import java.text.ParseException;
import java.util.HashSet;
import java.util.Set;

public class EditCommand extends Command {
	
	public static final String COMMAND_WORD = "edit";
	
	public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits a task in the task manager. "
            + "Parameters: NAME d/DEADLINE p/PRIORITY" 
            + " Example: " + COMMAND_WORD
            + " CS2103T Software Engineeringv0.1 d/131016 p/1";
	
	public static final String MESSAGE_EDIT_TASK_SUCCESS = "Task edited: %1$s";
    public static final String MESSAGE_DUPLICATE_TASK = "This task already exists in the task manager.";

    public final int targetIndex;
    
    public EditCommand(int targetIndex) 
    		throws IllegalValueException{
    	this.targetIndex = targetIndex;
    }

	@Override
	public CommandResult execute() {
		UnmodifiableObservableList<ReadOnlyTask> lastShownList = model.getFilteredPersonList();

        if (lastShownList.size() < targetIndex) {
            indicateAttemptToExecuteIncorrectCommand();
            return new CommandResult(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
        }
        ReadOnlyTask taskToEdit = lastShownList.get(targetIndex - 1);

        try {
            model.editTask(taskToEdit);
        } catch (PersonNotFoundException pnfe) {
            assert false : "The target task cannot be missing";
        }

        return new CommandResult(String.format(MESSAGE_EDIT_TASK_SUCCESS, taskToEdit));
	}
    
    
}
