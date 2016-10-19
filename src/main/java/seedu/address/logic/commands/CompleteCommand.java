package seedu.address.logic.commands;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.UnmodifiableObservableList;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.LogicManager;
import seedu.address.model.tag.UniqueTagList;
import seedu.address.model.tag.UniqueTagList.DuplicateTagException;
import seedu.address.model.task.Description;
import seedu.address.model.task.Priority;
import seedu.address.model.task.ReadOnlyTask;
import seedu.address.model.task.Task;
import seedu.address.model.task.Time;
import seedu.address.model.task.UniqueTaskList.TaskNotFoundException;

/**
 * Deletes a person identified using it's last displayed index from the address book.
 */
public class CompleteCommand extends Command {

    public static final String COMMAND_WORD = "complete";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Complete a task identified by the index number used in the last task listing.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_COMPLETE_TASK_SUCCESS = "Task Done!";

    public final int targetIndex;

    public CompleteCommand(int targetIndex) {
        this.targetIndex = targetIndex;
    }


    @Override
    public CommandResult execute() {

        UnmodifiableObservableList<ReadOnlyTask> lastShownList = model.getFilteredTaskList();

        if (lastShownList.size() < targetIndex) {
            indicateAttemptToExecuteIncorrectCommand();
            return new CommandResult(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
        }

        ReadOnlyTask taskToComplete = lastShownList.get(targetIndex - 1);

        Description description = taskToComplete.getDescription();
        Time timeStart = taskToComplete.getTimeStart();
        Time timeEnd = taskToComplete.getTimeEnd();
        Priority priority = taskToComplete.getPriority();
        UniqueTagList tags =taskToComplete.getTags();
        boolean completeStatus = taskToComplete.getCompleteStatus();

        LogicManager.tasks.push(new Task(description, priority, timeStart, timeEnd, tags, completeStatus));
        LogicManager.indexes.push(targetIndex);

        DeleteCommand delete = new DeleteCommand(targetIndex);
        delete.model = model;
		delete.execute();
		AddCommand add;
		try {
			add = new AddCommand(description.toString(), priority.toString(), timeStart, timeEnd, tags, true,targetIndex-1);
			add.model = model;
			add.insert();
			undo = true;
		} catch (IllegalValueException e) {
			LogicManager.tasks.pop();
	        LogicManager.indexes.pop();
			return new CommandResult("Completion failed.");
		}
		SelectCommand select = new SelectCommand(targetIndex);
		select.model = model;
		select.execute();
        return new CommandResult("Completion done!");
    }

    @Override
	 public CommandResult undo() throws IllegalValueException{
		 Task task = LogicManager.tasks.pop();
		 int index = LogicManager.indexes.pop();

		 task.undoTask();
		 DeleteCommand delete = new DeleteCommand(index);
		 delete.model = model;
		 delete.execute();

		 AddCommand add = new AddCommand(task,index-1);
		 add.model = model;
		 add.insert();

		 LogicManager.tasks.pop();
		 LogicManager.indexes.pop();

		 return new CommandResult("Undo complete!");
	 }

}