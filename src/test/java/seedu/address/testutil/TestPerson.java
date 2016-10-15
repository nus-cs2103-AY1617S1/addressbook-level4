package seedu.address.testutil;

import seedu.address.model.tag.UniqueTagList;
import seedu.address.model.person.*;

/**
 * A mutable person object. For testing only.
 */
public class TestPerson implements ReadOnlyTask {

    private TaskName name;
    private Address address;
    private DueTime email;
    private DateTime phone;
    private UniqueTagList tags;

    public TestPerson() {
        tags = new UniqueTagList();
    }

    public void setName(TaskName name) {
        this.name = name;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public void setEmail(DueTime email) {
        this.email = email;
    }

    public void setPhone(DateTime phone) {
        this.phone = phone;
    }

    @Override
    public TaskName getName() {
        return name;
    }

    @Override
    public DateTime getDueDate() {
        return phone;
    }

    @Override
    public DueTime getDueTime() {
        return email;
    }

    @Override
    public Address getAddress() {
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
        sb.append("e/" + this.getDueTime().value + " ");
        sb.append("a/" + this.getAddress().value + " ");
        this.getTags().getInternalList().stream().forEach(s -> sb.append("t/" + s.tagName + " "));
        return sb.toString();
    }
}
