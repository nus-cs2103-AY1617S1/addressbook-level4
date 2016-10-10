package seedu.address.testutil;

import seedu.tasklist.model.tag.UniqueTagList;
import seedu.tasklist.model.task.*;

/**
 * A mutable Task object. For testing only.
 */
public class TestPerson implements ReadOnlyTask {

    private TaskDetails name;
    private int uniqueID;
    private EndTime endTime;
    private StartTime startTime;
    private Priority priority;
    private UniqueTagList tags;
    private boolean isComplete;

    public TestPerson() {
        tags = new UniqueTagList();
    }

    public void setName(TaskDetails name) {
        this.name = name;
    }

    public void setUniqueID(int uniqueID) {
        this.uniqueID = uniqueID;
    }

    public void setEmail(EndTime endTime) {
        this.endTime = endTime;
    }

    public void setPhone(StartTime startTime) {
        this.startTime = startTime;
    }

    @Override
    public TaskDetails getName() {
        return name;
    }

    @Override
    public StartTime getPhone() {
        return startTime;
    }

    @Override
    public EndTime getEmail() {
        return endTime;
    }

    @Override
    public int getUniqueID() {
        return uniqueID;
    }
    
    public void markAsComplete() {
        isComplete = true;
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
        sb.append("add " + this.getName().taskDetails + " ");
        sb.append("p/" + this.getPhone().value + " ");
        sb.append("e/" + this.getEmail().value + " ");
        sb.append("a/" + this.getUniqueID() + " ");
        this.getTags().getInternalList().stream().forEach(s -> sb.append("t/" + s.tagName + " "));
        return sb.toString();
    }

    @Override
    public boolean isComplete() {
        return isComplete;
    }

    @Override
    public Priority getPriority() {
        return priority;
    }
}
