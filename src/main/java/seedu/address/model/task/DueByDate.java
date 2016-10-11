package seedu.address.model.task;

import seedu.address.commons.exceptions.IllegalValueException;

/**
 * Represents a Task's Due date in the To Do List.
 * Guarantees: immutable; is valid as declared in {@link #isValidDueByDate(String)}
 */
public class DueByDate {

    public static final String MESSAGE_DUEBYDATE_CONSTRAINTS = "";
    public static final String DUEBYDATE_VALIDATION_REGEX = "";

    public final String value;

    /**
     * Validates given Due date.
     *
     * @throws IllegalValueException if given due date string is invalid.
     */
    public DueByDate(String dueByDate) throws IllegalValueException {
        assert dueByDate != null;
        dueByDate = dueByDate.trim();
        if (!isValidDueByDate(dueByDate)) {
            throw new IllegalValueException(MESSAGE_DUEBYDATE_CONSTRAINTS);
        }
        this.value = dueByDate;
    }

    /**
     * Returns true if a given string is a valid due date.
     */
    public static boolean isValidDueByDate(String test) {
        return test.matches(DUEBYDATE_VALIDATION_REGEX);
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
