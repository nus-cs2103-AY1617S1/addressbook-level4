package taskle.model.person;

import taskle.model.tag.UniqueTagList;

/**
 * A modifiable interface for a Task in the taskmanager.
 * Implementations should guarantee: details are present and not null, field values are validated.
 */
public interface ModifiableTask {
    
    void setName(Name name);

    void setTags(UniqueTagList tag);
    
    Name getName();
    
    UniqueTagList getTags();
}
