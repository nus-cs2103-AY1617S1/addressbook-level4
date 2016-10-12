package seedu.address.model.task;


import seedu.address.commons.exceptions.IllegalValueException;

/**
 * Represents a Task's date number in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidStart(String)}
 */
public class StartTime {

    public static final String MESSAGE_START_CONSTRAINTS =
            "Start Time should follow the format hh:mm am/pm(or h:mm am/pm)";
    public static final String START_VALIDATION_REGEX = "((1[012]|0[1-9]|[1-9]):[0-5][0-9](?i)(am|pm)\\sto\\s)?(1[012]|[1-9]|0[1-9]):[0-5][0-9](?i)(am|pm)";

    public final String value;

    /**
     * Validates given start.
     *
     * @throws IllegalValueException if given start address string is invalid.
     */
    public StartTime(String start) throws IllegalValueException {
        assert start != null;
        start = start.trim();
        if (!isValidStartTime(start) && !start.equals("")) {
            throw new IllegalValueException(MESSAGE_START_CONSTRAINTS);
        }
        this.value = start;
    }

    /**
     * Returns if a given string is a valid task start.
     */
    public static boolean isValidStartTime(String test) {
        return test.matches(START_VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof StartTime // instanceof handles nulls
                && this.value.equals(((StartTime) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
