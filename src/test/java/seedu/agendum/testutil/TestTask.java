package seedu.agendum.testutil;

import java.time.LocalDateTime;
import java.util.Optional;

import seedu.agendum.model.tag.UniqueTagList;
import seedu.agendum.model.task.*;

/**
 * A mutable task object. For testing only.
 */
public class TestTask implements ReadOnlyTask {

    private Name name;
    private boolean isCompleted;
    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;
    private UniqueTagList tags;

    public TestTask() {
        isCompleted = false;
        startDateTime = null;
        endDateTime = null;
        tags = new UniqueTagList();
    }

    /**
     * Copy constructor.
     */
    public TestTask(TestTask other) {
        this.name = other.name;
        this.isCompleted = other.isCompleted;
        this.startDateTime = other.startDateTime;
        this.endDateTime = other.endDateTime;
        this.tags = other.tags;
    }    

    public void setName(Name name) {
        this.name = name;
    }
    
    public void markAsCompleted() {
        this.isCompleted = true;
    }

    public void markAsUncompleted() {
        this.isCompleted = false;
    }

    public void setStartDateTime(Optional<LocalDateTime> startDateTime) {
        this.startDateTime = startDateTime.orElse(null);
    }
    
    public void setEndDateTime(Optional<LocalDateTime> endDateTime) {
        this.endDateTime = endDateTime.orElse(null);
    }

    @Override
    public Name getName() {
        return name;
    }
    
    @Override
    public boolean isCompleted() {
        return isCompleted;
    }

    @Override
    public Optional<LocalDateTime> getStartDateTime() {
        return Optional.ofNullable(startDateTime);
    }

    @Override
    public Optional<LocalDateTime> getEndDateTime() {
        return Optional.ofNullable(endDateTime);
    }

    @Override
    public UniqueTagList getTags() {
        return tags;
    }

    @Override
    public String toString() {
        return getAsText();
    }

    public String getAddCommand() {
        StringBuilder sb = new StringBuilder();
        sb.append("add " + this.getName().fullName + " ");
        this.getTags().getInternalList().stream().forEach(s -> sb.append("t/" + s.tagName + " "));
        return sb.toString();
    }

}
