package seedu.address.logic;

import javafx.collections.ObservableList;
import seedu.address.logic.commands.CommandResult;
import seedu.address.model.item.ReadOnlyTask;

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

    /** Returns the filtered list of undone tasks */
    ObservableList<ReadOnlyTask> getFilteredUndoneTaskList();
    
    /** Returns the filtered list of done tasks **/
    ObservableList<ReadOnlyTask> getFilteredDoneTaskList();

    /** Generates the tool tip for the current user input **/
    String generateToolTip(String commandText);

}
