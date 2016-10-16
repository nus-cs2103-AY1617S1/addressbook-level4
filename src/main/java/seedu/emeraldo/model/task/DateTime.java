package seedu.emeraldo.model.task;


import seedu.emeraldo.commons.exceptions.IllegalValueException;

/**
 * Represents a Task's date and time in Emeraldo.
 * Guarantees: immutable; is valid as declared in {@link #isValidDateTime(String)}
 */
public class DateTime {
    
    public static final String MESSAGE_ADDRESS_CONSTRAINTS = "Task date and time must follow this format YY/MM/DD HH:MM";
    public static final String ADDRESS_VALIDATION_REGEX = "(.+)?";

    public final String value;

    /**
     * Validates given date and time.
     *
     * @throws IllegalValueException if given address string is invalid.
     */
    public DateTime(String dateTime) throws IllegalValueException {
        assert dateTime != null;
        if (!isValidDateTime(dateTime)) {
            throw new IllegalValueException(MESSAGE_ADDRESS_CONSTRAINTS);
        }
        this.value = dateTime;
    }

    private static boolean isValidDateTime(String test) {
        return test.matches(ADDRESS_VALIDATION_REGEX);
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