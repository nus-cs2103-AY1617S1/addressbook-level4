package seedu.address.model.task;


import seedu.address.commons.exceptions.IllegalValueException;

/**
 * Represents a Task's date number in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidStart(String)}
 */
public class StartTime {

    public static final String MESSAGE_START_CONSTRAINTS =
            "Task starts should be 2 alphanumeric/period strings separated by '@'";
    public static final String START_VALIDATION_REGEX = "[\\w\\.]+@[\\w\\.]+";

    public final String value;

    /**
     * Validates given start.
     *
     * @throws IllegalValueException if given start address string is invalid.
     */
    public StartTime(String start) throws IllegalValueException {
        assert start != null;
        start = start.trim();
        if (!isValidStartTime(start)) {
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
