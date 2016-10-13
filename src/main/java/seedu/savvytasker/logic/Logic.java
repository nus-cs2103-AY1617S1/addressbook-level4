package seedu.savvytasker.logic;

import javafx.collections.ObservableList;
import seedu.savvytasker.logic.commands.CommandResult;
import seedu.savvytasker.model.person.ReadOnlyTask;

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
    ObservableList<ReadOnlyTask> getFilteredTaskList();

}
