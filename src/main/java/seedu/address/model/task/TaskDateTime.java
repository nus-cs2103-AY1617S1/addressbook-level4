package seedu.address.model.task;

import java.util.Date;

import seedu.address.commons.core.Messages;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.DateFormatter;

public class TaskDateTime {

    private Date date;
    private boolean showTime = false;
    
    public TaskDateTime() {
        date = null;
    }
    
    public TaskDateTime(TaskDateTime other) {
        this.date = (Date) other.getDate();
        this.showTime = other.showTime;
    }
    
    public TaskDateTime(String args) throws IllegalValueException {
        if (args == null || args.trim().isEmpty())
            return;
        
        date = DateFormatter.convertStringToDate(args);
        checkIfTimeIsSpecified(new Date());
    }

    public void setDate(long l) { 
        Date newDate = new Date(l);
        checkIfTimeIsSpecified(newDate);
        this.date = newDate; 
    }

    private void checkIfTimeIsSpecified(Date other) {
        if (!DateFormatter.convertDateToFullTimeString(date)
                .equals(DateFormatter.convertDateToFullTimeString(other))) {
            showTime = true;
        }
    }
    
    public Date getDate() { 
        return date;
    }
    
    public String getDisplayDateString() {
        if (date == null)
            return "";
        else 
            return DateFormatter.convertDateToDisplayString(date);
    }
    
    public String getDisplayTimeString() {
        if (showTime)
            return " " + DateFormatter.convertTimeToDisplayString(date);
        else
            return "";
    }
    
    public boolean getShowTime() {
        return showTime;
    }
    
    public String getDisplayString() {
        return getDisplayDateString() + getDisplayTimeString();
    }
    
    public String toString() {
        return getDisplayString();
    }
    
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof TaskDateTime // instanceof handles nulls
                && isSameStateAs((TaskDateTime) other));
    }
    
    public boolean isSameStateAs(TaskDateTime other) {
        return getDisplayString().equals(other.getDisplayString());
    }
}
