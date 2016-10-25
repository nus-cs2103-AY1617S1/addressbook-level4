package seedu.todo.logic;

import seedu.todo.logic.commands.CommandResult;

//@@author A0135817B
/**
 * API of the Logic component
 */
public interface Logic {
    /**
     * Executes the command and returns the result.
     * @param input The command as entered by the user.
     */
    CommandResult execute(String input);

    /**
     * Receives the intermediate product of the command and sends back a CommandPreview.
     * @param input The intermediate input as entered by the user.
     */
    void preview(String input);
}
