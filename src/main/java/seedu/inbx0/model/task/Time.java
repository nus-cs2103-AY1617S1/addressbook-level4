package seedu.inbx0.model.task;

import seedu.inbx0.commons.exceptions.IllegalValueException;
import java.text.SimpleDateFormat;
import java.util.List;

import com.joestelmach.natty.Parser;

/**
 * Represents Time of a Task Event in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidTime(String)}
 */
public class Time {

    public static final String MESSAGE_TIME_CONSTRAINTS = "Time can either be a numeric string, alphanumeric string, or alphabet string \n"
                                                        + "Example: HH:MM format OR 3a (3am) OR 3 hours later";
    public static final String TIME_VALIDATION_REGEX = "\\d+";
    
    public final String value;
    
    /**
     * Validates given time.
     *
     * @throws IllegalValueException if given start time string is invalid.
     * !isValidTime(time) &&
     */
    
    public Time(String time) throws IllegalValueException {
        assert time != null;
     //   time = time.trim();
       
        if ( time == "" | time.length() == 0 | time == null) {
            this.value = "";
        }
        else {
                try{
                    List<java.util.Date> getTime = new Parser().parse(time).get(0).getDates();
                    SimpleDateFormat ft = new SimpleDateFormat ("HH:mm");
                    this.value = ft.format(getTime.get(0)); 
                } catch (IndexOutOfBoundsException e) {
                    throw new IllegalValueException(MESSAGE_TIME_CONSTRAINTS);
                }
        }
       
    }
    
    /**
     * Returns true if a given string is a valid task start time.
     */
    public static boolean isValidTime(String test) {
        boolean hourCheck = false;
        boolean minCheck = false;
        
        if(test.matches(TIME_VALIDATION_REGEX) && (test.length() == 4)) {
            if((Integer.parseInt(test) / 100) < 24) {
                hourCheck = true;
            }
            
            if((Integer.parseInt(test) % 100) < 60) {
                minCheck = true;
            }
        }
       
        return (hourCheck && minCheck);
    }
    
    @Override
    public String toString() {
        return value;
    }
    

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Time // instanceof handles nulls
                && this.value.equals(((Time) other).value)); // state check
    }
    
    @Override
    public int hashCode() {
        return value.hashCode();
    }

    public String getTime() {
        return value;
    }
}
