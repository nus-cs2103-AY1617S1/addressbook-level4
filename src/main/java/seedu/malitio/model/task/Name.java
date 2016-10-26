package seedu.malitio.model.task;

import seedu.malitio.commons.exceptions.IllegalValueException;

/**
 * Represents a Task's name in the Malitio.
 * Guarantees: immutable; is valid as declared in {@link #isValidName(String)}
 */
public class Name {

    public final String fullName;

    /**
     * Validates given name.
     */
    public Name(String name) {
        assert name != null;
        name = name.trim();       
        this.fullName = name;
    }

    @Override
    public String toString() {
        return fullName;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Name // instanceof handles nulls
                && this.fullName.equals(((Name) other).fullName)); // state check
    }

    @Override
    public int hashCode() {
        return fullName.hashCode();
    }

}
