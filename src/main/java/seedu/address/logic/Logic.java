package seedu.address.logic;

import javafx.collections.ObservableList;
import seedu.address.logic.commands.CommandResult;
import seedu.address.model.activity.ReadOnlyActivity;
import seedu.address.model.activity.task.ReadOnlyTask;

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

    /** Returns the filtered list of activities */
    ObservableList<ReadOnlyActivity> getFilteredPersonList();
    
    /** Returns the filtered list of overdue tasks */
    ObservableList<ReadOnlyActivity> getFilteredOverdueTaskList();
    
    /** Returns the filtered list of upcoming tasks and events */
    ObservableList<ReadOnlyActivity> getFilteredUpcomingList();

}
