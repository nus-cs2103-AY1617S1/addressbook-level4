package seedu.dailyplanner.logic;

import javafx.beans.Observable;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.ObservableList;
import seedu.dailyplanner.logic.commands.CommandResult;
import seedu.dailyplanner.model.task.ReadOnlyTask;

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
    
    /** Returns the pinned task list */
    ObservableList<ReadOnlyTask> getPinnedTaskList();

    /** Returns the last task added index property held in model */
    IntegerProperty getLastTaskAddedIndexProperty();
    
    /** Returns the StringProperty holding the last shown date command */
    StringProperty getLastShowDateProperty();

}
