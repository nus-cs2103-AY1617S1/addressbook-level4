package seedu.unburden.model.task;

import seedu.unburden.commons.exceptions.IllegalValueException;

/**
 * Represents a Task's description in the task manager.
 * Guarantees: immutable; is valid as declared in {@link #isValidDescription(String)}
 * @@author A0143095H
 */

//@@Gauri Joshi A0143095H
public class TaskDescription {
	private static final String MESSAGE_TASK_CONSTRAINTS = "Task descriptions should be spaces or alphanumeric characters.";
	public static final String TASK_VALIDATION_REGEX = "[\\p{Alnum} ]+";
	
	public final String fullTaskDescriptions;

	/**
	 * 
	 * @param details
	 *            Validates the given String as a task description
	 * @throws IllegalValueException
	 *             if the string passed in is invalid
	 */
	public TaskDescription(String details) throws IllegalValueException {
		/*
		assert details != null;
		details = details.trim();
		if (isValidDetails(details)) {
			throw new IllegalValueException(MESSAGE_TASK_CONSTRAINTS);
		}
		*/
		this.fullTaskDescriptions = details;
	}

	private boolean isValidDetails(String details) {
		return details.matches(TASK_VALIDATION_REGEX);
	}

	@Override
	public String toString() {
		return fullTaskDescriptions;
	}

	@Override
	public boolean equals(Object other) {
		return other == this // short circuit if same object
				|| (other instanceof TaskDescription // instanceof handles nulls
						&& this.fullTaskDescriptions.equals(((TaskDescription) other).fullTaskDescriptions)); 
	}

	@Override
	public int hashCode() {
		return fullTaskDescriptions.hashCode();
	}

}
