package seedu.taskscheduler.model.task;

import java.util.Date;

import seedu.taskscheduler.commons.exceptions.IllegalValueException;
import seedu.taskscheduler.commons.util.DateFormatter;

//@@author A0148145E

/**
 * Represents the Task date and time model.
 */
public class TaskDateTime {

    private Date date;
    private boolean enShowTime = false;
    
    public TaskDateTime() {
        date = null;
    }
    
    public TaskDateTime(TaskDateTime source) {
        this.date = source.getDate();
        this.enShowTime = source.enShowTime;
    }
    
    public TaskDateTime(String args) throws IllegalValueException {
        if (args == null || args.trim().isEmpty()) {
            return;
        }
        
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
            enShowTime = true;
        } else {
            setTimeToMax(date);
        }
    }
    
    public Date getDate() { 
        return date;
    }
    
    public boolean isBefore(TaskDateTime other) { 
        //as null represents infinity
        if (date == null) {
            return false;
        } else if (other == null || other.getDate() == null) {
            return true;
        } else {
            return getDate().before(other.getDate());
        }
    }
    
    public boolean isAfter(TaskDateTime other) { 
        //as null represents infinity
        if (date == null) {
            return true;
        } else if (other == null || other.getDate() == null) {
            return false;
        } else {
            return getDate().after(other.getDate());
        }
    }
    
    public String getDisplayDateString() {
        if (date == null) {
            return "";
        }
        else {
            return DateFormatter.convertDateToDisplayString(date);
        }
    }
    
    public String getDisplayTimeString() {
        if (enShowTime) {
            return " " + DateFormatter.convertTimeToDisplayString(date);
        }
        else {
            return "";
        }
    }
    
    public boolean getEnShowTime() {
        return enShowTime;
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
    
    @SuppressWarnings("deprecation")
    private void setTimeToMax(Date date) {
        date.setHours(23);
        date.setMinutes(59);
        date.setSeconds(59);
    }
}
