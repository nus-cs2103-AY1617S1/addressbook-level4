package seedu.todoList.model.task.attributes;

import seedu.todoList.commons.exceptions.IllegalValueException;

/**
 * Represents a Event's start time in the TodoList.
 * Guarantees: immutable; is valid as declared in {@link #isValidStartTime(String)}
 */
public class StartTime {

    public static final String MESSAGE_STARTTIME_CONSTRAINTS =
            "Event Start time should be written in this format, must be 4 digits '1000'";
    public static final String STARTTIME_VALIDATION_REGEX = "^(\\d{4})$";
    
    public final String startTime;
    
    /**
     * Validates given start time.
     *
     * @throws IllegalValueException if given start time is invalid.
     */
    public StartTime(String startTime) throws IllegalValueException {
        assert startTime != null;
        startTime = startTime.trim();
        if (!isValidStartTime(startTime)) {
            throw new IllegalValueException(MESSAGE_STARTTIME_CONSTRAINTS);
        }
        this.startTime = startTime;
    }
    
    /**
     * Returns if a given string is a valid event start time.
     */
    public static boolean isValidStartTime(String starttime) {
        return starttime.matches(STARTTIME_VALIDATION_REGEX);
    }
    
    
    @Override
    public String toString() {
        return startTime;
    }
    
    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof StartTime // instanceof handles nulls
                && this.startTime.equals(((StartTime) other).startTime)); // state check
    }

    @Override
    public int hashCode() {
        return startTime.hashCode();
    }

}
