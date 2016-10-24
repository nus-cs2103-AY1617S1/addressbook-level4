package seedu.todoList.model.task.attributes;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import seedu.todoList.commons.exceptions.IllegalValueException;
import seedu.todoList.model.task.Event;

/**
 * Represents a Event's start time in the TodoList.
 * Guarantees: immutable; is valid as declared in {@link #isValidStartTime(String)}
 */
public class StartTime {

    public static final String MESSAGE_STARTTIME_CONSTRAINTS =
            "Event Start time should be written in this format, must be 4 digits '10:00' and within 24 hrs format (0000 to 2359)";
    public static final String STARTTIME_VALIDATION_REGEX = "^(\\d{2}:\\d{2})$";
    
    public final String startTime;
    public final String saveStartTime;
    /**
     * Validates given start time.
     *
     * @throws IllegalValueException if given start time is invalid.
     */
    public StartTime(String startTime) throws IllegalValueException {
        assert startTime != null;
        startTime = startTime.trim();
        saveStartTime = startTime.trim();
        if (!isValidStartTime(startTime)) {
            throw new IllegalValueException(MESSAGE_STARTTIME_CONSTRAINTS);
        }
        
        //Checking time in 24-Hr format
        String[] stimeArr = startTime.split(":");
        String hour = "";
        switch(stimeArr[0]){
            case "00" : hour = "12:"; break;
            case "23" : hour = "11:"; break;
            case "22" : hour = "10:"; break;
            case "21" : hour = "09:"; break;
            case "20" : hour = "08:"; break;
            case "19" : hour = "07:"; break;
            case "18" : hour = "06:"; break;
            case "17" : hour = "05:"; break;
            case "16" : hour = "04:"; break;
            case "15" : hour = "03:"; break;
            case "14" : hour = "02:"; break;
            case "13" : hour = "01:"; break;
            default: hour = stimeArr[0] + ":";
        }
        if(Integer.parseInt(stimeArr[0]) > 11){
            startTime = hour + stimeArr[1] + "pm";
            this.startTime = startTime;
        }else{
            startTime = hour + stimeArr[1] + "am";
            this.startTime = startTime;
        }
    }
    
    /**
     * Returns if a given string is a valid event start time.
     */
    public static boolean isValidStartTime(String starttime) { 
        String[] stimeArr = starttime.split(":");       
        boolean checkTime = true;
        
        if(Integer.parseInt(stimeArr[0]) > 23 || Integer.parseInt(stimeArr[1]) > 59){
           checkTime = false;
        }
            
        if(starttime.matches(STARTTIME_VALIDATION_REGEX) && checkTime){
            return true;
        }else{
            return false;
        }
    }
  
    @Override
    public String toString() {
        return startTime;
    }
    
    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof StartTime // instanceof handles nulls
                && this.startTime.equals(((StartTime) other).startTime)); // state check
    }

    @Override
    public int hashCode() {
        return startTime.hashCode();
    }

}
