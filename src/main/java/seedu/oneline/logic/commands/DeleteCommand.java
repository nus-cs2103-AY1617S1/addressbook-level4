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
    public static final String MESSAGE_DELETE_CAT_SUCCESS = "Deleted category: %1$s";

    public static DeleteCommand createFromArgs(String args) throws IllegalCmdArgsException, IllegalValueException {
        args = args.trim();
        if (args.startsWith(CommandConstants.TAG_PREFIX)){
            return DeleteTagCommand.createFromArgs(args);
        } else {
            return DeleteTaskCommand.createFromArgs(args);
        }
    }
    
    @Override
    public boolean canUndo() {
        return true;
    }

}
