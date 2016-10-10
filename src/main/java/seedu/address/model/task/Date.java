package seedu.address.model.task;

import seedu.address.commons.exceptions.IllegalValueException;

/**
 * Represents a Task's date in the to-do list.
 * Guarantees: immutable; is valid as declared in {@link #isValidDate(String)}
 */
public class Date {

    public static final String MESSAGE_DATE_CONSTRAINTS = "Task date should be in the format dd/mm/yy";
    public static final String DATA_VALIDATION_REGEX = "(0?[1-9]|[12][0-9]|3[01])/(0?[1-9]|1[012])/(\\d\\d)";

    public final String value;

    /**
     * Validates given date number.
     *
     * @throws IllegalValueException if given date string is invalid.
     */
    public Date(String date) throws IllegalValueException {
        assert date != null;
        date = date.trim();
        if (!isValidDate(date)) {
            throw new IllegalValueException(MESSAGE_DATE_CONSTRAINTS);
        }
        this.value = date;
    }

    /**
     * Returns true if a given string is a valid task date number.
     */
    public static boolean isValidDate(String test) {
        return test.matches(DATA_VALIDATION_REGEX);
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
