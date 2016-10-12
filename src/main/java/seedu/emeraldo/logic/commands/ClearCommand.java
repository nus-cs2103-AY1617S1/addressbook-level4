package seedu.emeraldo.logic.commands;

import seedu.emeraldo.model.Emeraldo;

/**
 * Clears the address book.
 */
public class ClearCommand extends Command {

    public static final String COMMAND_WORD = "clear";
    public static final String MESSAGE_SUCCESS = "Address book has been cleared!";

    public ClearCommand() {}


    @Override
    public CommandResult execute() {
        assert model != null;
        model.resetData(Emeraldo.getEmptyEmeraldo());
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
