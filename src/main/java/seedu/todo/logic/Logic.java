package seedu.todo.logic;

import javafx.collections.ObservableList;
import seedu.todo.logic.commands.CommandResult;
import seedu.todo.model.task.ImmutableTask;

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
