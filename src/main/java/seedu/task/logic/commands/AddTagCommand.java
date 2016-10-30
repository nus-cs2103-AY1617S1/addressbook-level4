//@@author A0147969E
package seedu.task.logic.commands;

import seedu.task.commons.core.Messages;
import seedu.task.commons.core.UnmodifiableObservableList;
import seedu.task.commons.exceptions.IllegalValueException;
import seedu.task.logic.LogicManager;
import seedu.task.model.tag.Tag;
import seedu.task.model.tag.UniqueTagList;
import seedu.task.model.tag.UniqueTagList.DuplicateTagException;
import seedu.task.model.tag.UniqueTagList.NotExistTagException;
import seedu.task.model.task.*;

/**
 * Adds a tag to a task.
 */
public class AddTagCommand extends Command{

    public static final String COMMAND_WORD = "addtag";

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

        UnmodifiableObservableList<ReadOnlyTask> lastShownList = model.getSortedFilteredTaskList();

        if (lastShownList.size() < targetIndex) {
            indicateAttemptToExecuteIncorrectCommand();
            return new CommandResult(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
        }

        ReadOnlyTask taskToUpdate = lastShownList.get(targetIndex - 1);

        UniqueTagList tags =taskToUpdate.getTags();
        try {
			tags.add(tag);
		} catch (DuplicateTagException e1) {
			return new CommandResult(String.format(MESSAGE_DUPLICATE_TAG));
		}

        return executeAddTagCommand(taskToUpdate,tags,lastShownList);
    }

	private CommandResult executeAddTagCommand(ReadOnlyTask taskToUpdate, UniqueTagList tags, UnmodifiableObservableList<ReadOnlyTask> lastShownList) {
		Description description = taskToUpdate.getDescription();
        Time timeStart = taskToUpdate.getTimeStart();
        Time timeEnd = taskToUpdate.getTimeEnd();
        Priority priority = taskToUpdate.getPriority();
        boolean completeStatus = taskToUpdate.getCompleteStatus();

		DeleteCommand delete = new DeleteCommand(targetIndex);
        delete.model = model;
		delete.execute();
		AddCommand add;
		try {
			add = new AddCommand(new Task(description, priority, timeStart, timeEnd, tags, completeStatus), targetIndex-1);
			add.model = model;
			add.insert();
			undo = true;
			LogicManager.tasks.pop();
			try {
				tags.remove(tag);
			} catch (NotExistTagException e1) {
				return new CommandResult("Tag Does Not Exist");
			}
			LogicManager.tasks.push(new Task(description, priority, timeStart, timeEnd, tags, completeStatus));
		} catch (IllegalValueException e){
			 LogicManager.tasks.pop();
			return new CommandResult("re-adding failed");
		};

        return new CommandResult(String.format(MESSAGE_ADD_TAG_SUCCESS, lastShownList.get(targetIndex - 1)));
	}

}
