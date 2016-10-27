package seedu.cmdo.logic;

import javafx.collections.ObservableList;
import seedu.cmdo.logic.commands.CommandResult;
import seedu.cmdo.model.task.ReadOnlyTask;

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
    
    /** Returns the first-run list of tasks */
    ObservableList<ReadOnlyTask> getFilteredTaskList(boolean firstRun);
    
    ObservableList<ReadOnlyTask> getBlockedList();

	ObservableList<ReadOnlyTask> getAllTaskList();


}
