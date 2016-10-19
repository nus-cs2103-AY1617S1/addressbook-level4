package seedu.cmdo.model.task;


import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

import seedu.cmdo.commons.exceptions.IllegalValueException;

/**
 * Represents a Task's due time in the To Do List.
 * Guarantees: immutable; is valid as declared in {@link #isValidDueByTime(String)}
 */
public class DueByTime {

    public static final String MESSAGE_DUEBYTIME_CONSTRAINTS = "Due at what time? You should type in a time in format HHMM";
//    public static final String DUEBYTIME_VALIDATION_REGEX = ".*";

    public final LocalTime value;

    /**
     * Validates given dueByTime.
     *
     * @throws IllegalValueException if given dueByTime string is invalid.
     */
    public DueByTime(LocalTime dueByTime) throws IllegalValueException {
        assert dueByTime != null;
        // Enable storage of floating time
        if (!dueByTime.equals(LocalTime.MAX)) {
        	this.value = dueByTime.truncatedTo(ChronoUnit.MINUTES);
        } else {
        	this.value = dueByTime;
        }
    }

    @Override
    public String toString() {
        return value.toString();
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

    /*
     * Produces a friendly string of values in the format HH:MM
     * 
     * @author A0139661Y
     */
    public String getFriendlyString() {
		// If floating date, return do not print anything
    	if (value.equals(LocalTime.MAX)) {
    		return "";
    	}
		return new StringBuilder(value.format(DateTimeFormatter.ofPattern("kkmm"))).toString();
	}
    
	// @@author A0139661Y
	public String getFriendlyStartString() {
		if (start.equals(LocalTime.MAX)) {
			return "";
		} return start.format(DateTimeFormatter.ofPattern("kkmm")).toString(); 
	}
	
	// @@author A0139661Y
	public String getFriendlyEndString() {
		if (!isRange) {
			return "";
		}
		if (end.equals(LocalTime.MAX)) {
			return "";
		} else return end.format(DateTimeFormatter.ofPattern("kkmm")).toString();
	}
}
