//@@author A0138848M
package seedu.oneline.model.task;

import seedu.oneline.commons.exceptions.IllegalValueException;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.time.DateUtils;

import com.joestelmach.natty.*;

public class TaskTime implements Comparable<TaskTime> {

    public static final String MESSAGE_TASK_TIME_CONSTRAINTS =
            "Task time should have a valid date or time format";
    
    private final Date value;

    /**
     * Sets this.value to null if empty time string is given. 
     * Otherwise sets this.value to the date represented by time string.
     * 
     * An empty TaskTime is represented by a TaskTime whose 
     * .getDate method returns null
     *
     * @throws IllegalValueException if given time string is invalid.
     */
    public TaskTime(String time) throws IllegalValueException {
        assert time != null;
        time = time.trim();

        if (time.isEmpty()){
            // represent an empty tasktime with a null value field
            value = null;
        } else {        
            value = getDate(time);
        }
    }
    
    /**
     * Returns the date represented by input time string
     * 
     * @param time the user given time string
     * @return the date represented by the time string
     * @throws IllegalValueException if given time string is invalid
     * 
     */
    @SuppressWarnings("deprecation")
    private Date getDate(String time) throws IllegalValueException{
        Parser parser = new Parser(); // use the natty parser
        List<DateGroup> dates = parser.parse(time);

        if (!isValidTaskTime(dates)) {
            throw new IllegalValueException(MESSAGE_TASK_TIME_CONSTRAINTS);
        }
        
        Date date = dates.get(0).getDates().get(0);
        
        // if time was not explicitly declared, set the time to 2359
        if (dates.get(0).isTimeInferred()){
            date.setHours(23);
            date.setMinutes(59);
            date.setSeconds(59);
        }
        
        return date;
    }

    /**
     * Returns true if the time supplied is valid by checking the result of parser.parse
     * 
     * @param test the list of dategroups under test
     * @return true if list contains a valid date
     * 
     * Pre-condition: test is the return value of applying natty's Parser.parse(time) 
     * where time is the time in question
     */
    private static boolean isValidTaskTime(List<DateGroup> test) {
        return !(test.isEmpty() || test.get(0).getDates().isEmpty());
    }

    /**
     * Returns the default time value
     */
    public static TaskTime getDefault() {
        try {
            return new TaskTime("");
        } catch (IllegalValueException e) {
            assert false; // This function should return a correct value!
        }
        return null;
    }
    
    public Date getDate(){
        return value;
    }
    
    public Calendar getCalendar(){
        return DateUtils.toCalendar(value);
    }
    
    @Override
    public String toString() {
        return value == null ? "" : value.toString();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof TaskTime)) {
            return false;
        }
        TaskTime otherTime = (TaskTime) other;
        if (this.value == null || otherTime.value == null) {
            return this.value == otherTime.value;
        } else {
            return this.value.compareTo(otherTime.value) == 0;
        }
    }

    @Override
    public int hashCode() {
        return value == null ? 0 : value.hashCode();
    }
    
    /**
     * Serialize field for storage
     */
    public String serialize() {
        return value == null ? "" : value.toString();
    }
    
    /**
     * Deserialize from storage
     */
    public static TaskTime deserialize(String args) throws IllegalValueException {
        return new TaskTime(args);
    }

    /**
     * compare task time by the date it represents
     */
    @Override
    public int compareTo(TaskTime o) {
        if (this.value == null){
            return o.value == null ? 0 : -1;
        } else if (o.value == null){
            return 1;
        } else {
            return this.value.compareTo(o.value);
        }
    }
    
    /**
     * Returns true if task time is valid
     * 
     * @return true if value is null, false otherwise
     */
    public boolean isValid(){
        return value != null;
    }
}