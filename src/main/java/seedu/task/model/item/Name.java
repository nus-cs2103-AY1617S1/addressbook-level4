package seedu.task.model.item;

import seedu.task.commons.exceptions.IllegalValueException;

/**
 * Represents a Task's or an Event's name in the task book.
 * Guarantees: immutable; is valid as declared in {@link #isValidName(String)}
 */
public class Name implements Comparable<Name> {

    public static final String MESSAGE_NAME_CONSTRAINTS = "Task/Event names should be spaces or alphanumeric characters";
    public static final String NAME_VALIDATION_REGEX = "[a-zA-Z0-9#\\$\\.\\(\\)%&\\s\\,\\@\\:\\_]+";

    public final String fullName;

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
        this.fullName = name;
    }

    /**
     * Returns true if a given string is a valid task name.
     */
    public static boolean isValidName(String test) {
        return test.matches(NAME_VALIDATION_REGEX);
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

	@Override
	public int compareTo(Name o) {
		return this.fullName.compareTo(o.fullName);
	}

}
