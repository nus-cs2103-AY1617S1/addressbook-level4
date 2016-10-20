package seedu.address.testutil;

import seedu.address.model.deadline.UniqueDeadlineList;
import seedu.address.model.tag.UniqueTagList;
import seedu.address.model.task.*;

/**
 * A mutable person object. For testing only.
 */
public class TestTask implements ReadOnlyTask {

    private Name name;
    private UniqueDeadlineList deadlines;
    private Priority priority;
    private UniqueTagList tags;

    public TestTask() {
    	deadlines = new UniqueDeadlineList();
        tags = new UniqueTagList();
    }

    public void setName(Name name) {
        this.name = name;
    }

//    public void setDeadline(Deadline deadline) {
//        this.deadline = deadline;
//    }

    public void setPriority(Priority priority) {
        this.priority = priority;
    }

    @Override
    public Name getName() {
        return name;
    }

    @Override
    public Priority getPriority() {
        return priority;
    }

    @Override
    public UniqueDeadlineList getDeadlines() {
        return deadlines;
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
        this.getDeadlines().getInternalList().stream().forEach(s -> sb.append("d/" + s.deadlineDate + " "));
        sb.append("p/" + this.getPriority().value + " ");
        this.getTags().getInternalList().stream().forEach(s -> sb.append("t/" + s.tagName + " "));
        return sb.toString();
    }
}
