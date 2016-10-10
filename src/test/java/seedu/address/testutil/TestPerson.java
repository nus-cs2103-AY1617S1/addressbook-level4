package seedu.address.testutil;

import seedu.menion.model.activity.*;
import seedu.menion.model.tag.UniqueTagList;

/**
 * A mutable person object. For testing only.
 */
public class TestPerson implements ReadOnlyActivity {

    private ActivityName name;
    private Note address;
    private ActivityTime email;
    private ActivityDate phone;
    private UniqueTagList tags;

    public TestPerson() {
        tags = new UniqueTagList();
    }

    public void setName(ActivityName name) {
        this.name = name;
    }

    public void setAddress(Note address) {
        this.address = address;
    }

    public void setEmail(ActivityTime email) {
        this.email = email;
    }

    public void setPhone(ActivityDate phone) {
        this.phone = phone;
    }

    @Override
    public ActivityName getName() {
        return name;
    }

    @Override
    public ActivityDate getDeadline() {
        return phone;
    }

    @Override
    public ActivityTime getReminder() {
        return email;
    }

    @Override
    public Note getPriority() {
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
