package seedu.todolist.model.task;

import seedu.todolist.commons.exceptions.IllegalValueException;

/**
 * Represents a Task's remarks parameter in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidRemarksParameter(String)}
 */
public class RemarksParameter {
	public static final String MESSAGE_REMARKS_PARAMETER_CONSTRAINTS = "Task's remarks parameter should be spaces or alphanumeric characters";
    public static final String REMARKS_PARAMETER_VALIDATION_REGEX = "[\\p{Alnum} ]+";

    public final String remarksParameter;
    
    /**
     * Validates given remarks parameter.
     *
     * @throws IllegalValueException if given remarks parameter string is invalid.
     */
    public RemarksParameter(String remarks) throws IllegalValueException {
        assert remarks != null;
        remarks = remarks.trim();
        if (!isValidRemarksParameter(remarks)) {
            throw new IllegalValueException(MESSAGE_REMARKS_PARAMETER_CONSTRAINTS);
        }
        this.remarksParameter = remarks;
    }

    /**
     * Returns true if a given string is a valid task's remarks parameter.
     */
    public static boolean isValidRemarksParameter(String test) {
        return test.matches(REMARKS_PARAMETER_VALIDATION_REGEX);
    }


    @Override
    public String toString() {
        return remarksParameter;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof RemarksParameter // instanceof handles nulls
                && this.remarksParameter.equals(((RemarksParameter) other).remarksParameter)); // state check
    }

    @Override
    public int hashCode() {
        return remarksParameter.hashCode();
    }
}
