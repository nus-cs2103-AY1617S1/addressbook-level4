package seedu.todolist.model.task;

import seedu.todolist.commons.exceptions.IllegalValueException;

//@@author A0153736B
/**
 * Represents a Task's remarks parameter in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidRemarksParameter(String)}
 */
public class Remarks {
	public static final String MESSAGE_REMARKS_PARAMETER_CONSTRAINTS = "Task's remarks parameter should be spaces or alphanumeric characters";
    public static final String REMARKS_PARAMETER_VALIDATION_REGEX = "[\\p{Alnum} ]+";

    public final String remarks;
    
    /**
     * Validates given remarks parameter.
     *
     * @throws IllegalValueException if given remarks parameter string is invalid.
     */
    public Remarks(String remarks) throws IllegalValueException {
        if (remarks != null) {
            String trimmedRemarks = remarks.trim();
            if (!isValidRemarksParameter(trimmedRemarks)) {
                throw new IllegalValueException(MESSAGE_REMARKS_PARAMETER_CONSTRAINTS);
            }
            this.remarks = trimmedRemarks;
        }
        else {
        	this.remarks = remarks;
        }
    }

    /**
     * Returns true if a given string is a valid task's remarks parameter.
     */
    public static boolean isValidRemarksParameter(String test) {
        return test.matches(REMARKS_PARAMETER_VALIDATION_REGEX);
    }


    @Override
    public String toString() {
        return remarks;
    }
}
