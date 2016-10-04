package seedu.todo.model.task;

import java.time.LocalDateTime;
import java.util.Optional;
import com.google.common.collect.ImmutableSet;

import seedu.todo.model.tag.Tag;

public interface ReadOnlyTask {
    public String getTitle();
    
    public Optional<String> getDescription();
    
    public Optional<String> getLocation();
    
    public Optional<LocalDateTime> getStartTime();

    public Optional<LocalDateTime> getEndTime();
    
    public boolean isPinned();
    
    public boolean isCompleted();
    
    public ImmutableSet<Tag> getTags();
    
}
