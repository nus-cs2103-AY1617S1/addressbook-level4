package seedu.malitio.model;


import seedu.malitio.model.tag.Tag;
import seedu.malitio.model.tag.UniqueTagList;
import seedu.malitio.model.task.ReadOnlyDeadline;
import seedu.malitio.model.task.ReadOnlyEvent;
import seedu.malitio.model.task.ReadOnlyFloatingTask;
import seedu.malitio.model.task.UniqueDeadlineList;
import seedu.malitio.model.task.UniqueEventList;
import seedu.malitio.model.task.UniqueFloatingTaskList;

import java.util.List;

/**
 * Unmodifiable view of an malitio
 */
public interface ReadOnlyMalitio {

    UniqueTagList getUniqueTagList();

    UniqueFloatingTaskList getUniqueFloatingTaskList();
    
    UniqueDeadlineList getUniqueDeadlineList();
    
    UniqueEventList getUniqueEventList();

    /**
     * Returns an unmodifiable view of tasks list
     */
    List<ReadOnlyFloatingTask> getFloatingTaskList();
    
    /**
     * Returns an unmodifiable view of deadlines list
     */
    List<ReadOnlyDeadline> getDeadlineList();
    
    /**
     * Returns an unmodifiable view of deadlines list
     */
    List<ReadOnlyEvent> getEventList();    

    /**
     * Returns an unmodifiable view of tags list
     */
    List<Tag> getTagList();

}
