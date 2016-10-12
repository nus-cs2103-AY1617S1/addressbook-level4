package seedu.address.model.person;


import seedu.address.commons.exceptions.IllegalValueException;

/**
 * Represents a Task's Time in the to-do-list.
 * Guarantees: immutable; is valid as declared in {@link #isValidAddress(String)}
 */
public class Time {
    
    public static final String MESSAGE_TIME_CONSTRAINTS = 
            "Time should be in 24hr format. Eg. 2359";
    public static final String TIME_VALIDATION_REGEX = "[0-2][0-3][0-5][0-9]";

    public final String value;

    /**
     * Validates given time.
     *
     * @throws IllegalValueException if given time string is invalid.
     */
    public Time(String time) throws IllegalValueException {
        if (time == null){
            this.value = "";
            return;
        }
        if (!isValidAddress(time)) {
            throw new IllegalValueException(MESSAGE_TIME_CONSTRAINTS);
        }
        this.value = time;
    }

    /**
     * Returns true if a given string is a valid time
     */
    public static boolean isValidAddress(String test) {
        return test.equals("") || test.matches(TIME_VALIDATION_REGEX);
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

}