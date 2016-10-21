package seedu.todo.model.task;

import java.time.LocalDateTime;
import java.util.Set;

import seedu.todo.model.tag.Tag;

public interface MutableTask extends ImmutableTask {
    public void setTitle(String title);

    public void setPinned(boolean pinned);

    public void setCompleted(boolean completed);

    public void setDescription(String description);

    public void setLocation(String location);

    public void setStartTime(LocalDateTime startTime);

    public void setEndTime(LocalDateTime endTime);

    public void setTags(Set<Tag> tags);
}
