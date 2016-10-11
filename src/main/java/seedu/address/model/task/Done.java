package seedu.address.model.task;

import seedu.address.commons.exceptions.IllegalValueException;

/**
 * Represents if a Task's is done in the To Do List.
 * Guarantees: immutable; is valid as declared in {@link #isValidDone(String)}
 */
public class Done {

    public static final String MESSAGE_DONE_CONSTRAINTS = "";
    public static final String DONE_VALIDATION_REGEX = "";

    public final String value;

    /**
     * Validates given done.
     *
     * @throws IllegalValueException if given done is invalid.
     */
    public Done(String done) throws IllegalValueException {
        assert done != null;
        done = done.trim();
        if (!isValidDone(done)) {
            throw new IllegalValueException(MESSAGE_DONE_CONSTRAINTS);
        }
        this.value = done;
    }

    /**
     * Returns true if a given string is a valid done.
     */
    public static boolean isValidDone(String test) {
        return test.matches(DONE_VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }
    /*
    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Done // instanceof handles nulls
                && this.value.equals(((Done) other).value)); // state check
    }
	*/
    
    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
