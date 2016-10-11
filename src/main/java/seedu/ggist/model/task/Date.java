package seedu.ggist.model.task;


import seedu.ggist.commons.core.Messages;
import seedu.ggist.commons.exceptions.IllegalValueException;

/**
 * Represents a Task's date in the GGist.
 * Guarantees: immutable; is valid as declared in right format
 */
public class Date {

    public static final String MESSAGE_DATE_CONSTRAINTS =
            "Task date should be 2 ";
    public static final String DATE_VALIDATION_REGEX = "[\\p{Alnum} ]+";

    public String value;

    /**
     * Validates given date.
     *
     * @throws IllegalValueException if given date string is invalid.
     */
    public Date(String date) throws IllegalValueException {
        assert date != null;
        date = date.trim();
        if (!date.equals(Messages.MESSAGE_NO_DATE_SPECIFIED) && !isValidDate(date)) {
            throw new IllegalValueException(MESSAGE_DATE_CONSTRAINTS);
        } 
        this.value = date;
    }

    /**
     * Returns if a given string is a valid date.
     */
    public static boolean isValidDate(String test) {
        return test.matches(DATE_VALIDATION_REGEX);
    }
    
    public void editDate(String newDate) throws IllegalValueException{
    	 assert newDate != null;
         newDate = newDate.trim();
         if (!newDate.equals(Messages.MESSAGE_NO_DATE_SPECIFIED) && !isValidDate(newDate)) {
             throw new IllegalValueException(MESSAGE_DATE_CONSTRAINTS);
         } 
         this.value = newDate;
         
    }
    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Date // instance of handles nulls
                && this.value.equals(((Date) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
