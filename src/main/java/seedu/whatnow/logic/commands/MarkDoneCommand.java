//@@author A0139772U
package seedu.whatnow.logic.commands;

import static seedu.whatnow.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.whatnow.commons.core.Messages;
import seedu.whatnow.commons.core.UnmodifiableObservableList;
import seedu.whatnow.model.task.ReadOnlyTask;
import seedu.whatnow.model.task.UniqueTaskList.TaskNotFoundException;

/**
 * Marks a task identified using it's last displayed index from WhatNow as
 * completed.
 */
public class MarkDoneCommand extends Command {

    public static final String COMMAND_WORD = "done";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Mark the task identified by the index number used in the last task listing as completed.\n"
            + "Parameters: TODO/SCHEDULE INDEX (must be a positive integer)\n" + "Example: " + COMMAND_WORD
            + " todo 1\n" + "Example: " + COMMAND_WORD + " schedule 1";

    public static final String MESSAGE_MARK_TASK_SUCCESS = "Task marked as completed: %1$s";
    public static final String MESSAGE_MARK_TASK_FAIL = "Unable to mark task as complete";
    public static final String MESSAGE_MARK_INVALID_FORMAT = "Invalid format for done command";
    private static final String TASK_TYPE_FLOATING = "todo";
    private static final String TASK_TYPE_SCHEDULE = "schedule";
    public static final String MESSAGE_MISSING_INDEX = "Please specify index";
    public static final String MESSAGE_MISSING_TASKTYPE_AND_INDEX = "Please specify taskType and index";
    public final String taskType;
    public final int targetIndex;

    public MarkDoneCommand(String taskType, int targetIndex) {
        this.taskType = taskType;
        this.targetIndex = targetIndex;
    }

    /**
     * Executes the MarkDoneCommand to mark a task at targetIndex as completed
     */
    @Override
    public CommandResult execute() {
        UnmodifiableObservableList<ReadOnlyTask> lastShownList;
        if (taskType.equals(TASK_TYPE_FLOATING)) {
            model.updateFilteredListToShowAllIncomplete();
            lastShownList = model.getCurrentFilteredTaskList();
        } else if (taskType.equals(TASK_TYPE_SCHEDULE)) {
            model.updateFilteredScheduleListToShowAllIncomplete();
            lastShownList = model.getCurrentFilteredScheduleList();
        } else {
            return new CommandResult(String.format(MESSAGE_INVALID_COMMAND_FORMAT, MarkDoneCommand.MESSAGE_USAGE));
        }
        if (lastShownList.size() < targetIndex || targetIndex <0 ) {
            indicateAttemptToExecuteIncorrectCommand();
            return new CommandResult(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
        }

        ReadOnlyTask taskToMark = lastShownList.get(targetIndex - 1);

        try {
            model.markTask(taskToMark);
            model.getUndoStack().push(COMMAND_WORD);
            model.getStackOfMarkDoneTask().push(taskToMark);
        } catch (TaskNotFoundException tnfe) {
            return new CommandResult(String.format(MESSAGE_MARK_TASK_FAIL));
        }

        return new CommandResult(String.format(MESSAGE_MARK_TASK_SUCCESS, taskToMark));
    }
}