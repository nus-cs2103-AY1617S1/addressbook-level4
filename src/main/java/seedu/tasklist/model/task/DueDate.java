package seedu.tasklist.model.task;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import seedu.tasklist.commons.exceptions.IllegalValueException;

/**
 * Represents a Task's due date in the task list.
 * Guarantees: immutable; is valid as declared in {@link #isValidDate(String)}
 */
public class DueDate {
    
    public static final String MESSAGE_DUEDATE_CONSTRAINTS = "DueDate should be numeric only";
    public static final String DATE_VALIDATION_REGEX = "^(?:[0-9 ]+|)$";

    public final String dueDate;

    /**
     * Validates given due date.
     *
     * @throws IllegalValueException if given due date string is invalid.
     */
    public DueDate(String dueDate) throws IllegalValueException {
        assert dueDate != null;
        if (!isValidDate(dueDate)) {
            throw new IllegalValueException(MESSAGE_DUEDATE_CONSTRAINTS);
        }
        this.dueDate = dueDate;
    }

    /**
     * Returns true if a given string is a valid date.
     */
    public static boolean isValidDate(String test) {
        return test.matches(DATE_VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return dueDate.toString();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DueDate // instanceof handles nulls
                && this.dueDate.equals(((DueDate) other).dueDate)); // state check
    }

    @Override
    public int hashCode() {
        return dueDate.hashCode();
    }

}