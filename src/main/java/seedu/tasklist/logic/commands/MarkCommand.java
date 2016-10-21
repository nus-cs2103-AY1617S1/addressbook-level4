package seedu.tasklist.logic.commands;

import seedu.tasklist.commons.core.Messages;
import seedu.tasklist.commons.core.UnmodifiableObservableList;
import seedu.tasklist.model.task.ReadOnlyTask;
import seedu.tasklist.model.task.UniqueTaskList.TaskCompletionException;
import seedu.tasklist.model.task.UniqueTaskList.TaskNotFoundException;

import static seedu.tasklist.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.Optional;

/**
 * Marks a task identified using it's last displayed index from the task list.
 */
public class MarkCommand extends Command {
    
    public static final String COMMAND_WORD = "mark";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Marks a task identified by the index number used in the last task listing.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: \n" + COMMAND_WORD + " 1\n" + COMMAND_WORD + " 2";

    public static final String MESSAGE_MARK_TASK_SUCCESS = "Task marked: %1$s";
    public static final String MESSAGE_MARKED_TASK = "This task is already marked in the task list.";

    public int targetIndex;
    
    public MarkCommand() {};
    
    public MarkCommand(int targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute() {

        UnmodifiableObservableList<ReadOnlyTask> lastShownList = model.getFilteredTaskList();

        if (lastShownList.size() < targetIndex) {
            indicateAttemptToExecuteIncorrectCommand();
            return new CommandResult(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
        }

        ReadOnlyTask taskToMark = lastShownList.get(targetIndex - 1);

        try {
            model.markTask(taskToMark);
        } catch (TaskNotFoundException pnfe) {
            assert false : "The target task cannot be missing";
        } catch (TaskCompletionException e) {
            return new CommandResult(MESSAGE_MARKED_TASK);
        }

        return new CommandResult(String.format(MESSAGE_MARK_TASK_SUCCESS, taskToMark));
    }

    /**
     * Parses arguments in the context of the mark task command.
     *
     * @param args full command args string
     * @return the prepared command
     */
    @Override
    public Command prepare(String args) {
        Optional<Integer> index = parseIndex(args);
        if(!index.isPresent()){
            return new IncorrectCommand(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_USAGE));
        }

        return new MarkCommand(index.get());
    }
    
}
