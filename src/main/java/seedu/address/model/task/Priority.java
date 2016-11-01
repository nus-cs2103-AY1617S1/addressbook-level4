package seedu.address.model.task;

import seedu.address.commons.exceptions.IllegalValueException;
//@@author A0138717X
public class Priority {
	public static final String MESSAGE_INVALID_PRIORITY_LEVEL = "Priority level must be of value 1, 2 or 3";

	public final int priorityLevel;

	public Priority(int priorityLevel) throws IllegalValueException {
		if(!isValidPriorityLevel(priorityLevel))
			throw new IllegalValueException(MESSAGE_INVALID_PRIORITY_LEVEL);
		this.priorityLevel = priorityLevel;
	}

	public static boolean isValidPriorityLevel(int priorityLevel) {
		if(priorityLevel <= 3 && priorityLevel >= 0)
			return true;
		return false;
	}

    public boolean isEmptyPriorityLevel() {
    	if(priorityLevel == 0)
    		return true;
    	return false;
    }

	@Override
    public String toString() {
        return String.valueOf(priorityLevel);
    }
}
