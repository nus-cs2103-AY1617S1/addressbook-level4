package seedu.address.model.task;


import seedu.address.commons.exceptions.IllegalValueException;

/**
 * Represents a Task's startDateTime number in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidEndDateTime(String)}
 */
public class EndDateTime {

    public static final String MESSAGE_END_DATE_TIME_CONSTRAINTS =
            "Task endDateTimes should be 2 alphanumeric/period strings separated by '@'";
    public static final String END_DATE_TIME_VALIDATION_REGEX = "[\\w\\.]+@[\\w\\.]+";

    public final String value;

    /**
     * Validates given endDateTime.
     *
     * @throws IllegalValueException if given endDateTime address string is invalid.
     */
    public EndDateTime(String endDateTime) throws IllegalValueException {
        assert endDateTime != null;
        endDateTime = endDateTime.trim();
        if (!isValidEndDateTime(endDateTime)) {
            throw new IllegalValueException(MESSAGE_END_DATE_TIME_CONSTRAINTS);
        }
        this.value = endDateTime;
    }

    /**
     * Returns if a given string is a valid task endDateTime.
     */
    public static boolean isValidEndDateTime(String test) {
        return test.matches(END_DATE_TIME_VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof EndDateTime // instanceof handles nulls
                && this.value.equals(((EndDateTime) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
