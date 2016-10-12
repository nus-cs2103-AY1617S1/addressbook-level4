package seedu.task.testutil;

import seedu.todolist.model.task.*;

/**
 * A mutable task object. For testing only.
 */
public class TestTask implements ReadOnlyTask {

    private Name name;
    private Interval interval;
    private LocationParameter locationParameter;
    private RemarksParameter remarksParameter;

    public void setName(Name name) {
        this.name = name;
    }
    
    public void setInterval(Interval interval) {
        this.interval = interval;
    }
    
    public void setLocationParameter(LocationParameter locationParameter) {
        this.locationParameter = locationParameter;
    }

    public void setRemarksParameter(RemarksParameter remarksParameter) {
    	this.remarksParameter = remarksParameter;
    }
    
    @Override
    public Name getName() {
        return name;
    }
    
    @Override
    public Interval getInterval() {
        return interval;
    }

    @Override
    public LocationParameter getLocationParameter() {
        return locationParameter;
    }
    
    @Override
    public RemarksParameter getRemarksParameter() {
    	return remarksParameter;
    }

    @Override
    public String toString() {
        return getAsText();
    }

    public String getAddCommand() {
        StringBuilder sb = new StringBuilder();
        sb.append("add " + this.getName().fullName + " ");
        sb.append("from " + this.getInterval().startDate + " " + this.getInterval().startTime 
                + " to " + this.getInterval().endDate + " " + this.getInterval().endTime);
        sb.append("at " + this.getLocationParameter() + " ");
        sb.append("remarks " + this.getRemarksParameter());
        return sb.toString();
    }
}
