package seedu.address.testutil;

import seedu.menion.model.activity.*;
import seedu.menion.model.tag.UniqueTagList;

/**
 * A mutable person object. For testing only.
 */
public class TestPerson implements ReadOnlyActivity {

    private Name name;
    private Priority address;
    private Reminder email;
    private Deadline phone;
    private UniqueTagList tags;

    public TestPerson() {
        tags = new UniqueTagList();
    }

    public void setName(Name name) {
        this.name = name;
    }

    public void setAddress(Priority address) {
        this.address = address;
    }

    public void setEmail(Reminder email) {
        this.email = email;
    }

    public void setPhone(Deadline phone) {
        this.phone = phone;
    }

    @Override
    public Name getName() {
        return name;
    }

    @Override
    public Deadline getDeadline() {
        return phone;
    }

    @Override
    public Reminder getReminder() {
        return email;
    }

    @Override
    public Priority getPriority() {
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
        sb.append("p/" + this.getDeadline().value + " ");
        sb.append("e/" + this.getReminder().value + " ");
        sb.append("a/" + this.getPriority().value + " ");
        this.getTags().getInternalList().stream().forEach(s -> sb.append("t/" + s.tagName + " "));
        return sb.toString();
    }
}
