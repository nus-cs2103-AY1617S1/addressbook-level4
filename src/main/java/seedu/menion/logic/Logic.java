package seedu.menion.logic;

import javafx.collections.ObservableList;
import seedu.menion.logic.commands.CommandResult;
import seedu.menion.model.activity.ReadOnlyActivity;

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
    
    //@@author A0146752B
    /** Returns the filtered list of tasks */
    ObservableList<ReadOnlyActivity> getFilteredTaskList();
    
    /** Returns the filtered list of floating tasks */
    ObservableList<ReadOnlyActivity> getFilteredFloatingTaskList();
    
    /** Returns the filtered list of events */
    ObservableList<ReadOnlyActivity> getFilteredEventList();
    //@@author
}
