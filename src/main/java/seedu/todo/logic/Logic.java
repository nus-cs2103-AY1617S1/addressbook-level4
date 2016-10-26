package seedu.todo.logic;

import javafx.collections.ObservableList;
import seedu.todo.logic.commands.CommandResult;
import seedu.todo.model.tag.Tag;
import seedu.todo.model.task.ReadOnlyTask;

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

    /** Returns the unmodfiable filtered list of tasks */
    ObservableList<ReadOnlyTask> getUnmodifiableFilteredTaskList();

    //@@author A0138967J
    ObservableList<ReadOnlyTask> getUnmodifiableTodayTaskList();
    
    //@@author A0142421X
    ObservableList<Tag> getUnmodifiableTagList();
}
