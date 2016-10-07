package taskle.model;

import java.util.List;

import taskle.model.person.ModifiableTask;
import taskle.model.person.UniqueTaskList;
import taskle.model.tag.Tag;
import taskle.model.tag.UniqueTagList;

public interface ModifiableTaskManager {
    
    UniqueTagList getModifiableUniqueTagList();

    UniqueTaskList getModifiableUniqueTaskList();
    
    /**
     * Returns an unmodifiable view of tasks list
     */
    List<ModifiableTask> getModifiableTaskList();

    /**
     * Returns an unmodifiable view of tags list
     */
    List<Tag> getModifiableTagList();
    
}
