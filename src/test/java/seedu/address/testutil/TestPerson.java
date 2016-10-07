package seedu.address.testutil;

import seedu.tasklist.model.task.*;
import seedu.tasklsit.model.tag.UniqueTagList;

/**
 * A mutable person object. For testing only.
 */
public class TestPerson implements ReadOnlyTask {

    private Title title;
    private DueDate dueDate;
    private Description description;
    private StartDate startDate;
    private UniqueTagList tags;

    public TestPerson() {
        tags = new UniqueTagList();
    }

    public void setName(Title title) {
        this.title = title;
    }

    public void setAddress(DueDate dueDate) {
        this.dueDate = dueDate;
    }

    public void setEmail(Description description) {
        this.description = description;
    }

    public void setPhone(StartDate startDate) {
        this.startDate = startDate;
    }

    @Override
    public Title getTitle() {
        return title;
    }

    @Override
    public StartDate getGroup() {
        return startDate;
    }

    @Override
    public Description getDescription() {
        return description;
    }

    @Override
    public DueDate getAddress() {
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
        sb.append("p/" + this.getGroup().startDate + " ");
        sb.append("e/" + this.getDescription().description + " ");
        sb.append("a/" + this.getAddress().date + " ");
        this.getTags().getInternalList().stream().forEach(s -> sb.append("t/" + s.tagName + " "));
        return sb.toString();
    }
}
