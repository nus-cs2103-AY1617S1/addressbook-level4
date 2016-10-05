package seedu.taskman.testutil;

import seedu.taskman.model.tag.UniqueTagList;
import seedu.taskman.model.task.*;

/**
 * A mutable task object. For testing only.
 */
public class TestTask implements ReadOnlyTask {

    private Title title;
    private Address address;
    private Email email;
    private Deadline deadline;
    private UniqueTagList tags;

    public TestTask() {
        tags = new UniqueTagList();
    }

    public void setName(Title title) {
        this.title = title;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public void setEmail(Email email) {
        this.email = email;
    }

    public void setDeadline(Deadline deadline) {
        this.deadline = deadline;
    }

    @Override
    public Title getTitle() {
        return title;
    }

    @Override
    public Deadline getDeadline() {
        return deadline;
    }

    @Override
    public Email getEmail() {
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
        sb.append("add " + this.getTitle().title + " ");
        sb.append("d/" + this.getDeadline().value + " ");
        sb.append("e/" + this.getEmail().value + " ");
        sb.append("a/" + this.getAddress().value + " ");
        this.getTags().getInternalList().stream().forEach(s -> sb.append("t/" + s.tagName + " "));
        return sb.toString();
    }
}
