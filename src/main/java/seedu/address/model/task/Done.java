package seedu.address.model.task;

import seedu.address.commons.exceptions.IllegalValueException;

/**
 * Represents a task's date number in the task manager.
 * Guarantees: immutable; is valid as declared in {@link #isValidDate(String)}
 */
public class Done {

//    public static final String MESSAGE_DATE_CONSTRAINTS = "Date numbers should follow the format dd/mm/yy";
//    public static final String DATE_VALIDATION_REGEX = "(0?[1-9]|[12][\\d]|3[01])/(0?[1-9]|1[012])/(\\d\\d)";

    public Boolean value;

    /**
     * Validates given date number.
     *
     * @throws IllegalValueException if given date string is invalid.
     */
    public Done(Boolean done) throws IllegalValueException {
        this.value = done;
    }
    
    public void setDone(Boolean done) {
        this.value = done;
    }
    
    public Boolean getDone() {
    	return this.value;
    }
    
    @Override
    public String toString() {
    	return "";
    }
    
    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Done // instanceof handles nulls
                && this.value.equals(((Done) other).value)); // state check
    }

}
