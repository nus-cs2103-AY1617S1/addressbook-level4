package seedu.address.model.item;


import seedu.address.commons.exceptions.IllegalValueException;

/**
 * Represents a Item's phone number in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidEmail(String)}
 */
public class TodoTime {

    public static final String MESSAGE_TIME_CONSTRAINTS =
            "Todo times should be HH:MM";
    public static final String TIME_VALIDATION_REGEX = "\\d{2}:\\d{2}";

    public final String value;

    /**
     * Validates given time.
     *
     * @throws IllegalValueException if given date string is invalid.
     */
    public TodoTime(String time) throws IllegalValueException {
        if (time == "") {
        	this.value = "";
        } else {
            time = time.trim();
            if (!isValidTime(time)) {
                throw new IllegalValueException(MESSAGE_TIME_CONSTRAINTS);
            }
            this.value = time;
        }
    }

    /**
     * Returns if a given string is a valid person email.
     */
    public static boolean isValidTime(String test) {
        return test.matches(TIME_VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof TodoTime // instanceof handles nulls
                && this.value.equals(((TodoTime) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
