package seedu.emeraldo.logic.commands;

import seedu.emeraldo.model.Emeraldo;

/**
 * Clears the Emeraldo.
 */
public class ClearCommand extends Command {

    public static final String COMMAND_WORD = "clear";
    public static final String MESSAGE_SUCCESS = "Emeraldo has been cleared!";

    public ClearCommand() {}


    @Override
    public CommandResult execute() {
        assert model != null;
        model.clearEmeraldo();
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
