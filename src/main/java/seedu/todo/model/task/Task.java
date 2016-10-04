package seedu.todo.model.task;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import com.google.common.collect.ImmutableSet;

import seedu.todo.model.tag.Tag;

public class Task implements ReadOnlyTask {
    private String title; 
    private String description; 
    private String location;
    private boolean pinned;
    private boolean completed;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private Set<Tag> tags = new HashSet<>();
    private UUID uuid;
    
    /**
     * Creates a new task
     */
    public Task(String title) {
        this.title = title;
        this.uuid = UUID.randomUUID();
    }
    
    /**
     * Constructs a Task from a ReadOnlyTask
     */
    public Task(ReadOnlyTask task) {
        this.title = task.getTitle();
        this.description = task.getDescription().orElse(null);
        this.location = task.getLocation().orElse(null);
        this.startTime = task.getStartTime().orElse(null);
        this.endTime = task.getEndTime().orElse(null);
        this.completed = task.isCompleted();
        this.pinned = task.isPinned();
        this.uuid = task.getUUID();
    }
    
    /**
     * Converts the provided ReadOnlyTask into a Task
     */
    public static Task unwrap(ReadOnlyTask task) {
        if (task instanceof Task) {
            return (Task) task;
        }
        
        return new Task(task);
    }
    
    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public Optional<String> getDescription() {
        return Optional.of(description);
    }

    @Override
    public Optional<String> getLocation() {
        return Optional.of(location);
    }

    @Override
    public Optional<LocalDateTime> getStartTime() {
        return Optional.of(startTime);
    }

    @Override
    public Optional<LocalDateTime> getEndTime() {
        return Optional.of(endTime);
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
    public ImmutableSet<Tag> getTags() {
        return (ImmutableSet<Tag>) tags;
    }

    public void setPinned(boolean pinned) {
        this.pinned = pinned;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
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

    public void setTags(Set<Tag> tags) {
        this.tags = tags;
    }

    @Override
    public UUID getUUID() {
        return uuid;
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true; 
        }
        
        if (!(o instanceof ReadOnlyTask)) {
            return false;
        }
        
        return uuid.equals(((ReadOnlyTask) o).getUUID());
    }
    
    @Override
    public int hashCode() {
        return uuid.hashCode();
    }
}
