package seedu.tasklist.logic;

import java.io.IOException;

import org.json.JSONException;
import org.json.simple.parser.ParseException;

import javafx.collections.ObservableList;
import seedu.tasklist.logic.commands.CommandResult;
import seedu.tasklist.model.ReadOnlyTaskList;
import seedu.tasklist.model.TaskCounter;
import seedu.tasklist.model.task.ReadOnlyTask;

/**
 * API of the Logic component
 */
public interface Logic {
    /**
     * Executes the command and returns the result.
     * @param commandText The command as entered by the user.
     * @return the result of the command execution.
     * @throws IOException 
     * @throws JSONException 
     * @throws ParseException 
     */
    CommandResult execute(String commandText) throws IOException, JSONException, ParseException;

    /** Returns the filtered list of persons */
    ObservableList<ReadOnlyTask> getFilteredTaskList();

	ReadOnlyTaskList getTaskList();

	TaskCounter getTaskCounter();

}
