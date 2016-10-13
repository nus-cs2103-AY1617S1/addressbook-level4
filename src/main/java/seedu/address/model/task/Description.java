package seedu.address.model.task;

import seedu.address.commons.exceptions.IllegalValueException;

/**
 * Represents a Task's description in the task manager.
 * Guarantees: immutable; is valid as declared in {@link #isValidDescription(String)}
 */
public class Description {

    public static final String MESSAGE_DESCRIPTION_CONSTRAINTS = "Task description should be spaces or a string";
    public static final String DESCRIPTION_VALIDATION_REGEX = "[\\p{Alnum} ]+";

    public final String fullDescription;

    /**
     * Validates given decription.
     *
     * @throws IllegalValueException if given description string is invalid.
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
     * Returns true if a given string is a valid task description.
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