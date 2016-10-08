package seedu.agendum.testutil;

import java.time.LocalDateTime;

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
        tags = new UniqueTagList();
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

    @Override
    public Name getName() {
        return name;
    }
    
    @Override
    public boolean isCompleted() {
        return isCompleted;
    }

    @Override
    public LocalDateTime getStartDateTime() {
        return startDateTime;
    }

    @Override
    public LocalDateTime getEndDateTime() {
        return endDateTime;
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
