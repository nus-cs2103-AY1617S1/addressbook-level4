package harmony.mastermind.logic;

import harmony.mastermind.logic.commands.CommandResult;
import harmony.mastermind.model.task.ReadOnlyTask;
import javafx.collections.ObservableList;

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
    
    //@@author A0124797R
    /** Returns the filtered list of archived tasks*/
    ObservableList<ReadOnlyTask> getFilteredArchiveList();
    

}
