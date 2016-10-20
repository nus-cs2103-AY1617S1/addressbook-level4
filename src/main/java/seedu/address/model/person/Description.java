package seedu.address.model.person;

import seedu.address.commons.exceptions.IllegalValueException;

/**
 * Represents a Task's description in the to-do-list
 * Guarantees: immutable; is valid as declared in {@link #isValidPhone(String)}
 */
public class Description {

    public static final String MESSAGE_DESCRIPTION_CONSTRAINTS = 
            "Description can contain anything";
    public static final String DESCRIPTION_VALIDATION_REGEX = ".+";

    public final String value;

    /**
     * Validates given description.
     *
     * @throws IllegalValueException if given description string is invalid.
     */
    public Description(String description) throws IllegalValueException {
    	if (description == null){
    		this.value = ""; 
    		return;
    	}
    	
        description = description.trim();
        if (!isValidPhone(description)) {
            throw new IllegalValueException(MESSAGE_DESCRIPTION_CONSTRAINTS);
        }
        this.value = description;
    }

    /**
     * Returns true if a given string is a valid description.
     */
    public static boolean isValidPhone(String test) {
        return test.equals("") || test.matches(DESCRIPTION_VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Description // instanceof handles nulls
                && this.value.equals(((Description) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
