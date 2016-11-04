package seedu.cmdo.model.task;


import seedu.cmdo.commons.exceptions.IllegalValueException;

//@@author A0141006B 
/**
 * Represents a Task's priority in the To Do List.
 * Guarantees: immutable; is valid as declared in {@link #isValidPriority(String)}
 */
public class Priority {
    
    public static final String MESSAGE_PRIORITY_CONSTRAINTS = "Task priority should /low, /medium or /high";
    public static final String HIGH = "high";
    public static final String MEDIUM = "medium";
    public static final String LOW = "low";
    public static final String NONE = "";

    public String value;

    /**
     * Creates a default priority object, which has value "low"
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
     */
    public static boolean isValidPriority(String testString) {
    	String test = testString.toLowerCase();
    	if (test.equals(HIGH) || test.equals((MEDIUM)) || test.equals(LOW) || test.equals(NONE))
    		return true;
    	return false;
    }
    
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

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}