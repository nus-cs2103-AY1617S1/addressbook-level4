package seedu.tasklist.model.task;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import seedu.tasklist.commons.exceptions.IllegalValueException;

/**
 * Represents a Task's due date in the task list.
 * Guarantees: immutable; is valid as declared in {@link #isValidDate(String)}
 */
public class DueDate {
    
    public static final String MESSAGE_DUEDATE_CONSTRAINTS = "DueDate should be numeric only";
    public static final String DATE_VALIDATION_REGEX = "^(?:\\d+|)$";

    public LocalDate dueDate;

    /**
     * Validates given due date.
     *
     * @throws IllegalValueException if given due date string is invalid.
     */
    public DueDate(String dueDate) throws IllegalValueException {
    	assert dueDate != null;
        if (!isValidDate(dueDate)) {
            throw new IllegalValueException(MESSAGE_DUEDATE_CONSTRAINTS);
        }
        
        if(!dueDate.isEmpty()){
        	this.dueDate = LocalDate.of(Integer.parseInt(dueDate.substring(4, 8)), Integer.parseInt(dueDate.substring(0, 2))
            		, Integer.parseInt(dueDate.substring(2, 4)));
        }
    }

    /**
     * Returns true if a given string is a valid date.
     */
    public static boolean isValidDate(String test) {
        return test.matches(DATE_VALIDATION_REGEX);
    }

    @Override
    public String toString() {
    		if(dueDate !=null){
    		DateTimeFormatter df = DateTimeFormatter.ofPattern("LLddyyyy");
    		return df.format(dueDate);
    	} else{
    		return "";
    	}
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DueDate // instanceof handles nulls
                && this.dueDate.equals(((DueDate) other).dueDate)); // state check
    }

    @Override
    public int hashCode() {
        return dueDate.hashCode();
    }

}