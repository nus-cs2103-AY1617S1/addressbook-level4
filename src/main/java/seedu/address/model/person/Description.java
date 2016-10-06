package seedu.address.model.person;


import seedu.address.commons.exceptions.IllegalValueException;

/**
 * Represents a Task's phone number in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidDescription(String)}
 */
public class Description {

    public static final String MESSAGE_EMAIL_CONSTRAINTS =
    		"Description should be spaces or alphanumeric characters";
    public static final String EMAIL_VALIDATION_REGEX = "[\\p{Alnum} ]+";

    public final String description;

    /**
     * Validates given email.
     *
     * @throws IllegalValueException if given email address string is invalid.
     */
    public Description(String email) throws IllegalValueException {
        assert email != null;
        email = email.trim();
        if (!isValidDescription(email)) {
            throw new IllegalValueException(MESSAGE_EMAIL_CONSTRAINTS);
        }
        this.description = email;
    }

    /**
     * Returns if a given string is a valid person email.
     */
    public static boolean isValidDescription(String test) {
        return test.matches(EMAIL_VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return description;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Description // instanceof handles nulls
                && this.description.equals(((Description) other).description)); // state check
    }

    @Override
    public int hashCode() {
        return description.hashCode();
    }

}
