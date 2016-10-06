package seedu.address.model.item;

import seedu.address.commons.exceptions.IllegalValueException;

/**
 * Represents a Person's name in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidName(String)}
 */
public class Description {

	public static final String MESSAGE_NAME_CONSTRAINTS = "Descriptions should be spaces or alphanumeric characters";
    public static final String NAME_VALIDATION_REGEX = "[\\p{Alnum} ]+";

	public final String fullDescription;

    /**
     * Validates given name.
     *
     * @throws IllegalValueException if given name string is invalid.
     */
	public Description(String desc) throws IllegalValueException {
		assert desc != null;
		desc = desc.trim();
		if (!isValidName(desc)) {
            throw new IllegalValueException(MESSAGE_NAME_CONSTRAINTS);
        }
		this.fullDescription = desc;
    }

    /**
     * Returns true if a given string is a valid person name.
     */
    public static boolean isValidName(String test) {
        return test.matches(NAME_VALIDATION_REGEX);
    }


    @Override
    public String toString() {
		return fullDescription;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Description // instanceof handles nulls
						&& this.fullDescription.equals(((Description) other).fullDescription)); // state
																								// check
    }

    @Override
    public int hashCode() {
		return fullDescription.hashCode();
    }

}
