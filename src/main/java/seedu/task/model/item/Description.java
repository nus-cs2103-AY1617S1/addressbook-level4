package seedu.task.model.item;


import seedu.task.commons.exceptions.IllegalValueException;

/**
 * Represents a Task's or Event's description in the task book.
 * Can be used for recording more details about the task or event.
 * Guarantees: immutable; is valid as declared in {@link #isValidDescription(String)}
 */

//@@author A0127570H
public class Description {
    
    public static final String MESSAGE_DESCRIPTION_CONSTRAINTS = "Task/Event description should be spaces, alphanumeric characters or these symbols .,:$#@%&()_";
    public static final String DESCRIPTION_VALIDATION_REGEX = "[a-zA-Z0-9#\\$\\.\\(\\)%&\\s\\,\\@\\:\\_\\-]+";

    public final String value;

    /**
     * Validates given description.
     *
     * @throws IllegalValueException if given description string is invalid.
     */
    public Description(String description) throws IllegalValueException {
        assert description != null;
        if (!isValidDescription(description)) {
            throw new IllegalValueException(MESSAGE_DESCRIPTION_CONSTRAINTS);
        }
        this.value = description;
    }

    /**
     * Returns true if a given string is a valid description.
     */
    public static boolean isValidDescription(String test) {
        return test.matches(DESCRIPTION_VALIDATION_REGEX);
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