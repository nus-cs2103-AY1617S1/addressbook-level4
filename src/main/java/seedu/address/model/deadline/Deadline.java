package seedu.address.model.deadline;

import seedu.address.model.task.Startline;

import seedu.address.commons.exceptions.IllegalValueException;

/**
 * Represents a Task's deadline in the task manager.
 * Guarantees: immutable; is valid as declared in {@link #isValidDeadline(String)}
 */
public class Deadline extends Startline {
    
    public static final String MESSAGE_ADDRESS_CONSTRAINTS = "Task deadline must be in ddmmyy or dd-MM-yy format.";
    public static final String DEADLINE_VALIDATION_REGEX = "\\d+";
    public static final String DEADLINE_DASH_VALIDATION_REGEX = "[\\d]+-[\\d]+-[\\d]+";

	public String deadlineDate;

    /**
     * Validates given deadline.
     *
     * @throws IllegalValueException if given date string is invalid.
     */
    public Deadline(String deadline) throws IllegalValueException {
    	super(deadline);
    }

}