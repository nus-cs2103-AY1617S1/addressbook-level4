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

        ReadOnlyTask taskToUpdate = lastShownList.get(targetIndex - 1);

        String description = taskToUpdate.getDescription().toString();;
        String time = taskToUpdate.getTime().toString();
        String date = taskToUpdate.getDate().toString();
        String priority = taskToUpdate.getPriority().toString();
        UniqueTagList tags =taskToUpdate.getTags();

        try {
			tags.add(tag);
		} catch (DuplicateTagException e1) {
			return new CommandResult(String.format(MESSAGE_DUPLICATE_TAG));
		}
		DeleteCommand delete = new DeleteCommand(targetIndex);
        delete.model = model;
		delete.execute();
		AddCommand add;
		try {
			add = new AddCommand(description, priority, time, date, tags);
			add.model = model;
			add.execute();
		} catch (IllegalValueException e){
			return new CommandResult("re-adding failed");
		};

        return new CommandResult(String.format(MESSAGE_ADD_TAG_SUCCESS, taskToUpdate));
    }

}
