package seedu.address.model.task;

import seedu.address.commons.exceptions.IllegalValueException;

/**
 * Represents a Task date in Simply.
 * Guarantees: immutable; is valid as declared in {@link #isValidDate(String)}
 */
public class Date {

    public static final String MESSAGE_DATE_CONSTRAINTS = "Dates should only contain 6 numbers";
    public static final String DATE_VALIDATION_REGEX = "\\d{6}|(no date)";

    public final String value;

    /**
     * Validates given date.
     *
     * @throws IllegalValueException if given date string is invalid.
     */
    public Date(String date) throws IllegalValueException {
        //assert date != null;
        date = date.trim();
        if (!isValidDate(date)) {
            throw new IllegalValueException(MESSAGE_DATE_CONSTRAINTS);
        }
        this.value = date;
    }

    /**
     * Returns true if a given string is a valid task date.
     */
    public static boolean isValidDate(String test) {
    	if (test.matches(DATE_VALIDATION_REGEX))
    		return true;
    	else
    		return false;
    }

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

}
