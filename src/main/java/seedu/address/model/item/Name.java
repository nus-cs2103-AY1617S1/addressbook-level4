package seedu.address.model.item;

import seedu.address.commons.exceptions.IllegalValueException;

public class Name {

    public static final String NAME_VALIDATION_REGEX = "[\\p{Alnum} ]+";
    public static final String MESSAGE_NAME_CONSTRAINTS = "Task names should be spaces or alphanumeric characters";
    
    public final String name;
    
    /**
     * Validates given name.
     *
     * @throws IllegalValueException if given name string is invalid.
     */
    public Name(String name) throws IllegalValueException {
        assert name != null;
        name = name.trim();
        if (!isValidName(name)) {
            throw new IllegalValueException(MESSAGE_NAME_CONSTRAINTS);
        }
        this.name = name;
    }

    /**
     * Returns true if a given value is a valid priority value.
     */
    public static boolean isValidName(String name) {
        return name.matches(NAME_VALIDATION_REGEX);
    }
    
    @Override
    public String toString() {
        return this.name;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Name // instanceof handles nulls
                && this.name.equals(((Name) other).name)); // state check
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }
}
