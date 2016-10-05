package seedu.whatnow.model.task;


import seedu.whatnow.commons.exceptions.IllegalValueException;

/**
 * Represents a Task's whatnow in the whatnow book.
 * Guarantees: immutable; is valid as declared in {@link #isValidAddress(String)}
 */
public class Address {
    
    public static final String MESSAGE_ADDRESS_CONSTRAINTS = "Task whatnowes can be in any format";
    public static final String ADDRESS_VALIDATION_REGEX = ".+";

    public final String value;

    /**
     * Validates given whatnow.
     *
     * @throws IllegalValueException if given whatnow string is invalid.
     */
    public Address(String whatnow) throws IllegalValueException {
        assert whatnow != null;
        if (!isValidAddress(whatnow)) {
            throw new IllegalValueException(MESSAGE_ADDRESS_CONSTRAINTS);
        }
        this.value = whatnow;
    }

    /**
     * Returns true if a given string is a valid task email.
     */
    public static boolean isValidAddress(String test) {
        return test.matches(ADDRESS_VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Address // instanceof handles nulls
                && this.value.equals(((Address) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}