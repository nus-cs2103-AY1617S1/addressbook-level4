package seedu.malitio.testutil;

import seedu.malitio.model.tag.UniqueTagList;
import seedu.malitio.model.task.*;

/**
 * A mutable event object. For testing only.
 */
public class TestEvent implements ReadOnlyEvent {

    private Name name;
    private DateTime start;
    private DateTime end;
    private UniqueTagList tags;

    public TestEvent() {
        tags = new UniqueTagList();
    }

    public void setName(Name name) {
        this.name = name;
    }

    @Override
    public Name getName() {
        return name;
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
        sb.append("add " + this.getName().fullName + " start " + this.getStart().toString() + " end " + this.getEnd().toString());
        this.getTags().getInternalList().stream().forEach(s -> sb.append("t/" + s.tagName + " "));
        return sb.toString();
    }

    public DateTime getStart() {
        return start;
    }

    public void setStart(DateTime start) {
        this.start = start;
    }

    public DateTime getEnd() {
        return end;
    }

    public void setEnd(DateTime end) {
        this.end = end;
    }

    @Override
    public String tagsString() {
        return ReadOnlyEvent.super.tagsString();
    }

    @Override
    public String getAsText() {
        return ReadOnlyEvent.super.getAsText();
    }

}
