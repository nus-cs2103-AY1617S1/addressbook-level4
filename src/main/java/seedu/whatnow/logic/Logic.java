package seedu.whatnow.logic;

import java.text.ParseException;

import javafx.collections.ObservableList;
import seedu.whatnow.logic.commands.CommandResult;
import seedu.whatnow.model.task.ReadOnlyTask;
import seedu.whatnow.model.task.UniqueTaskList.DuplicateTaskException;
import seedu.whatnow.model.task.UniqueTaskList.TaskNotFoundException;

/**
 * API of the Logic component
 */
public interface Logic {
    /**
     * Executes the command and returns the result.
     * @param commandText The command as entered by the user.
     * @return the result of the command execution.
     * @throws ParseException 
     * @throws TaskNotFoundException 
     * @throws DuplicateTaskException 
     */
    CommandResult execute(String commandText) throws ParseException, DuplicateTaskException, TaskNotFoundException;

    /** Returns the filtered list of tasks */
    ObservableList<ReadOnlyTask> getFilteredTaskList();
    
    /** Returns the filtered list of schedule */
    ObservableList<ReadOnlyTask> getFilteredScheduleList();

}
