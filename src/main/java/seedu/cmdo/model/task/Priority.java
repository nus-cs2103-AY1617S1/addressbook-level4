package seedu.cmdo.model.task;


import seedu.cmdo.commons.exceptions.IllegalValueException;

/**
 * Represents a Task's priority in the To Do List.
 * Guarantees: immutable; is valid as declared in {@link #isValidPriority(String)}
 */
public class Priority {
    
    public static final String MESSAGE_PRIORITY_CONSTRAINTS = "Task priority should /low, /medium or /high";
//    public static final String PRIORITY_VALIDATION_REGEX = "(high|medium|low)";
    
    public static final String HIGH = "high";
    public static final String MEDIUM = "medium";
    public static final String LOW = "low";
    public static final String NONE = "";

    public String value;

    /**
     * Creates a default priority object, which has value "low"
     * 
     * @author A0139661Y
     */
    public Priority() {
    	value = NONE;
    }
    
    /**
     * Validates given priority.
     *
     * @throws IllegalValueException if given address string is invalid.
     */
    public Priority(String priority) throws IllegalValueException {
        assert priority != null;
        if (!isValidPriority(priority)) {
            throw new IllegalValueException(MESSAGE_PRIORITY_CONSTRAINTS);
        }
        // Test-case manual add
        if (!priority.equals("")) {
        	this.value = priority.toLowerCase();
        } else this.value = NONE;
    }

    /**
     * Returns true if a given string is a valid priority.
     * 
     * @author A0139661Y
     */
    public static boolean isValidPriority(String testString) {
//        return test.matches(PRIORITY_VALIDATION_REGEX);
    	String test = testString.toLowerCase();
    	if (test.equals(HIGH) || test.equals((MEDIUM)) || test.equals(LOW) || test.equals(NONE))
    		return true;
    	return false;
    }
    
    //@@author A0141128R
    //getter to get value 
    public String getValue(){
    	return value;
    }
    //setter for priority
    public void setPriority(String value){
    	this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }
    /*
    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Priority // instanceof handles nulls
                && this.value.equals(((Priority) other).value)); // state check
    }
    */

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}