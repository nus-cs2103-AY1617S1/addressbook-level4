package seedu.task.model.item;

import seedu.task.commons.exceptions.IllegalValueException;


/**
 * Represents an event's duration in the task book.
 * Guarantees: immutable; is valid as declared in {@link #isValidDuration(String)}
 */
public class Duration {

    public static final String MESSAGE_DURATION_CONSTRAINTS = "event duration should be in a date format of DD-MM-YY DD-MM-YY";
    public static final String DURATION_VALIDATION_REGEX = "([0-9]{2})[-]([0-9]{2})[-]([0-9]{2})\\s?(([0-9]{2})[-]([0-9]{2})[-]([0-9]{2}))?"; 

    public final String value;

    /**
     * Validates given duration.
     *
     * @throws IllegalValueException if given duration string is invalid.
     */
    public Duration(String duration) throws IllegalValueException {
        assert duration != null;
        duration = duration.trim();
        if (!isValidDuration(duration)) {
            throw new IllegalValueException(MESSAGE_DURATION_CONSTRAINTS);
        }
        this.value = duration;
    }

    /**
     * Returns true if a given string is a valid event duration.
     */
    public static boolean isValidDuration(String test) {
        return test.matches(DURATION_VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Duration // instanceof handles nulls
                && this.value.equals(((Duration) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
