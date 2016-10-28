package seedu.flexitrack.model;

import java.util.List;

import seedu.flexitrack.model.task.ReadOnlyTask;
import seedu.flexitrack.model.task.UniqueTaskList;

/**
 * Unmodifiable view of an address book
 */
public interface ReadOnlyFlexiTrack {


    UniqueTaskList getUniqueTaskList();

    /**
     * Returns an unmodifiable view of persons list
     */
    List<ReadOnlyTask> getTaskList();

}
