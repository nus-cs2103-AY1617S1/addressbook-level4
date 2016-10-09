package seedu.address.model.task;

import seedu.address.commons.exceptions.IllegalValueException;

/**
 * Represents a Task's deadline
 * Empty String "" means no deadline (floating task)
 * Guarantees: is valid as declared in {@link #isValidDeadline(String)}
 */
public class Deadline {
    
    public static final String MESSAGE_DEADLINE_CONSTRAINTS = "Deadline" + "should follow DD.MM.YYYY[-Time(in 24 hrs)]";
    public static final String DEADLINE_VALIDATION_REGEX = "^[0-3]?[0-9].[0-1]?[0-9].([0-9]{4})(-[0-2]?[0-9]?)?";
    // EXAMPLE = "15.10.2016-14"
    
    public String time;

    public Deadline(String time) throws IllegalValueException {
        if (!isValidDeadline(time)) {
            throw new IllegalValueException(MESSAGE_DEADLINE_CONSTRAINTS);
        }
        this.time = time;
    }

    public static boolean isValidDeadline(String test) {
        return test.matches(DEADLINE_VALIDATION_REGEX) || test.equals("");
    }

    public String toString() {
        return time;
    }

    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Deadline // instanceof handles nulls
                && this.time.equals(((Deadline) other).time)); // state check
    }

    public int hashCode() {
        return time.hashCode();
    }
}