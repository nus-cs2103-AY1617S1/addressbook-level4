package seedu.address.testutil;

import java.util.Date;

import seedu.address.commons.util.DateFormatter;
import seedu.address.model.tag.UniqueTagList;
import seedu.address.model.task.*;

/**
 * A mutable person object. For testing only.
 */
public class TestTask implements ReadOnlyTask {

    private Name name;
    private Location address;
    private Date startDate;
    private Date endDate;
    private UniqueTagList tags;

    public TestTask() {
        tags = new UniqueTagList();
    }

    public void setName(Name name) {
        this.name = name;
    }

    public void setAddress(Location address) {
        this.address = address;
    }

    public void setStartDate(Date date) {
        this.startDate = date;
    }
    public void setEndDate(Date date) {
        this.endDate = date;
    }


    @Override
    public Name getName() {
        return name;
    }

    @Override
    public Date getStartDate() {
        return startDate;
    }

    @Override
    public Date getEndDate() {
        return endDate;
    }

    @Override
    public Location getLocation() {
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
        sb.append("s/" + DateFormatter.convertDateToString(this.getStartDate()) + " ");
        sb.append("e/" + DateFormatter.convertDateToString(this.getEndDate()) + " ");
        sb.append("a/" + this.getLocation().value);
//        this.getTags().getInternalList().stream().forEach(s -> sb.append("t/" + s.tagName + " "));
        return sb.toString();
    }
    
    public String getTaskString() {
        StringBuilder sb = new StringBuilder();
        sb.append(" " + this.getName().fullName + " ");
        sb.append("s/" + DateFormatter.convertDateToString(this.getStartDate()) + " ");
        sb.append("e/" + DateFormatter.convertDateToString(this.getEndDate()) + " ");
        sb.append("a/" + this.getLocation().value);
//        this.getTags().getInternalList().stream().forEach(s -> sb.append("t/" + s.tagName + " "));
        return sb.toString();
    }
}
