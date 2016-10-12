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
 * Adds a person to the address book.
 */
public class AddTagCommand extends Command{

    public static final String COMMAND_WORD = "addTag";

    public static final String MESSAGE_USAGE = COMMAND_WORD
    		+ ": add a tag to the task identified by the index number used in the last task listing.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1" + " Tired";

    public static final String MESSAGE_ADD_TAG_SUCCESS = "New tag added: %1$s";
    public static final String MESSAGE_DUPLICATE_TAG = "This Tag already exists";

    public final int targetIndex;
    public final Tag tag;

    public AddTagCommand(int targetIndex, String t) throws IllegalValueException {
        this.targetIndex = targetIndex;;
        this.tag = new Tag(t);
    }

	@Override
    public CommandResult execute() {

        UnmodifiableObservableList<ReadOnlyTask> lastShownList = model.getFilteredTaskList();

        if (lastShownList.size() < targetIndex) {
            indicateAttemptToExecuteIncorrectCommand();
            return new CommandResult(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        ReadOnlyTask taskToAddTag = lastShownList.get(targetIndex - 1);

        try {
			taskToAddTag.getTags().add(tag);
		} catch (DuplicateTagException e) {
			return new CommandResult(MESSAGE_DUPLICATE_TAG);
		}

        return new CommandResult(String.format(MESSAGE_ADD_TAG_SUCCESS, taskToAddTag));
    }

}
