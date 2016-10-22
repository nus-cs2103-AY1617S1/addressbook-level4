package seedu.todolist.logic;

import javafx.collections.ObservableList;
import seedu.todolist.logic.commands.CommandResult;
import seedu.todolist.model.task.ReadOnlyTask;

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

    /** Returns the filtered list of all tasks */
    ObservableList<ReadOnlyTask> getFilteredAllTaskList();
    
    /** Returns the filtered complete list of tasks */
    ObservableList<ReadOnlyTask> getFilteredCompleteTaskList();
    
    /** Returns the filtered incomplete list of tasks */
    ObservableList<ReadOnlyTask> getFilteredIncompleteTaskList();
    
    void setCurrentTab(String tab);

}
