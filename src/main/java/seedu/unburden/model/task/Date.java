package seedu.unburden.model.task;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import seedu.unburden.commons.exceptions.IllegalValueException;

/**
 * Represents a Task's date in the task manager
 * Guarantees: immutable; is valid as declared in {@link #isValidDate(String)}
 * @@author A0143095H
 */
<<<<<<< HEAD
//@@ Gauri Joshi A0143095H
=======

>>>>>>> 8d8e4ba9ff12a062a4380758728d06dbc71c916d
public class Date {

    public static final String MESSAGE_DATE_CONSTRAINTS = "Task dates should be in the format dd-mm-yyyy";
    public static final String DATE_VALIDATION_REGEX = "[0-9]{2}[-][0-9]{2}[-][0-9]{4}$";
    
   

    public final String fullDate;

    /**
     * Validates given date.
     *
     * @throws IllegalValueException if given date string is invalid.
     */
    public Date(String date) throws IllegalValueException {
        /*assert date != null;
        date = date.trim();
        if (!isValidDate(date)) {
            throw new IllegalValueException(MESSAGE_DATE_CONSTRAINTS);
        }
        */
        this.fullDate = date;
    }

    /**
     * Returns true if a given string is a valid date.
     */
    public static boolean isValidDate(String test) {
        return test.matches(DATE_VALIDATION_REGEX);
    }
    
    public java.util.Date toDate() throws ParseException {
    	DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
    	return df.parse(fullDate);
    }


    @Override
    public String toString() {
        return fullDate;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Date // instanceof handles nulls
                && this.fullDate.equals(((Date) other).fullDate)); // state check
    }

    @Override
    public int hashCode() {
        return fullDate.hashCode();
    }

}
