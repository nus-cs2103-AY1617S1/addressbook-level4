package seedu.address.testutil;

import seedu.emeraldo.model.tag.UniqueTagList;
import seedu.emeraldo.model.task.*;

/**
 * A mutable task object. For testing only.
 */
public class TestTask implements ReadOnlyTask {

    private Description description;
    private DateTime dateTime;
    private UniqueTagList tags;

    public TestTask() {
        tags = new UniqueTagList();
    }

    public void setDescription(Description description) {
        this.description = description;
    }

    public void setDateTime(DateTime dateTime) {
        this.dateTime = dateTime;
    }

    @Override
    public Description getDescription() {
        return description;
    }


    @Override
    public DateTime getDateTime() {
        return dateTime;
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
        sb.append("add \"" + this.getDescription().fullDescription + "\" ");
        sb.append(this.getDateTime().value + " ");
        this.getTags().getInternalList().stream().forEach(s -> sb.append("#" + s.tagName + " "));
        return sb.toString();
    }
}
