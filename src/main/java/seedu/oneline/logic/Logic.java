package seedu.oneline.logic;

import java.util.Map.Entry;

import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import seedu.oneline.logic.commands.CommandResult;
import seedu.oneline.model.task.ReadOnlyTask;
import seedu.oneline.model.tag.Tag;
import seedu.oneline.model.tag.TagColor;
import seedu.oneline.model.tag.TagColorMap; 

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

    /** Returns list of all tags */ 
    ObservableList<Tag> getTagList(); 
    
    /** Returns tag colors */ 
    TagColorMap getTagColorMap(); 
}
