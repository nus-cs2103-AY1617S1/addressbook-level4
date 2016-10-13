package seedu.address.model.task;


import seedu.address.commons.exceptions.IllegalValueException;

/**
 * Represents a task's end time in Simply
 * Guarantees: immutable; is valid as declared in {@link #isValidEnd(String)}
 */
public class End {
    
    public static final String MESSAGE_END_CONSTRAINTS = "Task start time should be 4 numbers keyed in together in 24 hour format.";
    public static final String END_VALIDATION_REGEX = "\\d{4}";

    public final String value;

    /**
     * Validates given end time.
     *
     * @throws IllegalValueException if given address string is invalid.
     */
    public End(String end) throws IllegalValueException {
        assert end != null;
        if (!isValidEnd(end)) {
            throw new IllegalValueException(MESSAGE_END_CONSTRAINTS);
        }
        this.value = end;
    }

    /**
     * Returns true if a given string is a valid person email.
     */
    public static boolean isValidEnd(String test) {
    	if (test == "" || test.matches(END_VALIDATION_REGEX))
    		return true;
    	else
    		return false;
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof End // instanceof handles nulls
                && this.value.equals(((End) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}