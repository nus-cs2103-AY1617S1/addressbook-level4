package seedu.address.testutil;

import seedu.task.model.tag.UniqueTagList;
import seedu.task.model.task.*;

/**
 * A mutable person object. For testing only.
 */
public class TestPerson implements ReadOnlyTask {

    private TaskName taskName;
    private Venue address;
    private Time email;
    private Date phone;
    private UniqueTagList tags;

    public TestPerson() {
        tags = new UniqueTagList();
    }

    public void setName(TaskName taskName) {
        this.taskName = taskName;
    }

    public void setAddress(Venue address) {
        this.address = address;
    }

    public void setEmail(Time email) {
        this.email = email;
    }

    public void setPhone(Date phone) {
        this.phone = phone;
    }

    @Override
    public TaskName getName() {
        return taskName;
    }

    @Override
    public Date getDate() {
        return phone;
    }

    @Override
    public Time getTime() {
        return email;
    }

    @Override
    public Venue getVenue() {
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
        sb.append("p/" + this.getDate().value + " ");
        sb.append("e/" + this.getTime().value + " ");
        sb.append("a/" + this.getVenue().value + " ");
        this.getTags().getInternalList().stream().forEach(s -> sb.append("t/" + s.tagName + " "));
        return sb.toString();
    }
}
