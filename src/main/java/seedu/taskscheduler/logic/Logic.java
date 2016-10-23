package seedu.taskscheduler.logic;

import javafx.collections.ObservableList;
import seedu.taskscheduler.logic.commands.CommandResult;
import seedu.taskscheduler.model.task.*;

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

    /** Returns the filtered list of task */
    ObservableList<ReadOnlyTask> getFilteredTaskList();

}
