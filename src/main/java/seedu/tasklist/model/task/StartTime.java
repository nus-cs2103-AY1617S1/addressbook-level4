package seedu.tasklist.model.task;

import seedu.tasklist.commons.exceptions.IllegalValueException;

/**
 * Represents the start time of a task.
 * Guarantees: immutable; is valid as declared in {@link #isValidStartTime(String)}
 */
public class StartTime {

    public static final String MESSAGE_START_TIME_CONSTRAINTS = "Start time should only be entered in 24 hrs format or 12 hrs format.";
    public static final String START_TIME_VALIDATION_REGEX = ".*";

    public final String value;

    /**
     * Validates given start time.
     *
     * @throws IllegalValueException if given start time is invalid.
     */
    public StartTime(String startTime) throws IllegalValueException {
     //  assert phone != null;
    	
        if (!isValidStartTime(startTime)) {
            throw new IllegalValueException(MESSAGE_START_TIME_CONSTRAINTS);
        }

        this.value = startTime;
    }

    /**
     * Returns true if a given string is a valid start time.
     */
    public static boolean isValidStartTime(String test) {
    	if(test==null){
    		return true;
    	}
    	return test.matches(START_TIME_VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (this.value != null && ((StartTime) other).value != null ) && (other instanceof StartTime // instanceof handles nulls
                && this.value.equals(((StartTime) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
