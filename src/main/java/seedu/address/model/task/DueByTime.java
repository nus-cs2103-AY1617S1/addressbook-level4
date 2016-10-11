package seedu.address.model.task;


import seedu.address.commons.exceptions.IllegalValueException;

/**
 * Represents a Task's due time in the To Do List.
 * Guarantees: immutable; is valid as declared in {@link #isValidDueByTime(String)}
 */
public class DueByTime {

    public static final String MESSAGE_DUEBYTIME_CONSTRAINTS = "";
    public static final String DUEBYTIME_VALIDATION_REGEX = "";

    public final String value;

    /**
     * Validates given dueByTime.
     *
     * @throws IllegalValueException if given dueByTime string is invalid.
     */
    public DueByTime(String dueByTime) throws IllegalValueException {
        assert dueByTime != null;
        dueByTime = dueByTime.trim();
        if (!isValidDueByTime(dueByTime)) {
            throw new IllegalValueException(MESSAGE_DUEBYTIME_CONSTRAINTS);
        }
        this.value = dueByTime;
    }

    /**
     * Returns if a given string is a valid due time.
     */
    public static boolean isValidDueByTime(String test) {
        return test.matches(DUEBYTIME_VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }
    /*
    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DueTime // instanceof handles nulls
                && this.value.equals(((DueTime) other).value)); // state check
    }
    */

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
