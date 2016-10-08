package seedu.address.model.task;

import seedu.address.commons.exceptions.IllegalValueException;

/**
 * Represents a Task's Due day in the task Manager.
 * Guarantees: immutable; is valid as declared in {@link #isValidDueDay(String)}
 */
public class DueDay {

    public static final String MESSAGE_DUEDAY_CONSTRAINTS = "";
    public static final String DUEDAY_VALIDATION_REGEX = "";

    public final String value;

    /**
     * Validates given Due Day.
     *
     * @throws IllegalValueException if given due day string is invalid.
     */
    public DueDay(String dueDay) throws IllegalValueException {
        assert dueDay != null;
        dueDay = dueDay.trim();
        if (!isValidDueDay(dueDay)) {
            throw new IllegalValueException(MESSAGE_DUEDAY_CONSTRAINTS);
        }
        this.value = dueDay;
    }

    /**
     * Returns true if a given string is a valid due day.
     */
    public static boolean isValidDueDay(String test) {
        return test.matches(DUEDAY_VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }
    /*
    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DueDay // instanceof handles nulls
                && this.value.equals(((DueDay) other).value)); // state check
    }
    */

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
