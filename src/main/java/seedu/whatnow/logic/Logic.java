package seedu.whatnow.logic;

import java.text.ParseException;

import javafx.collections.ObservableList;
import seedu.whatnow.logic.commands.CommandResult;
import seedu.whatnow.model.task.ReadOnlyTask;

/**
 * API of the Logic component
 */
public interface Logic {
    /**
     * Executes the command and returns the result.
     * @param commandText The command as entered by the user.
     * @return the result of the command execution.
     * @throws ParseException 
     */
    CommandResult execute(String commandText) throws ParseException;

    /** Returns the filtered list of tasks */
    ObservableList<ReadOnlyTask> getFilteredTaskList();
    
    /** Returns the filtered list of schedule */
    ObservableList<ReadOnlyTask> getFilteredScheduleList();

}
