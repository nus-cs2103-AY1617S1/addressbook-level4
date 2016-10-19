package seedu.taskitty.model.task;

import java.time.DateTimeException;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import seedu.taskitty.commons.exceptions.IllegalValueException;
import seedu.taskitty.commons.util.TimeUtil;

/**
 * Represents a Task's name in the task manager.
 * Guarantees: immutable; is valid as declared in {@link #isValidName(String)}
 */
public class TaskTime {

    //TODO add more ways to type the time. eg. am/pm
    public static final String MESSAGE_TIME_CONSTRAINTS =
            "Task times should be in the format hh:mm"; //or hhmm
    public static final String MESSAGE_TIME_INVALID =
            "Task time provided is invalid!";
    public static final String MESSAGE_TIME_MISSING =
            "Task time must be provided";
    public static final String TIME_FORMAT_STRING = "HH:mm";
    public static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern(TIME_FORMAT_STRING);
    
    //format: hh:mm
    private static final String TIME_VALIDATION_FORMAT = "[\\p{Digit}]{1,2}:[\\p{Digit}]{2}";

    public final LocalTime time;

    /**
     * Validates given name.
     *
     * @throws IllegalValueException if given name string is invalid.
     */
    public TaskTime(String time) throws IllegalValueException {
        if (time == null) {
            throw new IllegalValueException(MESSAGE_TIME_MISSING);
        }
        
        time = time.trim();
        if (!isValidName(time)) {
            throw new IllegalValueException(MESSAGE_TIME_CONSTRAINTS);
        }
        
        this.time = LocalTime.parse(time, TIME_FORMATTER);
    }

    /**
     * Returns true if a given string is a valid person name.
     */
    public static boolean isValidName(String test) {
        return test.matches(TIME_VALIDATION_FORMAT);
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

    @Override
    public int hashCode() {
        return time.hashCode();
    }

}
