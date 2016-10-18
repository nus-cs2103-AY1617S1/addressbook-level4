package tars.model.task;

import tars.commons.exceptions.IllegalValueException;

/**
 * Represents a Task's priority in tars.
 */
public class Priority implements Comparable<Priority> {
	public static final String MESSAGE_PRIORITY_CONSTRAINTS = "Task priority should be h / m / l";
	public static final String PRIORITY_VALIDATION_REGEX = "^[hml]$";

	public String priorityLevel;
	public int level;

	/**
	 * Validates given task priority level.
	 *
	 * @throws IllegalValueException
	 *             if given priority level string is invalid.
	 */
	public Priority(String priorityLevel) throws IllegalValueException {
		assert priorityLevel != null;
		priorityLevel = priorityLevel.trim();
		if (!isValidPriorityLevel(priorityLevel)) {
			throw new IllegalValueException(MESSAGE_PRIORITY_CONSTRAINTS);
		}
		this.priorityLevel = priorityLevel;
		
		if (this.priorityLevel.equals("l"))
			level = 1;
		else if (this.priorityLevel.equals("m"))
			level = 2;
		else
			level = 3;
	}

	/**
	 * Returns true if a given string is a valid task priority level.
	 */
	public static boolean isValidPriorityLevel(String level) {
		return level.equals("") ? true : level.matches(PRIORITY_VALIDATION_REGEX);
	}

	@Override
	public String toString() {
		return priorityLevel;
	}
	
	@Override
	public boolean equals(Object other) {
		return other == this // short circuit if same object
				|| (other instanceof Priority // instanceof handles nulls
						&& this.toString().equals(((Priority) other).toString())); // state
																					// check
	}

	@Override
	public int compareTo(Priority o) {
		if (this.level > o.level) {
			return 1;
		} else if (this.level < o.level) {
			return -1;
		} else {
			return 0;
		}
	}

}
