package harmony.mastermind.logic;

import harmony.mastermind.logic.commands.CommandResult;
import harmony.mastermind.model.task.ReadOnlyTask;
import javafx.collections.ObservableList;

//@@author A0124797R
/**
 * API of the Logic component
 */
public interface Logic {
    /**
     * Executes the command and returns the result.
     * @param commandText The command as entered by the user.
     * @return the result of the command execution.
     */
    CommandResult execute(String commandText, String currentTab);

    /** Returns the filtered list of tasks */
    ObservableList<ReadOnlyTask> getFilteredTaskList();

    /** Returns the filtered list of floating tasks*/
    ObservableList<ReadOnlyTask> getFilteredFloatingTaskList();

    /** Returns the filtered list of events*/
    ObservableList<ReadOnlyTask> getFilteredEventList();

    /** Returns the filtered list of deadlines*/
    ObservableList<ReadOnlyTask> getFilteredDeadlineList();

    /** Returns the filtered list of archived tasks*/
    ObservableList<ReadOnlyTask> getFilteredArchiveList();
    

}
