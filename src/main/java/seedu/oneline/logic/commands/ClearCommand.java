package seedu.oneline.logic.commands;

import seedu.oneline.commons.exceptions.IllegalCmdArgsException;
import seedu.oneline.commons.exceptions.IllegalValueException;
import seedu.oneline.model.TaskBook;

/**
 * Clears the task book.
 */
public class ClearCommand extends Command {

    public static final String COMMAND_WORD = "clear";
    public static final String MESSAGE_SUCCESS = "Task book has been cleared!";

    public ClearCommand() {}

    public static ClearCommand createFromArgs(String args) {
        return new ClearCommand();
    }
    
    @Override
    public CommandResult execute() {
        assert model != null;
        model.resetData(TaskBook.getEmptyTaskBook());
        return new CommandResult(MESSAGE_SUCCESS);
    }
    
    @Override
    public boolean canUndo() {
        return true;
    }
}
