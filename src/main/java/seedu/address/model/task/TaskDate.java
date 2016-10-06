package seedu.address.model.task;

import java.time.LocalTime;
import java.time.LocalDate;
import java.time.LocalDateTime;
import seedu.address.commons.exceptions.IllegalValueException;

/**
 * Represents a Task's from and till date.
 * Guarantees: mutable; is valid as declared in {@link #isValidPhone(String)}
 */
public class TaskDate {

    public static final String MESSAGE_DATETIME_CONSTRAINTS = "Tasks' dates need to follow predefined format.";
    public static final String DATETIME_VALIDATION_REGEX = "\\d+";

    public LocalDateTime datetime;

    /**
     * Validates given phone number.
     *
     * @throws IllegalValueException if given phone string is invalid.
     */
    public TaskDate(String datetime) throws IllegalValueException {
        if (datetime == null) {
        	this.datetime = null;
        } else {
        	datetime = datetime.trim();
            
            if (!isValidDateTime(datetime)) {
                throw new IllegalValueException(MESSAGE_DATETIME_CONSTRAINTS);
            }
            this.datetime = null;
        }
    }

    /**
     * Returns true if a given string is a valid person phone number.
     */
    public static boolean isValidDateTime(String datetime) {
    	return datetime.matches(DATETIME_VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return datetime.toString();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof TaskDate // instanceof handles nulls
                && this.datetime.equals(((TaskDate) other).datetime)); // state check
    }

    @Override
    public int hashCode() {
        return datetime.hashCode();
    }

}
