package seedu.tasklist.model.task;

import seedu.tasklist.commons.exceptions.IllegalValueException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
/**
 * Represents a Task's start date in the task list.
 * Guarantees: immutable; is valid as declared in {@link #isValidStartDate(String)}
 */
public class StartDate {

    public static final String MESSAGE_STARTDATE_CONSTRAINTS = "StartDate should be numeric only";
    public static final String DATE_VALIDATION_REGEX = "^(?:[0-9 ]+|)$";
    public LocalDateTime startDate;
    
    /**
     * Validates given start date.
     *
     * @throws IllegalValueException if given startDate string is invalid.
     */
    public StartDate(String startDate) throws IllegalValueException {
    	
    	assert startDate != null;
        startDate = startDate.trim();
        if (!isValidStartDate(startDate)) {
            throw new IllegalValueException(MESSAGE_STARTDATE_CONSTRAINTS);
        } 
        
        if(!startDate.trim().isEmpty()){

        	this.startDate = LocalDateTime.of(Integer.parseInt(startDate.substring(4, 8)), Integer.parseInt(startDate.substring(0, 2))
            		, Integer.parseInt(startDate.substring(2, 4)), Integer.parseInt(startDate.substring(9, 11)),
            				Integer.parseInt(startDate.substring(11, 13)));           
        }
    }

    /**
     * Returns true if a given string is a valid start date.
     */
    public static boolean isValidStartDate(String test) {
        return test.matches(DATE_VALIDATION_REGEX);
    }

    @Override
    public String toString() {
    	if(startDate != null){    	
    		DateTimeFormatter df = DateTimeFormatter.ofPattern("MMddyyyy HHmm");
    		return df.format(startDate);
    	} else{
    		return "";
    	}
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

}
