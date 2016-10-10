package seedu.address.testutil;

import seedu.address.model.tag.UniqueTagList;
import seedu.address.model.task.*;

/**
 * A mutable person object. For testing only.
 */
public class TestPerson implements ReadOnlyTask {

    private TaskName name;
    private Reminder address;
    private Priority email;
    private DueDate phone;
    private UniqueTagList tags;

    public TestPerson() {
        tags = new UniqueTagList();
    }

    public void setName(TaskName name) {
        this.name = name;
    }

    public void setAddress(Reminder address) {
        this.address = address;
    }

    public void setEmail(Priority email) {
        this.email = email;
    }

    public void setPhone(DueDate phone) {
        this.phone = phone;
    }

    @Override
    public TaskName getName() {
        return name;
    }

    @Override
    public DueDate getDueDate() {
        return phone;
    }

    @Override
    public Priority getPriority() {
        return email;
    }

    @Override
    public Reminder getReminder() {
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
        sb.append("p/" + this.getDueDate().value + " ");
        sb.append("e/" + this.getPriority().value + " ");
        sb.append("a/" + this.getReminder().value + " ");
        this.getTags().getInternalList().stream().forEach(s -> sb.append("t/" + s.tagName + " "));
        return sb.toString();
    }
}
