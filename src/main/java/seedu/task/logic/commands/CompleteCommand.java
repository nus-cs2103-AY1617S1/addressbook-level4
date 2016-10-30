//@@author A0147969E
package seedu.task.logic.commands;

import seedu.task.commons.core.Messages;
import seedu.task.commons.core.UnmodifiableObservableList;
import seedu.task.commons.exceptions.IllegalValueException;
import seedu.task.logic.LogicManager;
import seedu.task.model.tag.UniqueTagList;
import seedu.task.model.task.Description;
import seedu.task.model.task.Priority;
import seedu.task.model.task.ReadOnlyTask;
import seedu.task.model.task.Task;
import seedu.task.model.task.Time;

/**
 * Deletes a task identified using it's last displayed index from the task manager.
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

        UnmodifiableObservableList<ReadOnlyTask> lastShownList = model.getSortedFilteredTaskList();

        if (lastShownList.size() < targetIndex) {
            indicateAttemptToExecuteIncorrectCommand();
            return new CommandResult(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
        }

        ReadOnlyTask taskToComplete = lastShownList.get(targetIndex - 1);

        prepareForUndo(taskToComplete);
        return(executeComplete(taskToComplete));
    }

	private CommandResult executeComplete(ReadOnlyTask taskToComplete){
		DeleteCommand delete = new DeleteCommand(targetIndex);
        delete.model = model;
		delete.execute();
		AddCommand add;
		try {
			add = new AddCommand(taskToComplete.getDescription().toString(),
							     taskToComplete.getPriority().toString(),
							     taskToComplete.getTimeStart(),
							     taskToComplete.getTimeEnd(),
							     taskToComplete.getTags(),
							     true,
							     targetIndex-1);
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


	private void prepareForUndo(ReadOnlyTask taskToComplete) {
		Description description = taskToComplete.getDescription();
        Time timeStart = taskToComplete.getTimeStart();
        Time timeEnd = taskToComplete.getTimeEnd();
        Priority priority = taskToComplete.getPriority();
        UniqueTagList tags =taskToComplete.getTags();
        boolean completeStatus = taskToComplete.getCompleteStatus();

        LogicManager.tasks.push(new Task(description, priority, timeStart, timeEnd, tags, completeStatus));
        LogicManager.indexes.push(targetIndex);
	}

}