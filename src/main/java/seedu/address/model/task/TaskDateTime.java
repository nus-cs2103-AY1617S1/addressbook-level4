package seedu.address.model.task;

import java.util.Date;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.DateFormatter;

public class TaskDateTime {

    private Date date;
    private Date time;
    
    
    public TaskDateTime() {
        
    }
    
    public TaskDateTime(String args) throws IllegalValueException {
        String[] split = args.split(" ");
        if (split.length > 0) {
            DateFormatter.convertStringToDate(split[0]);
        }
        if (split.length > 1) {
            DateFormatter.convertStringToTime(split[1]);
        }
    }
    
    public String getDateString() {
        if (date == null)
            return "";
        else 
            return DateFormatter.convertDateToString(date);
    }
    
    public String getDisplayDateString() {
        if (date == null)
            return "";
        else 
            return DateFormatter.convertDateToDisplayString(date);
    }
    
    public String getTimeString() {
        if (time == null)
            return "";
        else 
            return DateFormatter.convertTimeToString(date);
    }
    
    public String getDisplayTimeString() {
        if (time == null)
            return "";
        else 
            return DateFormatter.convertTimeToDisplayString(date);
    }
}
