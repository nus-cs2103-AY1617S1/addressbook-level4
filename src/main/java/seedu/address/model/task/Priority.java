package seedu.address.model.task;

import seedu.address.commons.exceptions.IllegalValueException;

/**
 * Represents a Person's name in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidName(String)}
 */
public class Priority {
    public static final String MESSAGE_NAME_CONSTRAINTS = "Priority should be 'high', 'low' or 'mid' only.";
    public static final String PRIORITY_VALIDATION_REGEX= "\\b(high)|(low)|(mid)\\b";

    public final String priorityLevel;
    
    public Priority(String priority) throws IllegalValueException {
        if(priority != null)
           priority = priority.trim();
        if (!isPriorityLevel(priority)) {
            throw new IllegalValueException(MESSAGE_NAME_CONSTRAINTS);
        }
        this.priorityLevel = priority;
    }
    
    public static boolean isPriorityLevel(String test) {
        if(test!=null){
           test = test.toLowerCase();
           return test.matches(PRIORITY_VALIDATION_REGEX);
        }
        return false;
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

}