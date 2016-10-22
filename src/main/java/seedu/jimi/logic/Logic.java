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
    
    ObservableList<ReadOnlyTask> getFilteredDay1TaskList();

    ObservableList<ReadOnlyTask> getFilteredDay2TaskList();

    ObservableList<ReadOnlyTask> getFilteredDay3TaskList();

    ObservableList<ReadOnlyTask> getFilteredDay4TaskList();

    ObservableList<ReadOnlyTask> getFilteredDay5TaskList();

    ObservableList<ReadOnlyTask> getFilteredDay6TaskList();

    ObservableList<ReadOnlyTask> getFilteredDay7TaskList();

    ObservableList<ReadOnlyTask> getFilteredAgendaTaskList();

    ObservableList<ReadOnlyTask> getFilteredAgendaEventList();
}
