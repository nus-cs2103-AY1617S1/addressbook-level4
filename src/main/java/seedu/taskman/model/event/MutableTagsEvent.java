package seedu.taskman.model.event;

import seedu.taskman.model.tag.UniqueTagList;

/**
 * An interface for classes to allow setting tags
 */
public interface MutableTagsEvent extends ReadOnlyEvent{

    void setTags(UniqueTagList replacement);
}
