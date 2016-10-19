package seedu.unburden.model.task;

import seedu.unburden.commons.exceptions.IllegalValueException;

/**
 * 
 * @author Nat Represents a task description in the task manager Guarantees that
 *         the details are immutable
 */

public class Details {
	private static final String MESSAGE_NAME_CONSTRAINTS = "Task details should be spaces or alphanumeric characters.";
	public static final String NAME_VALIDATION_REGEX = "[\\p{Alnum} ]+";
	public final String fullDetails;

	/**
	 * 
	 * @param details
	 *            Validates the given String as a task description
	 * @throws IllegalValueException
	 *             if the string passed in is invalid
	 */
	public Details(String details) throws IllegalValueException {
		assert details != null;
		details = details.trim();
		if (isValidDetails(details)) {
			throw new IllegalValueException(MESSAGE_NAME_CONSTRAINTS);
		}
		this.fullDetails = details;
	}

	private boolean isValidDetails(String details) {
		return details.matches(NAME_VALIDATION_REGEX);
	}

	@Override
	public String toString() {
		return fullDetails;
	}

	@Override
	public boolean equals(Object other) {
		return other == this // short circuit if same object
				|| (other instanceof Details // instanceof handles nulls
						&& this.fullDetails.equals(((Details) other).fullDetails)); 
	}

	@Override
	public int hashCode() {
		return fullDetails.hashCode();
	}

}
