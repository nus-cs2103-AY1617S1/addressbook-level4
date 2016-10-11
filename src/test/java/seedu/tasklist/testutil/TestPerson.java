package seedu.tasklist.testutil;

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
    
    public void setPriority(Priority priority) {
        this.priority = priority;
    }

    @Override
    public TaskDetails getName() {
        return name;
    }

    @Override
    public StartTime getStartTime() {
        return startTime;
    }

    @Override
    public EndTime getEndTime() {
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
        sb.append("at " + this.getStartTime().value + " ");
        sb.append("by " + this.getEndTime().value + " ");
        sb.append("p/" + this.getPriority() + " ");
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
