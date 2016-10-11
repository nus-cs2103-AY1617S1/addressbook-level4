package seedu.todo.model.task;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import seedu.todo.model.tag.Tag;

public class ValidationTask implements ImmutableTask {
    private String title;
    private String description;
    private String location;

    private boolean pinned;
    private boolean completed;

    private LocalDateTime startTime;
    private LocalDateTime endTime;

    private Set<Tag> tags =new HashSet<Tag>();
    private UUID uuid;

    public ValidationTask(String title) {
        this.setTitle(title);
        this.uuid = UUID.randomUUID();
    }
    
    /**
     * Constructs a Task from a ReadOnlyTask
     */
    public ValidationTask(ImmutableTask task) {
        this.setTitle(task.getTitle());
        this.setDescription(task.getDescription().orElse(null));
        this.setLocation(task.getLocation().orElse(null));
        this.setStartTime(task.getStartTime().orElse(null));
        this.setEndTime(task.getEndTime().orElse(null));
        this.setCompleted(task.isCompleted());
        this.setPinned(task.isPinned());
        this.uuid = task.getUUID();
    }
    
    /**
     * Validates the task by checking the individuals are valid.
     * @return whether the task is valid or not
     */
    public boolean isValidTask() {
        return isValidTime();
    }
    
    private boolean isValidTime() {
        if (startTime == null && endTime == null) {
            return true;
        } else if (startTime != null && endTime != null) {
            return startTime.isBefore(endTime);
        } else {
            return false;
        }
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
        return Collections.unmodifiableSet(tags);
    }

    @Override
    public UUID getUUID() {
        return uuid;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setPinned(boolean pinned) {
        this.pinned = pinned;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
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

}
