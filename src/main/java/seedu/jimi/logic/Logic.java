package seedu.jimi.logic;

import java.util.ArrayList;

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
    
    ObservableList<ReadOnlyTask> getFilteredFloatingTaskList();

    ObservableList<ReadOnlyTask> getFilteredCompletedTaskList();
    
    ObservableList<ReadOnlyTask> getFilteredIncompleteTaskList();
    
    ArrayList<ObservableList<ReadOnlyTask>> getFilteredDaysTaskList();
    
    ObservableList<ReadOnlyTask> getFilteredMondayTaskList();

    ObservableList<ReadOnlyTask> getFilteredTuesdayTaskList();

    ObservableList<ReadOnlyTask> getFilteredWednesdayTaskList();

    ObservableList<ReadOnlyTask> getFilteredThursdayTaskList();

    ObservableList<ReadOnlyTask> getFilteredFridayTaskList();

    ObservableList<ReadOnlyTask> getFilteredSaturdayTaskList();

    ObservableList<ReadOnlyTask> getFilteredSundayTaskList();

    ObservableList<ReadOnlyTask> getFilteredAgendaTaskList();

    ObservableList<ReadOnlyTask> getFilteredAgendaEventList();
}
