package seedu.todoList.model.task.attributes;

import seedu.todoList.commons.exceptions.IllegalValueException;

public class Priority {

    public static final String MESSAGE_PRIORITY_CONSTRAINTS = "Priority should only contain 1, 2 or 3\n"
                                                               + "1 is HIGH, 2 is MEDIUM , 3 is LOW";
    public static final String PRIORITY_VALIDATION_REGEX = "^[1-3]$";
    
    public final String priority;
    public String level = "HIGH";
    public final String savePriority;
    
	public Priority(String priority) throws IllegalValueException {
	    assert priority != null;
	    priority = priority.trim();
        savePriority = priority.trim();
	    if (!isValidPriority(priority)) {
            throw new IllegalValueException(MESSAGE_PRIORITY_CONSTRAINTS);
        }
        
        switch(Integer.parseInt(priority)){
            case 1: level = "HIGH"; break;
            case 2: level = "MEDIUM"; break;
            case 3: level = "LOW"; break;
        }
	    this.priority = level;
	}
    
	/**
     * Returns true if a given string is a valid priority value.
     */
    public static boolean isValidPriority(String test) {
        return test.matches(PRIORITY_VALIDATION_REGEX);
    }
    
	@Override
    public String toString() {
        return priority;
    }
	
	@Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Priority // instanceof handles nulls
                && this.priority == (((Priority) other).priority)); // state check
    }

    @Override
    public int hashCode() {
        return priority.hashCode();
    }

}
