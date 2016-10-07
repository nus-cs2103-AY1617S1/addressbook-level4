package seedu.todo.model.task;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import seedu.todo.model.tag.Tag;

public class MockTask implements ImmutableTask {
    public String title; 
    public String description;
    public String location;
    public LocalDateTime startTime;
    public LocalDateTime endTime;
    public boolean pinned; 
    public boolean completed;
    public UUID uuid = UUID.randomUUID();
    
    public MockTask(String title) {
        this.title = title;
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public Optional<String> getDescription() {
        return Optional.ofNullable(description);
    }

    @Override
    public Optional<String> getLocation() {
        return Optional.ofNullable(location);
    }

    @Override
    public Optional<LocalDateTime> getStartTime() {
        return Optional.ofNullable(startTime);
    }

    @Override
    public Optional<LocalDateTime> getEndTime() {
        return Optional.ofNullable(endTime);
    }

    @Override
    public boolean isPinned() {
        return pinned;
    }

    @Override
    public boolean isCompleted() {
        return completed;
    }

    @Override
    public Set<Tag> getTags() {
        return new HashSet<Tag>();
    }

    @Override
    public UUID getUUID() {
        return uuid;
    }

}
