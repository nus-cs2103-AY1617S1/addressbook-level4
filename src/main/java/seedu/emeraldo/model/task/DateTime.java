package seedu.emeraldo.model.task;


import seedu.emeraldo.commons.exceptions.IllegalValueException;

/**
 * Represents a Task's date and time in Emeraldo.
 * Guarantees: immutable; is valid as declared in {@link #isValidDateTime(String)}
 */
public class DateTime {
    
    public static final String MESSAGE_DATETIME_CONSTRAINTS = "Task date and time must follow this format DD/MM/YYYY HH:MM in 24 hours format";
    public static final String DATETIME_VALIDATION_REGEX = "((0?[1-9]|[12][0-9]|3[01])/(0?[1-9]|[1][0-2])/(([0-9][0-9])?[0-9][0-9])){1}"
                                                            + "( ([01][0-9]|[2][0-3]):([0-5][0-9]))?";

    public final String value;

    /**
     * Validates given date and time.
     *
     * @throws IllegalValueException if given address string is invalid.
     */
    public DateTime(String dateTime) throws IllegalValueException {
        assert dateTime != null;
        if (!isValidDateTime(dateTime)) {
            throw new IllegalValueException(MESSAGE_DATETIME_CONSTRAINTS);
        }
        this.value = dateTime;
    }

    private static boolean isValidDateTime(String test) {
        if(test.equals(""))
            return true;
        else
            return test.matches(DATETIME_VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DateTime // instanceof handles nulls
                && this.value.equals(((DateTime) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}