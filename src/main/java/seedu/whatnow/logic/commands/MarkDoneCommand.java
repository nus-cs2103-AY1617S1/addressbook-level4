package seedu.whatnow.logic.commands;

import seedu.whatnow.commons.core.Messages;
import seedu.whatnow.commons.core.UnmodifiableObservableList;
import seedu.whatnow.model.task.ReadOnlyTask;
import seedu.whatnow.model.task.UniqueTaskList.TaskNotFoundException;

/**
 * Marks a task identified using it's last displayed index from WhatNow as completed.
 */
public class MarkDoneCommand extends UndoAndRedo {
    
    public static final String COMMAND_WORD = "done";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Mark the task identified by the index number used in the last task listing as completed.\n"
            + "Parameters: TODO/SCHEDULE INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " todo 1\n"
            + "Example: " + COMMAND_WORD + " schedule 1";

    public static final String MESSAGE_MARK_TASK_SUCCESS = "Task marked as completed: %1$s";
    
    public static final String MESSAGE_MARK_TASK_FAIL = "Unable to mark task at listed index";
    private static final String TASK_TYPE_FLOATING = "floating";

    public final String taskType;
    public final int targetIndex;
    
   // public ReadOnlyTask taskToMark;
    public MarkDoneCommand(String taskType, int targetIndex) {
        this.taskType = taskType;
        this.targetIndex = targetIndex;
    }


    @Override
    public CommandResult execute() {
        UnmodifiableObservableList<ReadOnlyTask> lastShownList;
        if (taskType.equals(TASK_TYPE_FLOATING)) {
            lastShownList = model.getCurrentFilteredTaskList();
        } else {
            lastShownList = model.getCurrentFilteredScheduleList();
        }

        if (lastShownList.size() < targetIndex) {
            indicateAttemptToExecuteIncorrectCommand();
            return new CommandResult(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
        }

        ReadOnlyTask taskToMark = lastShownList.get(targetIndex - 1);

        try {
            model.markTask(taskToMark);
            model.getUndoStack().push(this);
        } catch (TaskNotFoundException pnfe) {
            return new CommandResult(MESSAGE_MARK_TASK_FAIL);
        }

        return new CommandResult(MESSAGE_MARK_TASK_SUCCESS);
    }


	@Override
	public CommandResult undo() {
		UnmodifiableObservableList<ReadOnlyTask> lastShownList;
		if (taskType.equals(TASK_TYPE_FLOATING)) {
            lastShownList = model.getCurrentFilteredTaskList();
        } else {
            lastShownList = model.getCurrentFilteredScheduleList();
        }
		
		if(lastShownList.size() < targetIndex) {
			indicateAttemptToExecuteIncorrectCommand();
			return new CommandResult(UndoCommand.MESSAGE_FAIL);
		}
		ReadOnlyTask taskToUnmark = lastShownList.get(targetIndex -1);
		
		taskToUnmark = lastShownList.get(targetIndex -1);
		try {
			model.unMarkTask(taskToUnmark);
		} catch(TaskNotFoundException pufe) {
			return new CommandResult(UndoCommand.MESSAGE_FAIL);
		}
		return new CommandResult(UndoCommand.MESSAGE_SUCCESS);
	}


	@Override
	public CommandResult redo() {
	    UnmodifiableObservableList<ReadOnlyTask> lastShownList;
        if (taskType.equals(TASK_TYPE_FLOATING)) {
            lastShownList = model.getCurrentFilteredTaskList();
        } else {
            lastShownList = model.getCurrentFilteredScheduleList();
        }

        if (lastShownList.size() < targetIndex) {
            indicateAttemptToExecuteIncorrectCommand();
            return new CommandResult(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
        }

        ReadOnlyTask taskToMark = lastShownList.get(targetIndex - 1);

        try {
            model.markTask(taskToMark);
            model.getUndoStack().push(this);
        } catch (TaskNotFoundException pnfe) {
            return new CommandResult(String.format(RedoCommand.MESSAGE_FAIL));
        }
        return new CommandResult(String.format(RedoCommand.MESSAGE_SUCCESS));
	}

}
