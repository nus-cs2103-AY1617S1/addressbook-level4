package seedu.task.logic;

import javafx.collections.ObservableList;
import seedu.task.logic.commands.CommandResult;
import seedu.task.model.task.ReadOnlyTask;

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

    //@@author A0141052Y
    /**
     * Updates the task list filter with the specified keyword
     * @param keyword to be used to filter the tasks
     */
    void updateTaskListFilter(String keyword);
    //@@author

    /** Returns the filtered list of tasks */
    ObservableList<ReadOnlyTask> getFilteredTaskList();

}
