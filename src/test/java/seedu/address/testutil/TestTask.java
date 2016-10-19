package seedu.address.testutil;

import seedu.address.model.tag.UniqueTagList;
import seedu.address.model.task.*;

/**
 * A mutable person object. For testing only.
 */
public class TestTask implements ReadOnlyTask {

    private Name name;
    private Location address;
    private TaskDateTime startDate;
    private TaskDateTime endDate;
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

    public void setStartDate(TaskDateTime date) {
        this.startDate = date;
    }
    public void setEndDate(TaskDateTime date) {
        this.endDate = date;
    }


    @Override
    public Name getName() {
        return name;
    }

    @Override
    public TaskDateTime getStartDate() {
        return startDate;
    }

    @Override
    public TaskDateTime getEndDate() {
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
        sb.append("s/" + this.getStartDate() + " ");
        sb.append("e/" + this.getEndDate() + " ");
        sb.append("at" + " " + this.getLocation().value);
//        this.getTags().getInternalList().stream().forEach(s -> sb.append("t/" + s.tagName + " "));
        return sb.toString();
    }
    
    public String getTaskString() {
        if (this.getEndDate() == null) {
            return getFloatingString();
        } else if (this.getStartDate() == null) {
            return getDeadlineString();
        } else {
            return getEventString();
        }
    }
    
    private String getFloatingString() {
        return " " + this.getName().fullName;
        
    }
    
    private String getDeadlineString() {
        StringBuilder sb = new StringBuilder();
        sb.append(" " + this.getName().fullName + " ");
        sb.append("by " + this.getEndDate());       
        return sb.toString();
    }

    private String getEventString() {
        StringBuilder sb = new StringBuilder();
        sb.append(" " + this.getName().fullName + " ");
        sb.append("s/" + this.getStartDate() + " ");
        sb.append("e/" + this.getEndDate() + " ");
        sb.append("at" + " " + this.getLocation().value);
//        this.getTags().getInternalList().stream().forEach(s -> sb.append("t/" + s.tagName + " "));
        return sb.toString();
    }
}
