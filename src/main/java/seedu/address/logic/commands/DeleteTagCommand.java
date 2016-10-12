package seedu.address.logic.commands;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.UnmodifiableObservableList;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.task.*;
import seedu.address.model.task.UniqueTaskList.TaskNotFoundException;
import seedu.address.model.tag.Tag;
import seedu.address.model.tag.UniqueTagList;
import seedu.address.model.tag.UniqueTagList.DuplicateTagException;
import seedu.address.model.tag.UniqueTagList.NotExistTagException;

import java.util.HashSet;
import java.util.Set;

/**
 * Adds a person to the address book.
 */
public class DeleteTagCommand extends Command{

    public static final String COMMAND_WORD = "deleteTag";

    public static final String MESSAGE_USAGE = COMMAND_WORD
    		+ ": delete a tag of the task identified by the index number used in the last task listing.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1" + " Tired(an existing one)";

    public static final String MESSAGE_ADD_TAG_SUCCESS = "Tag deleted: %1$s";
    public static final String MESSAGE_TAG_NOT_EXIST = "This Tag does not exist";

    public final int targetIndex;
    public final Tag tag;

    public DeleteTagCommand(int targetIndex, String t) throws IllegalValueException {
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
			tags.remove(tag);
		} catch (NotExistTagException e1) {
			return new CommandResult(String.format(MESSAGE_TAG_NOT_EXIST));
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
