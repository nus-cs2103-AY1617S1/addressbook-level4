package seedu.oneline.logic;

import javafx.collections.ObservableList;
import seedu.oneline.logic.commands.CommandResult;
import seedu.oneline.model.task.ReadOnlyTask;

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

    /** Returns the filtered list of tasks */
    ObservableList<ReadOnlyTask> getFilteredTaskList();
    
    //@@author: A0138848M
    /** Returns the sorted filtered list of tasks */
    ObservableList<ReadOnlyTask> getSortedFilteredTaskList();
}
