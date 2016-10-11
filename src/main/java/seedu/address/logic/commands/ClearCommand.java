package seedu.address.logic.commands;

import seedu.address.model.TaskBook;

/**
 * Clears the task book.
 */
public class ClearCommand extends Command {

    public static final String COMMAND_WORD = "clear";
    public static final String MESSAGE_SUCCESS = "Task book has been cleared!";

    public ClearCommand() {}

    @Override
    public CommandResult execute() {
        assert model != null;
        model.resetData(new TaskBook());
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
