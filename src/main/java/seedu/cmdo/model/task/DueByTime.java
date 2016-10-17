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
    public final Duration duration;
    
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
        	this.end = dueByTime.truncatedTo(ChronoUnit.MINUTES);
        	this.start = end;
        } else {
        	this.end = dueByTime;
        	this.start = end;
        }
    	this.duration = Duration.ZERO;
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
        this.start = dueByTimeStart.truncatedTo(ChronoUnit.MINUTES);
        this.end = dueByTimeEnd.truncatedTo(ChronoUnit.MINUTES);
        this.duration = Duration.between(start, end);
        this.isRange = true;
    }

    @Override
    public String toString() {
        if (isRange)
        	return new StringBuilder(start.toString() + "-" + end.toString()).toString();
        else 
        	return end.toString();
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

    /*
     * Produces a friendly string of values in the format HH:MM
     * 
     * @@author A0139661Y
     */
    public String getFriendlyString() {
		// If floating date, return do not print anything
    	if (isRange) {
    		if (end.equals(LocalTime.MAX)) {
    			return "";
    		}
    		return new StringBuilder(end.format(DateTimeFormatter.ofPattern("kkmm"))).toString();
    	}
		return new StringBuilder(start.format(DateTimeFormatter.ofPattern("kkmm")) 
				+ " - " 
				+ end.format(DateTimeFormatter.ofPattern("kkmm")))
				.toString();
	}
}