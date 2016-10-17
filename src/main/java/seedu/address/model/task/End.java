package seedu.address.model.task;


import seedu.address.commons.exceptions.IllegalValueException;

/**
 * Represents a task's end time in Simply
 * Guarantees: immutable; is valid as declared in {@link #isValidEnd(String)}
 */
public class End {
    
    public static final String MESSAGE_END_CONSTRAINTS = "Task end time can be entered in 24hour or 12hour format.";
    public static final String END_VALIDATION_REGEX = "([012]\\d{1}[0-5]\\d{1})|" +
    												  "([1-9](?:pm|am|PM|AM))|" + 
    												  "(1[0-2](?:pm|am|PM|AM))|" +
    												  "([1-9]\\.[0-5]{1}\\d{1}(?:pm|am))|" +
    												  "(1[0-2]\\.[0-5]{1}\\d{1}(?:pm|am))";
    public static final String DEFAULT_END_TIME = "2359";
    public final String value;

    /**
     * Validates given end time.
     *
     * @throws IllegalValueException if given address string is invalid.
     */
    public End(String end) throws IllegalValueException {
        //assert end != null;
    	if (end == null) 
    		end = "default";
        if (!isValidEnd(end)) {
            throw new IllegalValueException(MESSAGE_END_CONSTRAINTS);
        }
        if (end == "default")
        	this.value = DEFAULT_END_TIME;
        else
        	this.value = end;
    }

    /**
     * Returns true if a given string is a valid person email.
     */
    public static boolean isValidEnd(String test) {
    	if (test == "" || test.matches(END_VALIDATION_REGEX) || test == "default")
    		return true;
    	else
    		return false;
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof End // instanceof handles nulls
                && this.value.equals(((End) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}