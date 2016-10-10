package seedu.task.testutil;

import seedu.todolist.model.task.*;

/**
 * A mutable task object. For testing only.
 */
public class TestTask implements ReadOnlyTask {

    private Name name;
    private LocationParameter locationParameter;
    private RemarksParameter remarksParameter;

    public void setName(Name name) {
        this.name = name;
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
        sb.append("at " + this.getLocationParameter() + " ");
        sb.append("remarks " + this.getRemarksParameter());
        return sb.toString();
    }
}
