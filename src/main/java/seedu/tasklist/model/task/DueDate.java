package seedu.tasklist.model.task;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import javax.swing.text.DateFormatter;

import seedu.tasklist.commons.exceptions.IllegalValueException;

/**
 * Represents a Task's due date in the task list.
 * Guarantees: immutable; is valid as declared in {@link #isValidDate(String)}
 */
public class DueDate {
    
    public static final String MESSAGE_DUEDATE_CONSTRAINTS = "DueDate should be numeric only";
    public static final String DATE_VALIDATION_REGEX = "^(?:\\d+|)$";
    //public static final String DATE_VALIDATION_REGEX = "";

    public LocalDate dueDate;

    /**
     * Validates given due date.
     *
     * @throws IllegalValueException if given due date string is invalid.
     */
    public DueDate(String dueDate) throws IllegalValueException {
    	String month;
        String day;
        String year;
        
    	assert dueDate != null;
        if (!isValidDate(dueDate)) {
            throw new IllegalValueException(MESSAGE_DUEDATE_CONSTRAINTS);
        }
        
        if(!dueDate.equals("")){
       	month = "" + dueDate.charAt(0) + dueDate.charAt(1);
       	day = "" + dueDate.charAt(2) + dueDate.charAt(3);
       	year = "" + dueDate.charAt(4) + dueDate.charAt(5) + dueDate.charAt(6) + dueDate.charAt(7);
        
        this.dueDate = LocalDate.of(Integer.parseInt(year), Integer.parseInt(month), Integer.parseInt(day));
        }
        
        else{
        	this.dueDate = LocalDate.MAX;
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
    	DateTimeFormatter df = DateTimeFormatter.ofPattern("LLddyyyy");
    	return df.format(dueDate);
    	//return dueDate.toString();
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