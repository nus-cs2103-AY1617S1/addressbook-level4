package tars.logic;

import javafx.collections.ObservableList;
import tars.logic.commands.CommandResult;
import tars.model.task.ReadOnlyTask;
import tars.model.task.rsv.RsvTask;

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
    
    /** Returns the filtered list of RsvTasks */
    ObservableList<RsvTask> getFilteredRsvTaskList();

}
