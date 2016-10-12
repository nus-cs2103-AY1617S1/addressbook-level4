package seedu.address.testutil;

import seedu.address.model.tag.UniqueTagList;
import seedu.address.model.person.*;

/**
 * A mutable person object. For testing only.
 */
public class TestPerson implements ReadOnlyTask {

    private Description description;
    private Priority priority;
    private Venue venue;
    private Time time;
    private UniqueTagList tags;

    public TestPerson() {
        tags = new UniqueTagList();
    }

    public void setDescription(Description description) {
        this.description = description;
    }

    public void setPriority(Priority priority) {
        this.priority = priority;
    }

    public void setVenue(Venue venue) {
        this.venue = venue;
    }

    public void setTime(Time time) {
        this.time = time;
    }

    @Override
    public Description getDescription() {
        return description;
    }

    @Override
    public Time getTime() {
        return time;
    }

    @Override
    public Venue getVenue() {
        return venue;
    }

    @Override
    public Priority getPriority() {
        return priority;
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
        sb.append("add " + this.getDescription().fullDescription + " ");
        sb.append("pr/" + this.getPriority().toString() + " ");
        sb.append("time/" + this.getTime().toString() + " ");
        sb.append("a/" + this.getVenue().toString() + " ");
        this.getTags().getInternalList().stream().forEach(s -> sb.append("t/" + s.tagName + " "));
        return sb.toString();
    }
}
