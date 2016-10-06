package seedu.taskman.logic;

import javafx.collections.ObservableList;
import seedu.taskman.logic.commands.CommandResult;
import seedu.taskman.model.task.EventInterface;

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
    ObservableList<EventInterface> getFilteredTaskList();

}
