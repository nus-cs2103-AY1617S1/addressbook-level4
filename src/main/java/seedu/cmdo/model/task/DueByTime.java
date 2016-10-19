package seedu.cmdo.model.task;


import java.time.Duration;
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

    public final LocalTime start;
    public final LocalTime end;
    public final Boolean isRange;
    
    /**
     * Validates given dueByTime.
     *
     * @throws IllegalValueException if given dueByTime string is invalid.
     * 
     * @@author A0139661Y
     */
    public DueByTime(LocalTime dueByTime) throws IllegalValueException {
        assert dueByTime != null;
        // Enable storage of floating time
        if (!dueByTime.equals(LocalTime.MAX)) {
        	this.start = dueByTime.truncatedTo(ChronoUnit.MINUTES);
        } else {
        	this.start = dueByTime;
        }
    	this.end = LocalTime.MAX;
    	this.isRange = false;
    }
    
    /**
     * Create a range of dueByTime.
     *
     * @throws IllegalValueException if given dueByTime string is invalid.
     * 
     * @@author A0139661Y
     */
    public DueByTime(LocalTime dueByTimeStart, LocalTime dueByTimeEnd) throws IllegalValueException {
        assert dueByTimeStart != null && dueByTimeEnd != null;
        // Enable storage of floating time in date range
        if (dueByTimeStart.equals(LocalTime.MAX) && dueByTimeEnd.equals(LocalTime.MAX)) {
        	this.start = LocalTime.MAX;
        	this.end = start;
        } else {
        	this.start = dueByTimeStart.truncatedTo(ChronoUnit.MINUTES);
        	this.end = dueByTimeEnd.truncatedTo(ChronoUnit.MINUTES);
        }
        this.isRange = true;
    }

    @Override
    public String toString() {
        if (isRange)
        	return new StringBuilder(start.toString() + "/to/" + end.toString()).toString();
        else 
        	return start.toString();
    }
    
    /*
    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DueTime // instanceof handles nulls
                && this.value.equals(((DueTime) other).value)); // state check
    }
    */

//    @Override
//    public int hashCode() {
//        return value.hashCode();
//    }

	//@@author A0139661Y
    public boolean isRange() {
		return isRange;
	}
    
    /*
     * Produces a friendly string of values in the format HH:MM
     * 
     * @@author A0139661Y
     */
    public String getFriendlyString() {
		// If floating date, return do not print anything
		if (start.equals(LocalTime.MAX) && end.equals(LocalTime.MAX)) {
			return "";
		}
    	if (!isRange) {
    		return new StringBuilder(start.format(DateTimeFormatter.ofPattern("kkmm"))).toString();
    	}
		return new StringBuilder(start.format(DateTimeFormatter.ofPattern("kkmm")) 
				+ " - " 
				+ end.format(DateTimeFormatter.ofPattern("kkmm")))
				.toString();
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