package seedu.gtd.model.task;

import seedu.gtd.commons.exceptions.IllegalValueException;

/**
 * Represents a Task's due date in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidDueDate(String)}
 */
public class DueDate {

    public static final String MESSAGE_DUEDATE_CONSTRAINTS = "Task duedate numbers should only contain numbers";
    public static final String DUEDATE_VALIDATION_REGEX = "(0?[1-9]|[12][0-9]|3[01])/(0?[1-9]|1[012])/((19|20)\\d\\d)"; //dd-mm-yyyy

    public final String value;

    /**
     * Validates given due date.
     *
     * @throws IllegalValueException if given due date string is invalid.
     */
    public DueDate(String duedate) throws IllegalValueException {
        assert duedate != null;
        duedate = duedate.trim();
        if (!isValidDueDate(duedate)) {
            throw new IllegalValueException(MESSAGE_DUEDATE_CONSTRAINTS);
        }
        this.value = duedate;
    }

    /**
     * Returns true if a given string is a valid task due date number.
     */
    public static boolean isValidDueDate(String test) {
        return test.matches(DUEDATE_VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DueDate // instanceof handles nulls
                && this.value.equals(((DueDate) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
