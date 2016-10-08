package seedu.emeraldo.logic;

import javafx.collections.ObservableList;
import seedu.address.model.person.ReadOnlyTask;
import seedu.emeraldo.logic.commands.CommandResult;

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
    ObservableList<ReadOnlyTask> getFilteredPersonList();

}
