package taskle.model;

import java.util.List;

import taskle.model.person.ModifiableTask;
import taskle.model.tag.Tag;

public interface ModifiableTaskManager {
    /**
     * Returns an unmodifiable view of tasks list
     */
    List<ModifiableTask> setTaskList();

    /**
     * Returns an unmodifiable view of tags list
     */
    List<Tag> setTagList();
    
}
