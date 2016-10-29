//@@author A0121657H
package seedu.oneline.logic.commands;

import seedu.oneline.commons.core.Messages;
import seedu.oneline.commons.core.UnmodifiableObservableList;
import seedu.oneline.commons.exceptions.IllegalCmdArgsException;
import seedu.oneline.commons.exceptions.IllegalValueException;
import seedu.oneline.logic.parser.Parser;
import seedu.oneline.model.task.ReadOnlyTask;
import seedu.oneline.model.task.Task;
import seedu.oneline.model.task.UniqueTaskList;
import seedu.oneline.model.task.UniqueTaskList.TaskNotFoundException;

/**
 * Marks a task identified using it's last displayed index from the task book as done.
 */
public class DoneCommand extends Command {

    public static final String COMMAND_WORD = "done";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Marks the task identified by the index number used in the last task listing as done.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_DONE_TASK_SUCCESS = "Marked task as done: %1$s";

    public static final String MESSAGE_TASK_ALR_DONE = "Task is already marked as done.";

    public int targetIndex;

    public DoneCommand(int targetIndex) {
        this.targetIndex = targetIndex;
    }
    
    public static DoneCommand createFromArgs(String args) throws IllegalValueException, IllegalCmdArgsException {
        Integer index = null;
        try {
            index = Parser.getIndexFromArgs(args);
        } catch (IllegalValueException e) {
            throw new IllegalValueException(Messages.getInvalidCommandFormatMessage(MESSAGE_USAGE));
        }
        if (index == null) {
            throw new IllegalValueException(Messages.getInvalidCommandFormatMessage(MESSAGE_USAGE));
        }
        
        return new DoneCommand(index);
    }


    @Override
    public CommandResult execute() {

        UnmodifiableObservableList<ReadOnlyTask> lastShownList = model.getFilteredTaskList();

        if (lastShownList.size() < targetIndex) {
            indicateAttemptToExecuteIncorrectCommand();
            return new CommandResult(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
        }

        ReadOnlyTask taskToDone = lastShownList.get(targetIndex - 1);
        Task doneTask = null;
        doneTask = taskToDone.markDone(taskToDone);
        
        if(taskToDone.isCompleted()) {
            return new CommandResult(String.format(MESSAGE_TASK_ALR_DONE, taskToDone));
        } else {
            try {
                model.replaceTask(taskToDone, doneTask);
            } catch (TaskNotFoundException pnfe) {
                assert false : "The target task cannot be missing";
            } catch (UniqueTaskList.DuplicateTaskException e) {
                assert false : "The task should not have the same completed status as before";
            }

            return new CommandResult(String.format(MESSAGE_DONE_TASK_SUCCESS, taskToDone));
        }
    }
    
    @Override
    public boolean canUndo() {
        return true;
    }
}
