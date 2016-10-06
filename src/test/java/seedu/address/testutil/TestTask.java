package seedu.address.testutil;

import seedu.address.model.tag.UniqueTagList;
import seedu.address.model.task.*;

/**
 * A mutable task object. For testing only.
 */
public class TestTask implements ReadOnlyTask {

    private Name name;
    private Date date;
    private EndDateTime endDateTime;
    private StartDateTime startDateTime;
    private UniqueTagList tags;

    public TestTask() {
        tags = new UniqueTagList();
    }

    public void setName(Name name) {
        this.name = name;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setEndDateTime(EndDateTime endDateTime) {
        this.endDateTime = endDateTime;
    }

    public void setStartDateTime(StartDateTime startDateTime) {
        this.startDateTime = startDateTime;
    }

    @Override
    public Name getName() {
        return name;
    }

    @Override
    public StartDateTime getStartDateTime() {
        return startDateTime;
    }

    @Override
    public EndDateTime getEndDateTime() {
        return endDateTime;
    }

    @Override
    public Date getDate() {
        return date;
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
        sb.append("p/" + this.getStartDateTime().value + " ");
        sb.append("e/" + this.getEndDateTime().value + " ");
        sb.append("a/" + this.getDate().value + " ");
        this.getTags().getInternalList().stream().forEach(s -> sb.append("t/" + s.tagName + " "));
        return sb.toString();
    }
}
