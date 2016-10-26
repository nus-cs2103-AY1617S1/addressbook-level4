package seedu.todoList.model.task.attributes;

import seedu.todoList.commons.exceptions.IllegalValueException;

/**
 * Represents a Event's or Deadline's end time in the TodoList.
 * Guarantees: immutable; is valid as declared in {@link #isValidEndTime(String)}
 */
public class EndTime {

    public static final String MESSAGE_ENDTIME_CONSTRAINTS =
            "Event or Deadline End Time should be written in this format, must be 4 digits '10:00' and within 24 hrs format (0000 to 2359)";
    public static final String ENDTIME_VALIDATION_REGEX = "^(\\d{2}:\\d{2})$";
    
    public final String endTime;
    public final String saveEndTime;
    /**
     * Validates given end time.
     *
     * @throws IllegalValueException if given end time is invalid.
     */
    public EndTime(String endTime) throws IllegalValueException {
        assert endTime != null;
        endTime = endTime.trim();
        saveEndTime = endTime.trim();
        if (!isValidEndTime(endTime)) {
            throw new IllegalValueException(MESSAGE_ENDTIME_CONSTRAINTS);
        }
      //Checking time in 24-Hr format
        String[] etimeArr = endTime.split(":");
        String hour = "";
        switch(etimeArr[0]){
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
            default: hour = etimeArr[0] + ":";
        }
        if(Integer.parseInt(etimeArr[0]) > 11){
            endTime = hour + etimeArr[1] + "pm";
            this.endTime = endTime;
        }else{
            endTime = hour + etimeArr[1] + "am";
            this.endTime = endTime;
        }
    }
    
    /**
     * Returns if a given string is a valid event or deadline end time.
     */
    public static boolean isValidEndTime(String endtime) {
        String[] etimeArr = endtime.split(":");
        boolean checkTime = true;
        
        if(Integer.parseInt(etimeArr[0]) > 23 || Integer.parseInt(etimeArr[1]) > 59){
            checkTime = false;
        }
        
        if(endtime.matches(ENDTIME_VALIDATION_REGEX) && checkTime){
            return true;
        }else{
            return false;
        }
    }

    @Override
    public String toString() {
        return endTime;
    }
    
    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof EndTime // instanceof handles nulls
                && this.endTime.equals(((EndTime) other).endTime)); // state check
    }

    @Override
    public int hashCode() {
        return endTime.hashCode();
    }

}
