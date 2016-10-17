package seedu.todoList.model.task.attributes;

import seedu.todoList.commons.exceptions.IllegalValueException;

public class Priority {

    public static final String MESSAGE_PRIORITY_CONSTRAINTS = "Priority should only contain numbers";
    public static final String PRIORITY_VALIDATION_REGEX = "\\d+";
    
    public final int priority;
    
	public Priority(String priority) throws IllegalValueException {
	    assert priority != null;
	    priority = priority.trim();
        if (!isValidPriority(priority)) {
            throw new IllegalValueException(MESSAGE_PRIORITY_CONSTRAINTS);
        }
	    this.priority = Integer.parseInt(priority);
	}
    
	/**
     * Returns true if a given string is a valid priority value.
     */
    public static boolean isValidPriority(String test) {
        return test.matches(PRIORITY_VALIDATION_REGEX);
    }
    
	
	@Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Priority // instanceof handles nulls
                && this.priority == (((Priority) other).priority)); // state check
    }

    @Override
    public int hashCode() {
        return Integer.toString(priority).hashCode();
    }

}
