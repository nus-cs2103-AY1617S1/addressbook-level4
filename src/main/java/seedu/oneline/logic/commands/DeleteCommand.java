package seedu.oneline.logic.commands;

import seedu.oneline.commons.core.Messages;
import seedu.oneline.commons.core.UnmodifiableObservableList;
import seedu.oneline.commons.exceptions.IllegalCmdArgsException;
import seedu.oneline.commons.exceptions.IllegalValueException;
import seedu.oneline.logic.parser.Parser;
import seedu.oneline.model.task.ReadOnlyTask;
import seedu.oneline.model.task.UniqueTaskList.TaskNotFoundException;

/**
 * Deletes a task identified using it's last displayed index from the task book.
 */
public class DeleteCommand extends Command {

    public static final String COMMAND_WORD = "del";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes a task or category in the task book. \n"
                    + " === Delete Task === \n"
                    + "Parameters: INDEX (must be a positive integer)\n"
                    + "Example: " + COMMAND_WORD
                    + " 1\n"
                    + " === Delete Category === \n"
                    + "Parameters: #catname \n"
                    + "Example: " + COMMAND_WORD
                    + "#meeting";

    public static final String MESSAGE_DELETE_TASK_SUCCESS = "Deleted task: %1$s";

    public final int targetIndex;

    public DeleteCommand(int targetIndex) {
        this.targetIndex = targetIndex;
    }

    public static DeleteCommand createFromArgs(String args) throws IllegalCmdArgsException {
        if (args.trim().startsWith(CommandConstants.TAG_PREFIX)){
            return DeleteTagCommand.createFromArgs(args);
        }
        
        Integer index = null;
        try {
            index = Parser.getIndexFromArgs(args);
        } catch (IllegalValueException e) {
            throw new IllegalCmdArgsException(Messages.getInvalidCommandFormatMessage(MESSAGE_USAGE));
        }
        if (index == null) {
            throw new IllegalCmdArgsException(Messages.getInvalidCommandFormatMessage(MESSAGE_USAGE));
        }
        return new DeleteCommand(index);
    }

    @Override
    public CommandResult execute() {

        UnmodifiableObservableList<ReadOnlyTask> lastShownList = model.getFilteredTaskList();

        if (lastShownList.size() < targetIndex) {
            indicateAttemptToExecuteIncorrectCommand();
            return new CommandResult(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
        }

        ReadOnlyTask taskToDelete = lastShownList.get(targetIndex - 1);

        try {
            model.deleteTask(taskToDelete);
        } catch (TaskNotFoundException pnfe) {
            assert false : "The target task cannot be missing";
        }

        return new CommandResult(String.format(MESSAGE_DELETE_TASK_SUCCESS, taskToDelete));
    }
    
    @Override
    public boolean canUndo() {
        return true;
    }

}
