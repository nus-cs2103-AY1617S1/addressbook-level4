package seedu.address.logic.commands;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.UnmodifiableObservableList;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.task.*;
import seedu.address.model.task.UniqueTaskList.TaskNotFoundException;
import seedu.address.model.tag.Tag;
import seedu.address.model.tag.UniqueTagList;
import seedu.address.model.tag.UniqueTagList.DuplicateTagException;

import java.util.HashSet;
import java.util.Set;

/**
 * update the details of a task.
 */
public class UpdateCommand extends Command{

    public static final String COMMAND_WORD = "update";

    public static final String MESSAGE_USAGE = COMMAND_WORD
    		+ ": update the details of an existing task.\n"
            + "Parameters: INDEX (must be a positive integer) PROPERTY INFOMATION\n"
            + "Example: " + COMMAND_WORD + " 1" + " priority"+" high";

    public static final String MESSAGE_EDIT_SUCCESS = "Edit successfully";
    public static final String MESSAGE_EDIT_FAIL = "Editing failed";

    public final int targetIndex;
    public final String property;
    public final String info;

    public UpdateCommand(int targetIndex, String property, String info) throws IllegalValueException {
        this.targetIndex = targetIndex;;
        this.property = property;
        this.info = info;
    }

	@Override
    public CommandResult execute() {

        UnmodifiableObservableList<ReadOnlyTask> lastShownList = model.getFilteredTaskList();

        if (lastShownList.size() < targetIndex) {
            indicateAttemptToExecuteIncorrectCommand();
            return new CommandResult(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        ReadOnlyTask taskToUpdate = lastShownList.get(targetIndex - 1);

        try {
			switch(property){
				case "description":
					taskToUpdate.setDescription(new Description(info));
					break;
				case "time":
					taskToUpdate.setTime(new Time(info));
					break;
				case "priority":
					taskToUpdate.setPriority(new Priority(info));
					break;
				case "date":
					taskToUpdate.setDate(new Date(info));
					break;
				default: return new CommandResult(MESSAGE_EDIT_FAIL);
			}
        }catch (IllegalValueException e) {
        	return new CommandResult(MESSAGE_EDIT_FAIL);
		}

        return new CommandResult(String.format(MESSAGE_EDIT_SUCCESS, taskToUpdate));
    }

}
