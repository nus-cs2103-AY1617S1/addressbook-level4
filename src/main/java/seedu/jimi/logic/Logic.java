package seedu.jimi.logic;

import javafx.collections.ObservableList;
import seedu.jimi.logic.commands.CommandResult;
import seedu.jimi.model.task.ReadOnlyTask;

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
    
    /** Returns the filtered list of deadlineTasks */
    ObservableList<ReadOnlyTask> getFilteredDeadlineTaskList();
    
    /** Returns the filtered list of events */
    ObservableList<ReadOnlyTask> getFilteredEventList();

}
