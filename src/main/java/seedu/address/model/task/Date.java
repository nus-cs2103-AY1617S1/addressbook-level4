package seedu.address.model.task;

import seedu.address.commons.exceptions.IllegalValueException;

/**
 * Represents a task's date number in the task manager.
 * Guarantees: immutable; is valid as declared in {@link #isValidDate(String)}
 */
public class Date {

//    public static final String MESSAGE_DATE_CONSTRAINTS = "Date numbers should follow the format dd/mm/yy";
//    public static final String DATE_VALIDATION_REGEX = "(0?[1-9]|[12][\\d]|3[01])/(0?[1-9]|1[012])/(\\d\\d)";

    public String value;

    /**
     * Validates given date number.
     *
     * @throws IllegalValueException if given date string is invalid.
     */
    public Date(String date) throws IllegalValueException {
        assert date != null;
        date = date.trim();

//        if (!isValidDate(date) && !date.equals("")) {
//            throw new IllegalValueException(MESSAGE_DATE_CONSTRAINTS);
//        }
        this.value = date;
    }

//    /**
//     * Returns true if a given string is a valid task date number.
//     */
//    public static boolean isValidDate(String test) {
//        return test.matches(DATE_VALIDATION_REGEX);
//    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Date // instanceof handles nulls
                && this.value.equals(((Date) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
    
    public void setDate(String date) {
        this.value = date;
    }
    
    public String getDate() {
    	return this.value;
    }

}
