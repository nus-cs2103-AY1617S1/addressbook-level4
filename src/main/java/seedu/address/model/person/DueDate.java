package seedu.address.model.person;


import seedu.address.commons.exceptions.IllegalValueException;

/**
 * Represents a Task's address in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidDate(String)}
 */
public class DueDate {
    
    public static final String MESSAGE_ADDRESS_CONSTRAINTS = "DueDate should be numeric only";
    public static final String ADDRESS_VALIDATION_REGEX = "\\d+";

    public final String date;

    /**
     * Validates given address.
     *
     * @throws IllegalValueException if given address string is invalid.
     */
    public DueDate(String address) throws IllegalValueException {
        assert address != null;
        if (!isValidDate(address)) {
            throw new IllegalValueException(MESSAGE_ADDRESS_CONSTRAINTS);
        }
        this.date = address;
    }

    /**
     * Returns true if a given string is a valid person email.
     */
    public static boolean isValidDate(String test) {
        return test.matches(ADDRESS_VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return date;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DueDate // instanceof handles nulls
                && this.date.equals(((DueDate) other).date)); // state check
    }

    @Override
    public int hashCode() {
        return date.hashCode();
    }

}