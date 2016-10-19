package seedu.task.testutil;

import seedu.task.model.tag.UniqueTagList;
import seedu.task.model.task.*;

/**
 * A mutable task object. For testing only.
 */
public class TestTask implements ReadOnlyTask {

    private Name name;
    private DateTime openTime;
    private DateTime closeTime;
    private UniqueTagList tags;

    public TestTask() {
        tags = new UniqueTagList();
    }
    
    public void setName(Name name) {
        this.name = name;
    }

    public void setOpenTime(DateTime openTime) {
        this.openTime = openTime;
    }

    public void setCloseTime(DateTime closeTime) {
        this.closeTime = closeTime;
    }


    @Override
    public Name getName() {
        return name;
    }

    @Override
    public DateTime getOpenTime() {
        return openTime;
    }

    @Override
    public DateTime getCloseTime() {
        return closeTime;
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
        sb.append("add " + this.getName().taskName + " ");
        sb.append("s/" + this.getOpenTime().toPrettyString() + " ");
        sb.append("c/" + this.getCloseTime().toPrettyString() + " ");
        this.getTags().getInternalList().stream().forEach(s -> sb.append("t/" + s.tagName + " "));
        return sb.toString();
    }
    
    public String getArgs() {
        StringBuilder sb = new StringBuilder();
        sb.append(" "+this.getName().taskName + " ");
        sb.append("s/" + this.getOpenTime().toPrettyString() + " ");
        sb.append("c/" + this.getCloseTime().toPrettyString() + " ");
        this.getTags().getInternalList().stream().forEach(s -> sb.append("t/" + s.tagName + " "));
        return sb.toString();
    }
}
