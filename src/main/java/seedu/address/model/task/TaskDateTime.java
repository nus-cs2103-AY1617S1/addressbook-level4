package seedu.address.model.task;

import java.util.Date;

import seedu.address.commons.core.Messages;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.DateFormatter;

public class TaskDateTime {

    private Date date;
    private Date time;
    
    
    public TaskDateTime() {
        date = null;
        time = null;
    }
    
    public TaskDateTime(String args) throws IllegalValueException {
        if (args == null || args.trim().isEmpty())
            return;
        
        date = DateFormatter.convertStringToDate(args);
        if (!DateFormatter.convertDateToFullTimeString(date)
                .equals(DateFormatter.convertDateToFullTimeString(new Date()))) {
            time = date;
        }
//        if (split.length > 1 && !split[1].trim().isEmpty()) {
//            time = DateFormatter.convertStringToTime(split[1].trim());
//        }
    }
    
    public Date getDate() { 
        return date;
    }
    
    public Date getTime() {
        return time;
    }
    
    public String getDateString() {
//        if (date == null)
//            return "";
//        else 
//            return DateFormatter.convertDateToString(date);
        return getDisplayDateString();
    }
    
    public String getDisplayDateString() {
        if (date == null)
            return "";
        else 
            return DateFormatter.convertDateToDisplayString(date);
    }
    
    public String getTimeString() {
//        if (time == null)
//            return "";
//        else 
//            return " " + DateFormatter.convertTimeToString(date);
        return getDisplayTimeString();
    }
    
    public String getDisplayTimeString() {
        if (time == null)
            return "";
        else 
            return " " + DateFormatter.convertTimeToDisplayString(date);
    }
    
    public String getDisplayString() {
        return getDisplayDateString() + getDisplayTimeString();
    }
    
    public String toString() {
        return getDateString() + getTimeString();
    }
    
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof TaskDateTime // instanceof handles nulls
                && isSameStateAs((TaskDateTime) other));
    }
    
    public boolean isSameStateAs(TaskDateTime other) {
        return getDisplayString().equals(other.getDisplayString());
//                (getDateString().equals(other.getDateString()) 
//                && getTimeString().equals(other.getTimeString()));
    }
}
