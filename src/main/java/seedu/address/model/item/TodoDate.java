package seedu.address.model.item;


import seedu.address.commons.exceptions.IllegalValueException;

/**
 * Represents a Item's phone number in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidEmail(String)}
 */
public class TodoDate {

    public static final String MESSAGE_DATE_CONSTRAINTS =
            "Todo dates should be YYYY-MM-DD";
    public static final String DATE_VALIDATION_REGEX = "\\d{4}-\\d{2}-\\d{2}";

    public final String value;

    /**
     * Validates given date.
     *
     * @throws IllegalValueException if given date string is invalid.
     */
    public TodoDate(String date) throws IllegalValueException {
        if (date == "") {
        	this.value = "";
        } else {
            date = date.trim();
            if (!isValidDate(date)) {
                throw new IllegalValueException(MESSAGE_DATE_CONSTRAINTS);
            }
            this.value = date;
        }
    }

    /**
     * Returns if a given string is a valid person email.
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
                || (other instanceof TodoDate // instanceof handles nulls
                && this.value.equals(((TodoDate) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
