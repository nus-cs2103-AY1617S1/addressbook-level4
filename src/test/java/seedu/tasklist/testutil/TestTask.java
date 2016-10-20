package seedu.tasklist.testutil;

import seedu.tasklist.model.tag.UniqueTagList;
import seedu.tasklist.model.task.*;

/**
 * A mutable task object. For testing only.
 */
public class TestTask implements ReadOnlyTask {

    private Title title;
    private DueDate dueDate;
    private Description description;
    private StartDate startDate;
    private UniqueTagList tags;
    private boolean isCompleted;
    
    public TestTask() {
        tags = new UniqueTagList();
    }

    public void setTitle(Title title) {
        this.title = title;
    }

    public void setDueDate(DueDate dueDate) {
        this.dueDate = dueDate;
    }

    public void setDescription(Description description) {
        this.description = description;
    }

    public void setStartDate(StartDate startDate) {
        this.startDate = startDate;
    }

    @Override
    public Title getTitle() {
        return title;
    }

    @Override
    public StartDate getStartDate() {
        return startDate;
    }

    @Override
    public Description getDescription() {
        return description;
    }

    @Override
    public DueDate getDueDate() {
        return dueDate;
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
        sb.append("s/" + this.getStartDate().toString().replaceAll(":", "").replaceAll("-", "") + " ");
        sb.append("e/" + this.getDueDate().toString().replaceAll(":", "").replaceAll("-", "") + " ");
        this.getTags().getInternalList().stream().forEach(s -> sb.append("t/" + s.tagName + " "));
        return sb.toString();
    }

    @Override
    public boolean isCompleted() {
        return isCompleted;
    }
}
