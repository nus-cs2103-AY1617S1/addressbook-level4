package seedu.task.logic.commands;

import seedu.task.commons.core.Messages;
import seedu.task.commons.core.UnmodifiableObservableList;
import seedu.task.commons.exceptions.IllegalValueException;
import seedu.task.model.tag.Tag;
import seedu.task.model.tag.UniqueTagList;
import seedu.task.model.tag.UniqueTagList.NotExistTagException;
import seedu.task.model.task.*;

/**
 * Deletes a tag from a task.
 */
public class DeleteTagCommand extends Command{

    public static final String COMMAND_WORD = "deletetag";

    public static final String MESSAGE_USAGE = COMMAND_WORD
    		+ ": delete a tag of the task identified by the index number used in the last task listing.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1" + " Tired(an existing tag)";

    public static final String MESSAGE_DELETE_TAG_SUCCESS = "Tag deleted: %1$s";
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
            return new CommandResult(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
        }

        ReadOnlyTask taskToUpdate = lastShownList.get(targetIndex - 1);

        String description = taskToUpdate.getDescription().toString();;
        Time timeStart = taskToUpdate.getTimeStart();
        Time timeEnd = taskToUpdate.getTimeEnd();
        String priority = taskToUpdate.getPriority().toString();
        UniqueTagList tags =taskToUpdate.getTags();
        boolean completeStatus = taskToUpdate.getCompleteStatus();

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
			add = new AddCommand(description, priority, timeStart, timeEnd, tags, completeStatus,targetIndex-1);
			add.model = model;
			add.insert();
			undo = true;
		} catch (IllegalValueException e){
			return new CommandResult("re-adding failed");
		};

        return new CommandResult(String.format(MESSAGE_DELETE_TAG_SUCCESS, lastShownList.get(targetIndex - 1)));
    }

	@Override
	 public CommandResult undo() throws IllegalValueException{
			AddTagCommand addTag = new AddTagCommand(targetIndex,tag.tagName);
			addTag.model = model;
			addTag.execute();
			return new CommandResult("Undo complete!");
	 }

}
