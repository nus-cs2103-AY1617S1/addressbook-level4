package seedu.menion.model.activity;

import seedu.menion.commons.exceptions.IllegalValueException;

/**
 * Represents a Person's name in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidName(String)}
 */
public class ActivityName {

    public static final String MESSAGE_NAME_CONSTRAINTS = "Please limit the name of your activity to 40 characters.";


    public final String fullName;

    /**
     * Validates given name.
     *
     * @throws IllegalValueException if given name string is invalid.
     */
    public ActivityName(String name) throws IllegalValueException {
        assert name != null;
        name = name.trim();
        if (!isValidName(name)) {
            throw new IllegalValueException(MESSAGE_NAME_CONSTRAINTS);
        }
        this.fullName = name;
    }

    /**
     * Returns true if the input name is less than 140 characters.
     */
    public static boolean isValidName(String test) {
    	if (test.length() > 40){
    		return false;
    	}
    	
    	return true;
    }


    @Override
    public String toString() {
        return fullName;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ActivityName // instanceof handles nulls
                && this.fullName.equals(((ActivityName) other).fullName)); // state check
    }

    @Override
    public int hashCode() {
        return fullName.hashCode();
    }

}
