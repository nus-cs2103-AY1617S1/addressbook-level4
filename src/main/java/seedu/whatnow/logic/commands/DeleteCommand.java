//@@author A0139772U
package seedu.whatnow.logic.commands;

import java.util.logging.Logger;

import javafx.collections.ObservableList;
import seedu.whatnow.commons.core.LogsCenter;
import seedu.whatnow.commons.core.Messages;
import seedu.whatnow.commons.core.UnmodifiableObservableList;
import seedu.whatnow.model.task.ReadOnlyTask;
import seedu.whatnow.model.task.Task;
import seedu.whatnow.model.task.UniqueTaskList;
import seedu.whatnow.model.task.UniqueTaskList.DuplicateTaskException;
import seedu.whatnow.model.task.UniqueTaskList.TaskNotFoundException;
import seedu.whatnow.storage.StorageManager;

/**
 * Deletes a task identified using it's last displayed index from WhatNow.
 */
public class DeleteCommand extends Command {

    public static final String COMMAND_WORD = "delete";
    
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the task identified by the index number used in the last task listing.\n"
            + "Parameters: INDEX (must be a positive integer)\n" + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_DELETE_TASK_SUCCESS = "Deleted Task: %1$s";

    private static final String TASK_TYPE_FLOATING = "todo";
    private static final Logger logger = LogsCenter.getLogger(DeleteCommand.class);

    private final int targetIndex;
    private final String taskType;

    public DeleteCommand(String taskType, int targetIndex) {
        this.targetIndex = targetIndex;
        this.taskType = taskType;
    }

    /**
     * Execute DeleteCommand to delete a task at targetIndex
     */
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
        ReadOnlyTask taskToDelete = lastShownList.get(targetIndex - 1);

        assert model != null;
        try {
            int indexRemoved = model.deleteTask(taskToDelete);
            model.getUndoStack().push(COMMAND_WORD);
            model.getDeletedStackOfTasks().push(taskToDelete);
            model.getDeletedStackOfTasksIndex().push(indexRemoved);
        } catch (TaskNotFoundException tnfe) {
            logger.warning("Task not found: " + taskToDelete + "\n" + tnfe.getMessage());
        }
        return new CommandResult(String.format(MESSAGE_DELETE_TASK_SUCCESS, taskToDelete));
    }
}
