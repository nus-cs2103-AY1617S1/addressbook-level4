package seedu.todoList.model.task.attributes;

import seedu.todoList.commons.exceptions.IllegalValueException;

/**
 * Represents a Event's or Deadline's end time in the TodoList.
 * Guarantees: immutable; is valid as declared in {@link #isValidEndTime(String)}
 */
public class EndTime {

    public static final String MESSAGE_ENDTIME_CONSTRAINTS =
            "Event or Deadline End Time should be written in this format, must be 4 digits '1000'";
    public static final String ENDTIME_VALIDATION_REGEX = "^(\\d{4})$";
    
    public final String endTime;
    
    /**
     * Validates given end time.
     *
     * @throws IllegalValueException if given end time is invalid.
     */
    public EndTime(String endTime) throws IllegalValueException {
        assert endTime != null;
        endTime = endTime.trim();
        if (!isValidEndTime(endTime)) {
            throw new IllegalValueException(MESSAGE_ENDTIME_CONSTRAINTS);
        }
        this.endTime = endTime;
    }
    
    /**
     * Returns if a given string is a valid event or deadline end time.
     */
    public static boolean isValidEndTime(String endtime) {
        return endtime.matches(ENDTIME_VALIDATION_REGEX);
    }
    
    
    @Override
    public String toString() {
        return endTime;
    }
    
    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof EndTime // instanceof handles nulls
                && this.endTime.equals(((EndTime) other).endTime)); // state check
    }

    @Override
    public int hashCode() {
        return endTime.hashCode();
    }

}
