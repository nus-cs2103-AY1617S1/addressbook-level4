package seedu.todo.model.task;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import seedu.todo.model.tag.Tag;

public interface ImmutableTask {
    String getTitle();
    
    Optional<String> getDescription();
    
    Optional<String> getLocation();
    
    Optional<LocalDateTime> getStartTime();

    Optional<LocalDateTime> getEndTime();
    
    boolean isPinned();
    
    boolean isCompleted();
    
    default boolean isEvent() {
        return this.getStartTime().isPresent();
    }
    
    Set<Tag> getTags();

    LocalDateTime getCreatedAt();
    
    UUID getUUID();
}
