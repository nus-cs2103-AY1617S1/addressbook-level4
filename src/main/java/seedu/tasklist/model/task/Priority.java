/* @@author A0135769N */
package seedu.tasklist.model.task;

import seedu.tasklist.commons.exceptions.IllegalValueException;

/**
 * Represents a Person's name in the address book. Guarantees: immutable; is
 * valid as declared in {@link #isValidName(String)}
 */
public class Priority {
    public static final String MESSAGE_NAME_CONSTRAINTS = "The priority should only be specified as 'high', 'med' or 'low'.";
    public static final String PRIORITY_VALIDATION_REGEX = "\\b(high)|(low)|(med)\\b";

    public final String priorityLevel;

    public Priority(String priority) throws IllegalValueException {
        if (priority != null){
            priority = priority.trim().toLowerCase();
        	if (!isPriorityLevel(priority)) {
        		throw new IllegalValueException(MESSAGE_NAME_CONSTRAINTS);
	        }
	        this.priorityLevel = priority;
    	}
        else {
        	this.priorityLevel = "low";
        }
        
    }
    
    public static boolean isPriorityLevel(String test) {
            return test.matches(PRIORITY_VALIDATION_REGEX);
    }

    public String toString() {
        return priorityLevel;
    }

    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Priority // instanceof handles nulls
                && this.priorityLevel.equals(((Priority) other).priorityLevel)); // state check
    }

    @Override
    public int hashCode() {
        return priorityLevel.hashCode();
    }

    public int compareTo(Priority priority) {
        if (this.priorityLevel.equals(priority.priorityLevel)) return 0;
        else if (this.priorityLevel.equals("high")) return -1;
        else if (priority.priorityLevel.equals("high")) return 1;
        else if (this.priorityLevel.equals("med")) return -1;
        else return 1;
    }

}
