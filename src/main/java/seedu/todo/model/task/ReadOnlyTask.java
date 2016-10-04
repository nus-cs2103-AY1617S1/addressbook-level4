package seedu.todo.model.task;

import java.time.LocalDateTime;
import java.util.List;

/**
 * A read-only immutable interface that models a task in the to-do list.
 */
public interface ReadOnlyTask {
    
    String getTitle();
    String getDescription();
    LocalDateTime getDeadline();
    List<String> getTags();
    
    /**
     * Returns true if the object is a task, false if is an event.
     */
    boolean isTask();
    
}
