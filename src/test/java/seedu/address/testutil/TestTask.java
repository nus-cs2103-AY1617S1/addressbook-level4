package seedu.address.testutil;

import seedu.address.model.tag.UniqueTagList;
import seedu.address.model.task.*;

/**
 * A mutable person object. For testing only.
 */
public class TestTask implements ReadOnlyTask {

    private Description description;
    private Priority priority;
    private Time timeStart;
    private Time timeEnd;
    private UniqueTagList tags;

    public TestTask() {
        tags = new UniqueTagList();
    }

    public void setDescription(Description description) {
        this.description = description;
    }

    public void setPriority(Priority priority) {
        this.priority = priority;
    }

    public void setTimeStart(Time time) {
        this.timeStart = time;
    }

    public void setTimeEnd(Time time) {
        this.timeEnd = time;
    }

    @Override
    public Description getDescription() {
        return description;
    }

    @Override
    public Time getTimeStart() {
        return timeStart;
    }

    @Override
    public Time getTimeEnd() {
        return timeEnd;
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
        sb.append("start/" + this.getTimeStart().toString() + " ");
        sb.append("end/" + this.getTimeEnd().toString() + " ");
        this.getTags().getInternalList().stream().forEach(s -> sb.append("t/" + s.tagName + " "));
        return sb.toString();
    }
}
