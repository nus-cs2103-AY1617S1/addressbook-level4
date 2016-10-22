package seedu.tasklist.logic.commands;

import seedu.tasklist.commons.core.Messages;
import seedu.tasklist.commons.core.UnmodifiableObservableList;
import seedu.tasklist.model.task.ReadOnlyTask;
import seedu.tasklist.model.task.UniqueTaskList.TaskCompletionException;
import seedu.tasklist.model.task.UniqueTaskList.TaskNotFoundException;

import static seedu.tasklist.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.Optional;

/**
 * Unmarks a task identified using it's last displayed index from the task list.
 */
public class UnmarkCommand extends Command {
    
    public static final String COMMAND_WORD = "unmark";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Unmark a task identified by the index number used in the last task listing.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: \n" + COMMAND_WORD + " 1\n" + COMMAND_WORD + " 2";

    public static final String MESSAGE_UNMARK_TASK_SUCCESS = "Task unmarked: %1$s";
    public static final String MESSAGE_UNMARKED_TASK = "This task is already unmarked in the task list.";

    public int targetIndex;
    
    public UnmarkCommand() {};
    
    public UnmarkCommand(int targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute() {

        UnmodifiableObservableList<ReadOnlyTask> lastShownList = model.getFilteredTaskList();

        if (lastShownList.size() < targetIndex) {
            indicateAttemptToExecuteIncorrectCommand();
            return new CommandResult(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
        }

        ReadOnlyTask taskToUnmark = lastShownList.get(targetIndex - 1);

        try {
            model.unmarkTask(taskToUnmark);
        } catch (TaskNotFoundException pnfe) {
            assert false : "The target task cannot be missing";
        } catch (TaskCompletionException e) {
            return new CommandResult(MESSAGE_UNMARKED_TASK);
        }

        return new CommandResult(String.format(MESSAGE_UNMARK_TASK_SUCCESS, taskToUnmark));
    }

    /**
     * Parses arguments in the context of the unmark task command.
     *
     * @param args full command args string
     * @return the prepared command
     */
    @Override
    public Command prepare(String args) {
        Optional<Integer> index = parseIndex(args);
        if(!index.isPresent()){
            return new IncorrectCommand(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, UnmarkCommand.MESSAGE_USAGE));
        }

        return new UnmarkCommand(index.get());

    }
    
}
