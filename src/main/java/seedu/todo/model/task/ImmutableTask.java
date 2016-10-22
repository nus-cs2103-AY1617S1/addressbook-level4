package seedu.todo.model.task;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import seedu.todo.model.tag.Tag;

public interface ImmutableTask {
    public String getTitle();
    
    public Optional<String> getDescription();
    
    public Optional<String> getLocation();
    
    public Optional<LocalDateTime> getStartTime();

    public Optional<LocalDateTime> getEndTime();
    
    public boolean isPinned();
    
    public boolean isCompleted();
    
    default public boolean isEvent() {
        return this.getStartTime().isPresent();
    }
    
    public Set<Tag> getTags();

    public LocalDateTime getCreatedAt();
    
    public UUID getUUID();
}
