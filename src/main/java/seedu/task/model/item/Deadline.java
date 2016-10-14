package seedu.task.model.item;

import seedu.task.commons.exceptions.IllegalValueException;


/**
 * Represents a Task's deadline in the task book.
 * Guarantees: immutable; is valid as declared in {@link #isValidDeadline(String)}
 * @author kian ming
 */
public class Deadline {

    public static final String MESSAGE_DEADLINE_CONSTRAINTS = "Task deadline should be in a date format of DD-MM-YY";
    public static final String DEADLINE_VALIDATION_REGEX = "(([0-9]{2})[-]([0-9]{2})[-]([0-9]{2}))?"; 

    public final String value;

    /**
     * Validates given deadline.
     *
     * @throws IllegalValueException if given deadline string is invalid.
     */
    public Deadline(String deadline) throws IllegalValueException {
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
        return test.matches(DEADLINE_VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Deadline // instanceof handles nulls
                && this.value.equals(((Deadline) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
