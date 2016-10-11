package seedu.task.testutil;

import seedu.task.model.person.*;
import seedu.task.model.tag.UniqueTagList;

/**
 * A mutable person object. For testing only.
 */
public class TestPerson implements ReadOnlyTask {

    private Name name;
    private Location address;
    private EndTime email;
    private StartTime phone;
    private UniqueTagList tags;

    public TestPerson() {
        tags = new UniqueTagList();
    }

    public void setName(Name name) {
        this.name = name;
    }

    public void setAddress(Location address) {
        this.address = address;
    }

    public void setEmail(EndTime email) {
        this.email = email;
    }

    public void setPhone(StartTime phone) {
        this.phone = phone;
    }

    @Override
    public Name getName() {
        return name;
    }

    @Override
    public StartTime getStartTime() {
        return phone;
    }

    @Override
    public EndTime getEndTime() {
        return email;
    }

    @Override
    public Location getLocation() {
        return address;
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
        sb.append("p/" + this.getStartTime().value + " ");
        sb.append("e/" + this.getEndTime().value + " ");
        sb.append("a/" + this.getLocation().value + " ");
        this.getTags().getInternalList().stream().forEach(s -> sb.append("t/" + s.tagName + " "));
        return sb.toString();
    }
}
