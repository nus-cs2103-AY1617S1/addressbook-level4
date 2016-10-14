package seedu.address.logic;

import javafx.collections.ObservableList;
import seedu.address.logic.commands.CommandResult;
import seedu.address.model.task.ReadOnlyTask;

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

     /** Returns the filtered list of tasks for today*/
    ObservableList<ReadOnlyTask> getFilteredTodayTaskList();

    /** Returns the filtered list of tasks for tomorrow*/
    ObservableList<ReadOnlyTask> getFilteredTomorrowTaskList();

    /** Returns the filtered list of tasks for the next 7 days*/
    ObservableList<ReadOnlyTask> getFilteredIn7DaysTaskList();

    /** Returns the filtered list of tasks for the next 30 days*/
    ObservableList<ReadOnlyTask> getFilteredIn30DaysTaskList();

    /** Returns the filtered list of all someday tasks */
    ObservableList<ReadOnlyTask> getFilteredSomedayTaskList();

}
