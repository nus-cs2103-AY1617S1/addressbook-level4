package seedu.address.testutil;

import seedu.address.model.person.Datetime;
import seedu.address.model.person.Description;
import seedu.address.model.person.Name;
import seedu.address.model.person.ReadOnlyTask;
import seedu.address.model.person.Status;
import seedu.address.model.tag.UniqueTagList;

/**
 * A mutable person object. For testing only.
 */
public class TestTask implements ReadOnlyTask {

    private Name name;
    private Datetime datetime;
    private Description description;
    private Status status;
    private UniqueTagList tags;

    public TestTask() {
        tags = new UniqueTagList();
    }

    public void setName(Name name) {
        this.name = name;
    }
    
    public void setDatetime(Datetime datetime) {
        this.datetime = datetime;
    }

    public void setDescription(Description description) {
        this.description = description;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
    @Override
    public Name getName() {
        return name;
    }

    @Override
    public Description getDescription() {
        return description;
    }

    @Override
    public Datetime getDatetime() {
        return datetime;
    }

    @Override
    public Status getStatus() {
        return status;
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
        sb.append("d/" + this.getDescription().value + " ");
        sb.append("date/" + this.getDatetime().getTimeString() + " ");
        sb.append(" " + this.getDatetime().getTimeString() + " ");
        this.getTags().getInternalList().stream().forEach(s -> sb.append("t/" + s.tagName + " "));
        return sb.toString();
    }
}
