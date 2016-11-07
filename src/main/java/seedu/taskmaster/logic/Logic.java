package seedu.taskmaster.logic;

import javafx.collections.ObservableList;
import seedu.taskmaster.logic.commands.CommandResult;
import seedu.taskmaster.model.task.TaskOccurrence;

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

    /** Returns the filtered list of tasks. */
    ObservableList<TaskOccurrence> getFilteredTaskList();

}
