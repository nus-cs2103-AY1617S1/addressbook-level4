package seedu.todo.logic;

import javafx.collections.ObservableList;
import seedu.todo.logic.commands.CommandResult;
import seedu.todo.model.person.ReadOnlyPerson;
import seedu.todo.model.task.ImmutableTask;

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

    /** Returns the filtered list of persons */
    ObservableList<ReadOnlyPerson> getFilteredPersonList();
    
    ObservableList<ImmutableTask> getObservableTaskList();

}
