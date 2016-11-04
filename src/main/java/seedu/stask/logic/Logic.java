package seedu.stask.logic;

import javafx.collections.ObservableList;
import seedu.stask.logic.commands.CommandResult;
import seedu.stask.model.task.ReadOnlyTask;

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
    ObservableList<ReadOnlyTask> getFilteredDatedTaskList();

    /** Returns the filtered list of persons */
    ObservableList<ReadOnlyTask> getFilteredUndatedTaskList();
}
