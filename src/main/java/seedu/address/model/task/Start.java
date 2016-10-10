package seedu.address.model.task;


import seedu.address.commons.exceptions.IllegalValueException;

/**
 * Represents a task's start time in Simply.
 * Guarantees: immutable; is valid as declared in {@link #isValidStart(String)}
 */
public class Start {

    public static final String MESSAGE_START_CONSTRAINTS =
            "Task start time should be 4 numbers keyed in together in 24 hour format.";
    public static final String START_VALIDATION_REGEX = "\\d{4}";

    public final String value;

    /**
     * Validates given start time.
     *
     * @throws IllegalValueException if given start time string is invalid.
     */
    public Start(String start) throws IllegalValueException {
        assert start != null;
        start = start.trim();
        if (!isValidStart(start)) {
            throw new IllegalValueException(MESSAGE_START_CONSTRAINTS);
        }
        this.value = start;
    }

    /**
     * Returns if a given string is a valid person email.
     */
    public static boolean isValidStart(String test) {
    	if (test == "" || test.matches(START_VALIDATION_REGEX))
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
                || (other instanceof Start // instanceof handles nulls
                && this.value.equals(((Start) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
