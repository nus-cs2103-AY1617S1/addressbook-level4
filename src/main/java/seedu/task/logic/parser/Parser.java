package seedu.task.logic.parser;

import seedu.task.logic.commands.Command;

/**
 * API of the Parser component
 */

public interface Parser {
    
    /**
     * Prepares the command and returns the prepared Command.
     * @param args The arguments as entered by the user.
     * @return the prepared Command for execution.
     */
    public Command prepare(String args);
}
