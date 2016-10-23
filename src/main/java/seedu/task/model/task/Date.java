package seedu.task.model.task;

import seedu.task.commons.exceptions.IllegalValueException;

/**
 * Represents a task's date in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidDate(String)}
 */
public class Date {

    public static final String MESSAGE_DATE_CONSTRAINTS = "The date of a task should be in DD.MM.YYYY";
    public static final String DATE_VALIDATION_REGEX = "^\\s*$|[\\d]{2}.[\\d]{2}.[\\d]{4}";

    public final String value;

    /**
     * Validates given date.
     *
     * @throws IllegalValueException if given phone string is invalid.
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
     * Returns true if a given string is a valid task phone number.
     */
    public static boolean isValidDate(String test) {
        return test.matches(DATE_VALIDATION_REGEX);
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