package seedu.taskscheduler.logic;

import javafx.collections.ObservableList;
import seedu.taskscheduler.logic.commands.CommandResult;
import seedu.taskscheduler.model.tag.Tag;
import seedu.taskscheduler.model.task.*;

/**
 * API of the Logic component
 */
public interface Logic {
    /**
     * Executes the command and returns the result.
     * @param commandText The command as entered by the user.
     * @param savePrevCommand CommandHistory logs the previous command if true
     * @return the result of the command execution.
     */
    CommandResult execute(String commandText);
    CommandResult execute(String commandText, boolean savePrevCommand);

    /** Returns the filtered list of task */
    ObservableList<ReadOnlyTask> getFilteredTaskList();

    /** Returns the priority filtered list of task */
    ObservableList<ReadOnlyTask> getPriorityFilteredTaskList();
    
    /** Returns the priority filtered list of task */
    ObservableList<Tag> getUnmodifiableTagList();


}
