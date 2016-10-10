package seedu.menion.model.activity;


import seedu.menion.commons.exceptions.IllegalValueException;

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
        if (!isValidTime(time)) {
            throw new IllegalValueException(ACTIVITY_TIME_CONSTRAINTS);
        }
        this.value = time;
    }

    /**
     * Returns if a given string is a valid activity time.
     */
    public static boolean isValidTime(String test) {
        boolean result = false;
        
        if (test.matches(ACTIVITY_TIME_VALIDATION_REGEX)) {
            result = true;
        }
        return result;
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
