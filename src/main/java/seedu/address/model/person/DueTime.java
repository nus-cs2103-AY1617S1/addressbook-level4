package seedu.address.model.person;


import seedu.address.commons.exceptions.IllegalValueException;

/**
 * Represents a Person's phone number in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidTime(String)}
 */
public class DueTime {

    public static final String MESSAGE_TIME_CONSTRAINTS =
            "Invalid/Unsupported time format. Recommended format: (HH:MM)";
    public static final String TIME_VALIDATION_REGEX = "(^([0-9]|0[0-9]|1[0-9]|2[0-3]):?[0-5][0-9]"
            										+"(\\s*(hrs|HRS|hRs|HRs|HrS|Hrs|hRS))*$)|"
            										+ "(^([0-9]|1[0-2])(:[0-5][0-9])?\\s*(PM|AM|pm|am|Pm|Am|aM|pM))$";

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
