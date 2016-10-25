package seedu.todo.model.task;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import javafx.beans.Observable;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import seedu.todo.model.tag.Tag;

//@@author A0139021U-reused
/**
 * Represents a single task
 */
public class Task implements MutableTask {
    private StringProperty title = new SimpleStringProperty();
    private StringProperty description = new SimpleStringProperty();
    private StringProperty location = new SimpleStringProperty();

    private BooleanProperty pinned = new SimpleBooleanProperty();
    private BooleanProperty completed = new SimpleBooleanProperty();

    private ObjectProperty<LocalDateTime> startTime = new SimpleObjectProperty<>();
    private ObjectProperty<LocalDateTime> endTime = new SimpleObjectProperty<>();

    private ObjectProperty<Set<Tag>> tags = new SimpleObjectProperty<>(new HashSet<Tag>());
    private LocalDateTime createdAt = LocalDateTime.now();
    private UUID uuid;

    /**
     * Creates a new task
     */
    public Task(String title) {
        this.setTitle(title);
        this.uuid = UUID.randomUUID();
    }

    /**
     * Constructs a Task from a ReadOnlyTask
     */
    public Task(ImmutableTask task) {
        this.setTitle(task.getTitle());
        this.setDescription(task.getDescription().orElse(null));
        this.setLocation(task.getLocation().orElse(null));
        this.setStartTime(task.getStartTime().orElse(null));
        this.setEndTime(task.getEndTime().orElse(null));
        this.setCompleted(task.isCompleted());
        this.setPinned(task.isPinned());
        this.setCreatedAt(task.getCreatedAt());
        this.uuid = task.getUUID();
    }

    @Override
    public String getTitle() {
        return title.get();
    }

    @Override
    public Optional<String> getDescription() {
        return Optional.ofNullable(description.get());
    }

    @Override
    public Optional<String> getLocation() {
        return Optional.ofNullable(location.get());
    }

    @Override
    public Optional<LocalDateTime> getStartTime() {
        return Optional.ofNullable(startTime.get());
    }

    @Override
    public Optional<LocalDateTime> getEndTime() {
        return Optional.ofNullable(endTime.get());
    }

    @Override
    public boolean isPinned() {
        return pinned.get();
    }

    @Override
    public boolean isCompleted() {
        return completed.get();
    }

    @Override
    public Set<Tag> getTags() {
        return Collections.unmodifiableSet(tags.get());
    }
    
    @Override
    public LocalDateTime getCreatedAt() { return createdAt; }

    @Override
    public void setTitle(String title) {
        this.title.set(title);
    }

    @Override
    public void setPinned(boolean pinned) {
        this.pinned.set(pinned);
    }

    @Override
    public void setCompleted(boolean completed) {
        this.completed.set(completed);
    }

    @Override
    public void setDescription(String description) {
        this.description.set(description);
    }

    @Override
    public void setLocation(String location) {
        this.location.set(location);
    }

    @Override
    public void setStartTime(LocalDateTime startTime) {
        this.startTime.set(startTime);
    }

    @Override
    public void setEndTime(LocalDateTime endTime) {
        this.endTime.set(endTime);
    }

    @Override
    public void setTags(Set<Tag> tags) {
        this.tags.set(tags);
    }
    
    public void setCreatedAt(LocalDateTime createdAt) {
        if (createdAt == null) {
            createdAt = LocalDateTime.now();
        }
        
        this.createdAt = createdAt;
    }

    public Observable[] getObservableProperties() {
        return new Observable[] {
            title, description, location, startTime, endTime, tags, completed, pinned,
        };
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

        if (!(o instanceof ImmutableTask)) {
            return false;
        }

        return uuid.equals(((ImmutableTask) o).getUUID());
    }

    @Override
    public int hashCode() {
        return uuid.hashCode();
    }
    
    @Override
    public String toString() {
        return title.get();
    }
}
