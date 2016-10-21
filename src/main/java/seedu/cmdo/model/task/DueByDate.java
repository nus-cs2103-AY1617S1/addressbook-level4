package seedu.cmdo.model.task;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import seedu.cmdo.commons.exceptions.IllegalValueException;

/**
 * Represents a Task's Due date in the To Do List.
 * Guarantees: immutable; is valid as declared in {@link #isValidDueByDate(String)}
 */
public class DueByDate {

    public static final String MESSAGE_DUEBYDATE_CONSTRAINTS = "Due by? You should enter a day, or a date.";
//    public static final String DUEBYDATE_VALIDATION_REGEX = ".*";

    public final LocalDate start;
    public final LocalDate end;
    public final Boolean isRange;

    /**
     * Takes in a single date.
     *
     * @throws IllegalValueException if given due date string is invalid.
     * 
     * @@author A0139661Y
     */
    public DueByDate(LocalDate dueByDate) throws IllegalValueException {
        assert dueByDate != null;
        this.end = LocalDate.MIN;
        this.start = dueByDate;
        this.isRange = false;
    }
    
    /**
     * Takes in a start date and end date.
     *
     * @throws IllegalValueException if given due date string is invalid.
     * 
     * @@author A0139661Y
     */
    public DueByDate(LocalDate dueByDateStart, LocalDate dueByDateEnd) {
        assert dueByDateStart != null && dueByDateEnd != null;
        this.start = dueByDateStart;
        this.end = dueByDateEnd;
        this.isRange = true;
    }

    @Override
    public String toString() {
        if (isRange)
        	return new StringBuilder(start.toString() + "/to/" + end.toString()).toString();
        else 
        	return start.toString();
    }
    
    public boolean isRange() {
        return isRange;
    }
    
    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DueByDate // instanceof handles nulls
                && this.equals((DueByDate) other));
    }

    @Override
    public int hashCode() {
    	return Objects.hash(start, end);
    }
    
    /*
     * Produces a friendly string of values in the format MM/DD/YYYY
     * 
     * @@author A0139661Y
     */
	public String getFriendlyString() {		
		// If floating date, return do not print anything
		if (start.equals(LocalDate.MIN) && end.equals(LocalDate.MIN))
			return "";
		if (!isRange) {
			return new StringBuilder(start.format(DateTimeFormatter.ofPattern("MM/dd/uuuu"))).toString();
		}
		return new StringBuilder(start.format(DateTimeFormatter.ofPattern("MM/dd/uuuu")) 
								+ " - " 
								+ end.format(DateTimeFormatter.ofPattern("MM/dd/uuuu")))
								.toString();
	}
	
	// Operates on the premise that the start date is always specified.
	// @@author A0139661Y
	public String getFriendlyStartString() {
		if (!isRange)
			return "";
		return start.format(DateTimeFormatter.ofPattern("MM/dd/uuuu")).toString(); 
	}
	
	// @@author A0139661Y
	public String getFriendlyEndString() {
		if (end.equals(LocalDate.MIN)) {
			return "";
		} else return end.format(DateTimeFormatter.ofPattern("MM/dd/uuuu")).toString();
	}
}