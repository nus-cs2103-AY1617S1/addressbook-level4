package seedu.address.testutil;

import seedu.smartscheduler.model.tag.UniqueTagList;
import seedu.smartscheduler.model.task.*;

/**
 * A mutable person object. For testing only.
 */
public class TestPerson implements ReadOnlyTask {

    private Name name;
    private int uniqueID;
    private EndTime endTime;
    private StartTime startTime;
    private UniqueTagList tags;

    public TestPerson() {
        tags = new UniqueTagList();
    }

    public void setName(Name name) {
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
    public Name getName() {
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
        sb.append("p/" + this.getPhone().value + " ");
        sb.append("e/" + this.getEmail().value + " ");
        sb.append("a/" + this.getUniqueID() + " ");
        this.getTags().getInternalList().stream().forEach(s -> sb.append("t/" + s.tagName + " "));
        return sb.toString();
    }
}
