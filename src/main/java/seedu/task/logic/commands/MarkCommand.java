package seedu.task.logic.commands;

import seedu.task.commons.core.EventsCenter;
import seedu.task.commons.core.Messages;
import seedu.task.commons.core.UnmodifiableObservableList;
import seedu.task.commons.events.ui.JumpToTaskListRequestEvent;
import seedu.task.model.item.ReadOnlyTask;

/**
 * Marks a task as completed using it's last displayed index from the task book.
 * @@author A0121608N
 */
public class MarkCommand extends UndoableCommand {

    private static final boolean INCOMPLETE_STATUS = false;
    public static final String COMMAND_WORD = "mark";
    public static final String MESSAGE_USAGE = COMMAND_WORD + "\n"
            + "Marks the task identified by the index number used in the last task listing.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_MARK_TASK_SUCCESS = "Marked Task: %1$s";
    
    public  Integer targetIndex;
    private ReadOnlyTask taskToMark;
    
    public MarkCommand(int targetIndex) {
        this.targetIndex = targetIndex;
    }

	@Override
    public CommandResult execute() {

        UnmodifiableObservableList<ReadOnlyTask> lastShownList = model.getFilteredTaskList();

        if (lastShownList.size() < targetIndex || targetIndex == 0) {
            indicateAttemptToExecuteIncorrectCommand();
            return new CommandResult(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
        }
        
        taskToMark = lastShownList.get(targetIndex - 1);
        model.markTask(taskToMark); // list starts at zero
        if (taskToMark.getTaskStatus() == INCOMPLETE_STATUS) {   //Task will be selected if being marked from completed to uncompleted
            EventsCenter.getInstance().post(new JumpToTaskListRequestEvent(taskToMark, targetIndex - 1));
        }

        return new CommandResult(String.format(MESSAGE_MARK_TASK_SUCCESS, targetIndex));

    }

	@Override
	public CommandResult undo() {
		model.markTask(taskToMark);
		targetIndex = model.getFilteredTaskList().indexOf(taskToMark);
		return new CommandResult(String.format(MESSAGE_MARK_TASK_SUCCESS, targetIndex+1));
	}


	@Override
	public String toString() {
		return COMMAND_WORD +" "+ this.targetIndex;
	}

}
