package seedu.cmdo.model.task;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import seedu.cmdo.commons.exceptions.IllegalValueException;

/**
 * Represents a Task's Due date in the To Do List.
 * Guarantees: immutable; is valid as declared in {@link #isValidDueByDate(String)}
 */
public class DueByDate {

    public static final String MESSAGE_DUEBYDATE_CONSTRAINTS = "Due by? You should enter a day, or a date.";
//    public static final String DUEBYDATE_VALIDATION_REGEX = ".*";

    public final LocalDate value;

    /**
     * Validates given Due date.
     *
     * @throws IllegalValueException if given due date string is invalid.
     */
    public DueByDate(LocalDate dueByDate) {
        assert dueByDate != null;
        this.value = dueByDate;
    }

    @Override
    public String toString() {
        return value.toString();
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
    
    /*
     * Produces a friendly string of values in the format MM/DD/YYYY
     * 
     * @author A0139661Y
     */
	public String getFriendlyString() {
		// If floating date, return do not print anything
		if (value.equals(LocalDate.MIN)) {
			return "";
		}
		return new StringBuilder(value.format(DateTimeFormatter.ofPattern("MM/dd/uuuu"))).toString();
	}

}
