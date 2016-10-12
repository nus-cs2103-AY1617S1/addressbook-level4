package seedu.address.model.person;


import seedu.address.commons.exceptions.IllegalValueException;

/**
 * Represents a Person's phone number in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidStart(String)}
 */
public class Start {

    public static final String MESSAGE_START_CONSTRAINTS =
            "Person starts should be 2 alphanumeric/period strings separated by '@'";
    public static final String START_VALIDATION_REGEX = "[\\w\\.]+@[\\w\\.]+";

    public final String value;

    /**
     * Validates given start.
     *
     * @throws IllegalValueException if given start address string is invalid.
     */
    public Start(String start) throws IllegalValueException {
        assert start != null;
        start = start.trim();
        if (!isValidStart(start)) {
            throw new IllegalValueException(MESSAGE_START_CONSTRAINTS);
        }
        this.value = start;
    }

    /**
     * Returns if a given string is a valid person start.
     */
    public static boolean isValidStart(String test) {
        return test.matches(START_VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Start // instanceof handles nulls
                && this.value.equals(((Start) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
