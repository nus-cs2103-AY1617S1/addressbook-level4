package jym.manager.model.task;

import jym.manager.commons.exceptions.IllegalValueException;

/**
 * Represents a Person's phone number in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidPhone(String)}
 */
public class Priority {

    public static final String MESSAGE_PRIORITY_CONSTRAINTS = "Task priority should only contain numbers";
    public static final String PRIORITY_VALIDATION_REGEX = "\\d+";

    private final Integer value;

    /**
     * Validates given phone number.
     *
     * @throws IllegalValueException if given phone string is invalid.
     */
    public Priority(int priority) {
        this.value = priority;
    }
    public Priority(Priority other){
    	this.value = other.getValue();
    }
    //0 priority indicates there is no basically no priority value for the task
    public Priority(){
    	this.value = 0;
    }

    /**
     * Returns true if a given string is a valid person phone number.
     */
    public static boolean isValidPriority(String test) {
        return test.matches(PRIORITY_VALIDATION_REGEX);
    }
    public int getValue(){
    	return this.value;
    }
    @Override
    public String toString() {
        return value.toString();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Priority // instanceof handles nulls
                && this.value == (((Priority) other).value)); // state check
    }

  

}
