package seedu.address.model.task;


import seedu.address.commons.exceptions.IllegalValueException;

/**
 * Represents a Task's due time in the task manager.
 * Guarantees: immutable; is valid as declared in {@link #isValidDueTime(String)}
 */
public class DueTime {

    public static final String MESSAGE_DUETIME_CONSTRAINTS = "";
    public static final String DUETIME_VALIDATION_REGEX = "";

    public final String value;

    /**
     * Validates given dueTime.
     *
     * @throws IllegalValueException if given due time string is invalid.
     */
    public DueTime(String dueTime) throws IllegalValueException {
        assert dueTime != null;
        dueTime = dueTime.trim();
        if (!isValidDueTime(dueTime)) {
            throw new IllegalValueException(MESSAGE_DUETIME_CONSTRAINTS);
        }
        this.value = dueTime;
    }

    /**
     * Returns if a given string is a valid due time.
     */
    public static boolean isValidDueTime(String test) {
        return test.matches(DUETIME_VALIDATION_REGEX);
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
