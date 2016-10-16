package seedu.tasklist.model.task;

import seedu.tasklist.commons.exceptions.IllegalValueException;
import java.time.LocalDateTime;
import java.time.LocalDate;
/**
 * Represents a Task's start date in the task list.
 * Guarantees: immutable; is valid as declared in {@link #isValidStartDate(String)}
 */
public class StartDate {

    public static final String MESSAGE_STARTDATE_CONSTRAINTS = "StartDate should be digits only";
    public static final String DATE_VALIDATION_REGEX = "^(?:\\d+|)$";

    public LocalDate startDate;
    /**
     * Validates given start date.
     *
     * @throws IllegalValueException if given startDate string is invalid.
     */
    public StartDate(String startDate) throws IllegalValueException {
        String month;
        String day;
        String year;
    	
    	assert startDate != null;
        startDate = startDate.trim();
        if (!isValidStartDate(startDate)) {
            throw new IllegalValueException(MESSAGE_STARTDATE_CONSTRAINTS);
        }
        
        month = "" + startDate.charAt(0) + startDate.charAt(1);
        day = "" + startDate.charAt(2) + startDate.charAt(3);
        year = "" + startDate.charAt(4) + startDate.charAt(5) + startDate.charAt(6) + startDate.charAt(7);
        
        this.startDate = LocalDate.of(Integer.parseInt(year), Integer.parseInt(month), Integer.parseInt(day));
    }

    /**
     * Returns true if a given string is a valid start date.
     */
    public static boolean isValidStartDate(String test) {
        return test.matches(DATE_VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return startDate.toString();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof StartDate // instanceof handles nulls
                && this.startDate.equals(((StartDate) other).startDate)); // state check
    }

    @Override
    public int hashCode() {
        return startDate.hashCode();
    }

//	public LocalDate getStartDate() {
//		return startDate;
//	}
//
//	public void setStartDate(LocalDate startDate) {
//		this.startDate = startDate;
//	}

}
