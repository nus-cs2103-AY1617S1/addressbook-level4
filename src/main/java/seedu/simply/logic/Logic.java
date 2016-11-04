package seedu.simply.logic;

import javafx.collections.ObservableList;
import seedu.simply.logic.commands.CommandResult;
import seedu.simply.model.task.ReadOnlyTask;

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
    ObservableList<ReadOnlyTask> getFilteredEventList();
    ObservableList<ReadOnlyTask> getFilteredDeadlineList();
    ObservableList<ReadOnlyTask> getFilteredTodoList();

}
