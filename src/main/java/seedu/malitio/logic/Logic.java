package seedu.malitio.logic;

import javafx.collections.ObservableList;
import seedu.malitio.logic.commands.CommandResult;
import seedu.malitio.model.task.ReadOnlyDeadline;
import seedu.malitio.model.task.ReadOnlyEvent;
import seedu.malitio.model.task.ReadOnlyFloatingTask;

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
    
    //@@ Annabel Eng A0129595N
    /** Returns the filtered list of tasks */
    ObservableList<ReadOnlyFloatingTask> getFilteredFloatingTaskList();
    
    ObservableList<ReadOnlyDeadline> getFilteredDeadlineList();
    
    ObservableList<ReadOnlyEvent> getFilteredEventList();

    
}
