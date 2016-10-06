package seedu.todo.model.task;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import seedu.todo.model.tag.Tag;

/**
 * A dummy representation of the Task class.
 */
public class DummyTask implements ImmutableTask {
    
    /*Variables*/
    public String title, description, location;
    public LocalDateTime startTime, endTime;
    public boolean isPinned, isCompleted;
    public Set<Tag> tags;
    
    /*Override Methods for Immutable Task*/
    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public Optional<String> getDescription() {
        if (description != null) {
            return Optional.of(description);
        } else {
            return Optional.empty();
        }
    }

    @Override
    public Optional<String> getLocation() {
        if (location != null) {
            return Optional.of(location);
        } else {
            return Optional.empty();
        }
    }

    @Override
    public Optional<LocalDateTime> getStartTime() {
        if (startTime != null) {
            return Optional.of(startTime);
        } else {
            return Optional.empty();
        }
    }

    @Override
    public Optional<LocalDateTime> getEndTime() {
        if (endTime != null) {
            return Optional.of(endTime);
        } else {
            return Optional.empty();
        }
    }

    @Override
    public boolean isPinned() {
        return isPinned;
    }

    @Override
    public boolean isCompleted() {
        return isCompleted;
    }

    @Override
    public Set<Tag> getTags() {
        return tags;
    }

    @Override
    public UUID getUUID() {
        return null;
    }

    /*Setters Declaration*/
    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public void setPinned(boolean isPinned) {
        this.isPinned = isPinned;
    }

    public void setCompleted(boolean isCompleted) {
        this.isCompleted = isCompleted;
    }

    public void setTags(Set<Tag> tags) {
        this.tags = tags;
    }
    
    
    
}