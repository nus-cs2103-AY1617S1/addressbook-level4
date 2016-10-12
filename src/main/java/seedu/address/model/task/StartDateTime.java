package seedu.address.model.task;

import seedu.address.commons.exceptions.IllegalValueException;

/**
 * Represents a Task's startDateTime number in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidStartDateTime(String)}
 */
public class StartDateTime {

    public static final String MESSAGE_START_DATE_TIME_CONSTRAINTS = "Task startDateTime numbers should only contain numbers";
    public static final String START_DATE_TIME_VALIDATION_REGEX = "\\d+";

    public final String value;

    /**
     * Validates given startDateTime number.
     *
     * @throws IllegalValueException if given startDateTime string is invalid.
     */
    public StartDateTime(String startDateTime) throws IllegalValueException {
        assert startDateTime != null;
        startDateTime = startDateTime.trim();
        if (!isValidStartDateTime(startDateTime)) {
            throw new IllegalValueException(MESSAGE_START_DATE_TIME_CONSTRAINTS);
        }
        this.value = startDateTime;
    }

    /**
     * Returns true if a given string is a valid task startDateTime number.
     */
    public static boolean isValidStartDateTime(String test) {
        return test.matches(START_DATE_TIME_VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof StartDateTime // instanceof handles nulls
                && this.value.equals(((StartDateTime) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
