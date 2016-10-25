package seedu.ggist.logic;

import javafx.collections.ObservableList;
import seedu.ggist.logic.commands.CommandResult;
import seedu.ggist.model.task.ReadOnlyTask;

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
    
    /** Returns the sorted list of tasks */
    ObservableList<ReadOnlyTask> getSortedTaskList();
    
    /** Return the last listing of the filtered tasks */
    String getListing();

}
