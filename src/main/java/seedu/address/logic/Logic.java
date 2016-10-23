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

    //@@author A0142184L
    /** Returns the list of all non-done tasks (not-done and overdue tasks) */
	ObservableList<ReadOnlyTask> getNonDoneTaskList();

	/** Returns the list of all today tasks */
	ObservableList<ReadOnlyTask> getTodayTaskList();

	/** Returns the list of all tomorrow tasks */
	ObservableList<ReadOnlyTask> getTomorrowTaskList();

	/** Returns the list of all in-7-days tasks */
	ObservableList<ReadOnlyTask> getIn7DaysTaskList();

	/** Returns the list of all in-30-days tasks */
	ObservableList<ReadOnlyTask> getIn30DaysTaskList();

	/** Returns the list of all someday tasks */
	ObservableList<ReadOnlyTask> getSomedayTaskList();

}
