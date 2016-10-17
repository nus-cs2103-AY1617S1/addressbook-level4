package seedu.malitio.testutil;

import seedu.malitio.model.tag.UniqueTagList;
import seedu.malitio.model.task.*;

/**
 * A mutable task object. For testing only.
 */
public class TestTask implements ReadOnlyFloatingTask {

    private Name name;
    private DateTime due;
    private DateTime start;
    private DateTime end;
    private UniqueTagList tags;

    public TestTask() {
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
        sb.append("add " + this.getName().fullName + " ");
        this.getTags().getInternalList().stream().forEach(s -> sb.append("t/" + s.tagName + " "));
        return sb.toString();
    }


    public DateTime getDue() {
        return due;
    }


    public void setDue(DateTime due) {
        this.due = due;
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

}
