package tars.testutil;

import tars.model.task.*;
import tars.model.tag.UniqueTagList;

/**
 * A mutable task object. For testing only.
 */
public class TestTask implements ReadOnlyTask {

    private Name name;
    private UniqueTagList tags;
    private DateTime dateTime;
    private Priority priority;
    private Status status =  new Status();

    public TestTask() {
        tags = new UniqueTagList();
    }

    public void setName(Name name) {
        this.name = name;
    }
    
    public void setDateTime(DateTime dateTime) {
        this.dateTime = dateTime;
    }
    
   
    public void setPriority(Priority priority) {
        this.priority = priority;
    }

    @Override
    public Name getName() {
        return name;
    }
   
    @Override
    public DateTime getDateTime() {
        return dateTime;
    }

    @Override
    public Status getStatus() {
        return status;
    }

    @Override
    public Priority getPriority() {
        return priority;
    }

    @Override
    public UniqueTagList getTags() {
        return tags;
    }

    public void setStatus(Status status) {
    	this.status = status;
    }
    
    @Override
    public String toString() {
        return getAsText();
    } 

    public String getAddCommand() {
        StringBuilder sb = new StringBuilder();
        sb.append("add " + this.getName().taskName + " ");
        sb.append("/dt " + this.getDateTime().toString() + " ");
        sb.append("/p " + this.getPriority().toString() + " ");
        this.getTags().getInternalList().stream().forEach(s -> sb.append("/t " + s.tagName + " "));
        return sb.toString();
    }
}
