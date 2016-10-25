package seedu.oneline.logic.commands;

import seedu.oneline.commons.core.Messages;
import seedu.oneline.commons.core.UnmodifiableObservableList;
import seedu.oneline.commons.exceptions.IllegalCmdArgsException;
import seedu.oneline.commons.exceptions.IllegalValueException;
import seedu.oneline.logic.parser.Parser;
import seedu.oneline.model.task.ReadOnlyTask;
import seedu.oneline.model.task.UniqueTaskList.TaskNotFoundException;

/**
 * Marks a task identified using it's last displayed index from the task book as done.
 */
public class UndoneCommand extends Command {

    public static final String COMMAND_WORD = "undone";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Marks the task identified by the index number used in the last task listing as not done.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_DONE_TASK_SUCCESS = "Marked task as not done: %1$s";

    public static final String MESSAGE_TASK_ALR_NOT_DONE = "Task is currently marked not done.";

    public final int targetIndex;

    public UndoneCommand(String args) throws IllegalCmdArgsException {
        Integer index = null;
        try {
            index = Parser.getIndexFromArgs(args);
        } catch (IllegalValueException e) {
            throw new IllegalCmdArgsException(Messages.getInvalidCommandFormatMessage(MESSAGE_USAGE));
        }
        if (index == null) {
            throw new IllegalCmdArgsException(Messages.getInvalidCommandFormatMessage(MESSAGE_USAGE));
        }
        this.targetIndex = index;
    }

    public UndoneCommand(int targetIndex) {
        this.targetIndex = targetIndex;
    }


    @Override
    public CommandResult execute() {

        UnmodifiableObservableList<ReadOnlyTask> lastShownList = model.getFilteredTaskList();

        if (lastShownList.size() < targetIndex) {
            indicateAttemptToExecuteIncorrectCommand();
            return new CommandResult(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
        }

        ReadOnlyTask taskToUndone = lastShownList.get(targetIndex - 1);

        if(!taskToUndone.isCompleted()) {
            return new CommandResult(String.format(MESSAGE_TASK_ALR_NOT_DONE, taskToUndone));
        } else {
            try {
                model.undoneTask(targetIndex - 1);
            } catch (TaskNotFoundException pnfe) {
                assert false : "The target task cannot be missing";
            }

            return new CommandResult(String.format(MESSAGE_DONE_TASK_SUCCESS, taskToUndone));
        }
    }

}
