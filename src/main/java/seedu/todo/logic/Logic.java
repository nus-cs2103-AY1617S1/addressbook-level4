package seedu.todo.logic;

import seedu.todo.logic.commands.CommandResult;

/**
 * API of the Logic component
 */
public interface Logic {
    /**
     * Executes the command and returns the result.
     * @param input The command as entered by the user.
     */
    CommandResult execute(String input);
}
