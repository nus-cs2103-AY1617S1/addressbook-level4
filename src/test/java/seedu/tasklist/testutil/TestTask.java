package seedu.tasklist.testutil;

import seedu.tasklist.model.tag.UniqueTagList;
import seedu.tasklist.model.task.*;

/**
 * A mutable task object. For testing only.
 */
public class TestTask implements ReadOnlyTask {

    private Title title;
    private DateTime endDateTime;
    private Description description;
    private DateTime startDateTime;
    private UniqueTagList tags;
    private boolean isCompleted;
    private boolean isOverdue;
    private boolean isFloating;
    
    public TestTask() {
        tags = new UniqueTagList();
    }

    public void setTitle(Title title) {
        this.title = title;
    }

    public void setDescription(Description description) {
        this.description = description;
    }

    public void setStartDateTime(DateTime startDateTime) {
        this.startDateTime = startDateTime;
    }
    
    public void setEndDateTime(DateTime endDateTime) {
        this.endDateTime = endDateTime;
    }

    @Override
    public boolean isCompleted() {
        return isCompleted;
    }
    
    @Override
    public boolean isOverdue() {
        return isOverdue;
    }
    
    @Override
    public boolean isFloating() {
        return isFloating;
    }
    
    @Override
    public Title getTitle() {
        return title;
    }

    @Override
    public Description getDescription() {
        return description;
    }
    
    @Override
    public DateTime getStartDateTime() {
        return startDateTime;
    }

    @Override
    public DateTime getEndDateTime() {
        return endDateTime;
    }
    
    public void setCompleted(boolean b) {
        isCompleted = b;
    }
    
    public void setOverdue(boolean isOverdue) {
        this.isOverdue = isOverdue;
    }

    public void setFloating(boolean isFloating) {
        this.isFloating = isFloating;
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
        sb.append("add " + this.getTitle().fullTitle + " ");
        sb.append("d/" + this.getDescription().description + " ");
        sb.append("s/" + this.getStartDateTime().toString().replaceAll(":", "").replaceAll("-", "") + " ");
        sb.append("e/" + this.getEndDateTime().toString().replaceAll(":", "").replaceAll("-", "") + " ");
        this.getTags().getInternalList().stream().forEach(s -> sb.append("t/" + s.tagName + " "));
        return sb.toString();
    }
}
