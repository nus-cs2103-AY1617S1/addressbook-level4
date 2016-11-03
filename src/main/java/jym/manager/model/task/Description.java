package jym.manager.model.task;

import jym.manager.commons.exceptions.IllegalValueException;

/**
 * Represents a Person's name in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidName(String)}
 */
public class Description {

    public static final String MESSAGE_DESCRIPTION_CONSTRAINTS = "Task descriptions should be spaces or alphanumeric characters";
    public static final String DESCRIPTION_VALIDATION_REGEX = "[\\p{Alnum} ]+";

    private final String fullDescription;

    /**
     * Validates given name.
     *
     * @throws IllegalValueException if given name string is invalid.
     */
    public Description(String description) throws IllegalValueException {
        assert description != null;
        description = description.trim();
        if (!isValidDescription(description)) {
            throw new IllegalValueException(MESSAGE_DESCRIPTION_CONSTRAINTS);
        }
        this.fullDescription = description;
    }

    /**
     * Returns true if a given string is a valid person name.
     */
    public static boolean isValidDescription(String test) {
        return test.matches(DESCRIPTION_VALIDATION_REGEX);
    }


    @Override
    public String toString() {
        return fullDescription;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Description // instanceof handles nulls
                && this.fullDescription.equals(((Description) other).fullDescription)); // state check
    }

    @Override
    public int hashCode() {
        return fullDescription.hashCode();
    }

}
