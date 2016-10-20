package seedu.task.logic;

import javafx.collections.ObservableList;
import seedu.task.commons.exceptions.UndoableException;
import seedu.task.logic.commands.CommandResult;
import seedu.task.logic.commands.UndoableCommand;
import seedu.task.model.item.ReadOnlyEvent;
import seedu.task.model.item.ReadOnlyTask;

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
    
    /** Returns the filtered list of events */
    ObservableList<ReadOnlyEvent> getFilteredEventList();
}
