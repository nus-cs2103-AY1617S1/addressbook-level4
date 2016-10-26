//@@author A0139164A
package seedu.menion.model.activity;


import seedu.menion.commons.exceptions.IllegalValueException;
import seedu.menion.commons.util.DateChecker;

/**
 * Represents a Person's phone number in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidReminder(String)}
 */
public class ActivityTime {

    public static final String ACTIVITY_TIME_CONSTRAINTS = "Activity time should be in 24-hour format";
    public static final String ACTIVITY_TIME_VALIDATION_REGEX = "^[0-2][0-9][0-6][0-9]$";
    
    public final String value;
    /**
     * Validates given time date.
     *
     * @throws IllegalValueException if given time date string is invalid.
     */
    public ActivityTime(String time) throws IllegalValueException {
        assert time != null;
        time = time.trim();
        isValidTime(time);
        this.value = time;
    }

    /**
     * Returns if a given string is a valid activity time.
     */
    public static void isValidTime(String test) throws IllegalValueException  {
        
        DateChecker timeCheck = new DateChecker();
        
        // Checks that time has no illegal arguments
        if (!test.matches(ACTIVITY_TIME_VALIDATION_REGEX)) {
            throw new IllegalValueException(ACTIVITY_TIME_CONSTRAINTS);
        }
        // Checks time is between 0000 and 2400
        timeCheck.validTime(test);
        
        return;
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ActivityTime // instanceof handles nulls
                && this.value.equals(((ActivityTime) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
