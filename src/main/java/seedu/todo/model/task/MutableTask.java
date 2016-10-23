package seedu.todo.model.task;

import java.time.LocalDateTime;
import java.util.Set;

import seedu.todo.model.tag.Tag;

public interface MutableTask extends ImmutableTask {
    void setTitle(String title);

    void setPinned(boolean pinned);

    void setCompleted(boolean completed);

    void setDescription(String description);

    void setLocation(String location);

    void setStartTime(LocalDateTime startTime);

    void setEndTime(LocalDateTime endTime);

    void setTags(Set<Tag> tags);
}
