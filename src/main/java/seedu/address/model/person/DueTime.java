package seedu.address.model.person;


import seedu.address.commons.exceptions.IllegalValueException;

/**
 * Represents a Person's phone number in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidTime(String)}
 */
public class DueTime {

    public static final String MESSAGE_TIME_CONSTRAINTS =
            "Person emails should be 2 alphanumeric/period strings separated by '@'";
    public static final String TIME_VALIDATION_REGEX = "\\d+";

    public final String value;

    /**
     * Validates given email.
     *
     * @throws IllegalValueException if given email address string is invalid.
     */
    public DueTime(String email) throws IllegalValueException {
        assert email != null;
        email = email.trim();
        if (!isValidTime(email)) {
            throw new IllegalValueException(MESSAGE_TIME_CONSTRAINTS);
        }
        this.value = email;
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
                || (other instanceof DueTime // instanceof handles nulls
                && this.value.equals(((DueTime) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
