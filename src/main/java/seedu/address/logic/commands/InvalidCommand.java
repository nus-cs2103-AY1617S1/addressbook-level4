package seedu.address.logic.commands;

import seedu.address.commons.exceptions.IllegalValueException;

import java.util.HashSet;
import java.util.Set;

/**
 * Represents an invalid command that stores the error that made it invalid
 * Returns a CommandResult that has an error
 */
public class InvalidCommand extends Command {

    private String error;

    public InvalidCommand(String error) {
        assert error != null;

        this.error = error;
    }

    @Override
    public CommandResult execute() {
        return new CommandResult(error, true);
    }

}
