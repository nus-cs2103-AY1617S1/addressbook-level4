package seedu.address.model.person;

import seedu.address.commons.exceptions.IllegalValueException;

/**
 * Represents Time of a Task Event in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidTime(String)}
 */
public class Time {

    public static final String MESSAGE_TIME_CONSTRAINTS = "Start Time has to be in 24 hour format";
    public static final String TIME_VALIDATION_REGEX = "\\d+";
    
    public final String value;
    
    /**
     * Validates given time.
     *
     * @throws IllegalValueException if given start time string is invalid.
     */
    
    public Time(String time) throws IllegalValueException {
        assert time != null;
        time = time.trim();
        if (!isValidTime(time) && (time != "")) {
            throw new IllegalValueException(MESSAGE_TIME_CONSTRAINTS);
        }
        this.value = time;
    }
    
    /**
     * Returns true if a given string is a valid task start time.
     */
    public static boolean isValidTime(String test) {
        boolean hourCheck = false;
        boolean minCheck = false;
        
        if(test.matches(TIME_VALIDATION_REGEX)) {
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
}
