package seedu.address.model.task;

import seedu.address.commons.exceptions.IllegalValueException;

/**
 * Represents a Person's name in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidName(String)}
 */
public class Discription {

    public static final String MESSAGE_DISCRIPTION_CONSTRAINTS = "Person names should be spaces or alphanumeric characters";
    public static final String DISCRIPTION_VALIDATION_REGEX = "[\\p{Alnum} ]+";

    public final String fullDiscription;

    /**
     * Validates given name.
     *
     * @throws IllegalValueException if given name string is invalid.
     */
    public Discription(String discription) throws IllegalValueException {
        assert discription != null;
        discription = discription.trim();
        if (!isValidDiscription(discription)) {
            throw new IllegalValueException(MESSAGE_DISCRIPTION_CONSTRAINTS);
        }
        this.fullDiscription = discription;
    }

    /**
     * Returns true if a given string is a valid person name.
     */
    public static boolean isValidDiscription(String test) {
        return test.matches(DISCRIPTION_VALIDATION_REGEX);
    }


    @Override
    public String toString() {
        return fullDiscription;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Discription // instanceof handles nulls
                && this.fullDiscription.equals(((Discription) other).fullDiscription)); // state check
    }

    @Override
    public int hashCode() {
        return fullDiscription.hashCode();
    }

}
