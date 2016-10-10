package seedu.menion.model.activity;

import seedu.menion.commons.exceptions.IllegalValueException;

/**
 * Represents a Task's deadline in the task manager.
 * Guarantees: immutable; is valid as declared in {@link #isValidDeadline(String)}
 */
public class DeadlineDate {

    public static final String MESSAGE_DEADLINE_CONSTRAINTS = "Task deadline should be in dd-mm-yy or dd-mm-yyyy format";
    public static final String DEADLINE_VALIDATION_REGEX = "^[0-3][0-9]-[0-1][0-9]-[0-2][0][0-9][0-9]$";
    public static final String DEADLINE_SECOND_VALIDATION_REGEX = "^[0-3][0-9]-[0-1][0-9]-[0-9][0-9]$";     

    public final String value;

    /**
     * Validates given deadline.
     *
     * @throws IllegalValueException if given deadline string is invalid.
     */
    public DeadlineDate(String deadline) throws IllegalValueException {
        assert deadline != null;
        deadline = deadline.trim();
        if (!isValidDeadline(deadline)) {
            throw new IllegalValueException(MESSAGE_DEADLINE_CONSTRAINTS);
        }
        this.value = deadline;
    }

    /**
     * Returns true if a given string is a valid task deadline.
     */
    public static boolean isValidDeadline(String test) {
        boolean result = false;
        
        if (test.matches(DEADLINE_SECOND_VALIDATION_REGEX) || test.matches(DEADLINE_VALIDATION_REGEX)) {
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
                || (other instanceof DeadlineDate // instanceof handles nulls
                && this.value.equals(((DeadlineDate) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
