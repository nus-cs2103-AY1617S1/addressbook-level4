package seedu.address.logic;

import javafx.collections.ObservableList;
import seedu.address.logic.commands.CommandResult;
import seedu.address.model.person.ReadOnlyDatedTask;

/**
 * API of the Logic component
 */
public interface Logic {
    /**
     * Executes the command and returns the result.
     * @param commandText The command as entered by the user.
     * @return the result of the command execution.
     */
    CommandResult execute(String commandText);

    /** Returns the filtered list of persons */
    ObservableList<ReadOnlyDatedTask> getFilteredPersonList();

    /** Returns the filtered list of persons */
    ObservableList<ReadOnlyDatedTask> getFilteredUndatedTaskList();
}
