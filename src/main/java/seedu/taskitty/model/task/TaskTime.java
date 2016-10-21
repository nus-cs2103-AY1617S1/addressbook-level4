//@@author A0139930B
package seedu.taskitty.model.task;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import seedu.taskitty.commons.exceptions.IllegalValueException;

/**
 * Represents a Task's name in the task manager.
 * Guarantees: immutable; is valid as declared in {@link #isValidTime(String)}
 */
public class TaskTime {

    public static final String MESSAGE_TIME_CONSTRAINTS =
            "Task time should be in the format hh:mm";
    public static final String MESSAGE_TIME_INVALID =
            "Task time provided is invalid!";
    public static final String MESSAGE_TIME_MISSING =
            "Task time must be provided";
    public static final String TIME_FORMAT_STRING = "HH:mm";
    public static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern(TIME_FORMAT_STRING);
    
    //format: hh:mm
    private static final String TIME_VALIDATION_FORMAT = "[\\p{Digit}]{1,2}:[\\p{Digit}]{2}";

    private final LocalTime time;

    /**
     * Validates given time. The time should be parsed by Natty and
     * be in the format according to TIME_FORMAT_STRING
     *
     * @throws IllegalValueException if given time is invalid or null.
     */
    public TaskTime(String time) throws IllegalValueException {
        if (time == null) {
            throw new IllegalValueException(MESSAGE_TIME_MISSING);
        }
        
        time = time.trim();
        //This is not an assert because user can change the database and input wrong formats
        if (!isValidTimeFormat(time)) {
            throw new IllegalValueException(MESSAGE_TIME_CONSTRAINTS);
        }
        
        try {
            this.time = LocalTime.parse(time, TIME_FORMATTER);
        } catch (DateTimeParseException dtpe){
            throw new IllegalValueException(MESSAGE_TIME_INVALID);
        }
    }

    /**
     * Returns true if a given string is a valid person name.
     */
    public static boolean isValidTimeFormat(String test) {
        return test.matches(TIME_VALIDATION_FORMAT);
    }
    
    public boolean isBefore(TaskTime time) {
        return this.time.isBefore(time.getTime());
    }

    @Override
    public String toString() {
        return time.format(TIME_FORMATTER);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof TaskTime // instanceof handles nulls
                && this.time.equals(((TaskTime) other).time)); // state check
    }
    
    public static boolean isEquals(TaskTime left, TaskTime right) {
        if (left == null || right == null) {
            return false;
        }
        
        return left.equals(right);
    }

    @Override
    public int hashCode() {
        return time.hashCode();
    }
    
    public LocalTime getTime() {
        return time;
    }

}
