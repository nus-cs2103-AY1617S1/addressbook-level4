package seedu.task.logic;

import java.util.ArrayList;

import javafx.collections.ObservableList;
import seedu.task.logic.commands.CommandResult;
import seedu.task.model.task.ReadOnlyTask;

/**
 * API of the Logic component
 * @@author A0147335E reused
 */
public interface Logic {
    /**
     * Executes the command and returns the result.
     * @param commandText The command as entered by the user.
     * @return the result of the command execution.
     */
    CommandResult execute(String commandText);

    /** Returns the filtered list of persons */
    ObservableList<ReadOnlyTask> getFilteredTaskList();

    /** Returns the undo list of commands */
    ArrayList<RollBackCommand> getUndoList();

    /** Returns the list of previous commands */
    ArrayList<String> getPreviousCommandList();
}
