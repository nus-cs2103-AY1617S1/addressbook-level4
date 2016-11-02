package seedu.forgetmenot.logic;

import javafx.collections.ObservableList;
import seedu.forgetmenot.commons.exceptions.IllegalValueException;
import seedu.forgetmenot.logic.commands.CommandResult;
import seedu.forgetmenot.model.task.ReadOnlyTask;

/**
 * API of the Logic component
 */
public interface Logic {
    /**
     * Executes the command and returns the result.
     * @param commandText The command as entered by the user.
     * @return the result of the command execution.
     * @throws IllegalValueException 
     */
    CommandResult execute(String commandText) throws IllegalValueException;

    /** Returns the filtered list of tasks */
    ObservableList<ReadOnlyTask> getFilteredTaskList();

}
