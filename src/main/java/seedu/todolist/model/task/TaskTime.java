package seedu.todolist.model.task;

import java.time.DateTimeException;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import seedu.todolist.commons.exceptions.IllegalValueException;
import seedu.todolist.model.parser.TimeParser;

//@@author A0138601M
/**
 * Represents a Task's time in the to do list.
 * Guarantees: immutable; is valid as declared in {@link #isValidTime(String)}
 */
public class TaskTime {
    
    public static final String MESSAGE_TIME_CONSTRAINTS = "Task time should be in 24-hr format or AM/PM format";
    public static final String MESSAGE_TIME_INVALID = "Task time provided is invalid!";

    //format: 24-hr
    public static final String TIME_VALIDATION_REGEX_2 = "(\\p{Digit}){1,2}:(\\p{Digit}){2}";
    //format: AM/PM
    //minutes can be omitted and assumed to be 00
    public static final String TIME_VALIDATION_REGEX_1 = "(\\p{Digit}){1,2}(:(\\p{Digit}){2})?\\s?[AaPp][Mm]";
    public static final String TIME_VALIDATION_REGEX_FORMAT = TIME_VALIDATION_REGEX_1 + "|" + TIME_VALIDATION_REGEX_2;

    public static final String TIME_DISPLAY_FORMAT = "hh:mma";

    public final LocalTime time;
    
    /**
     * Validates given time.
     *
     * @throws IllegalValueException if given date is invalid.
     */
    public TaskTime(String time) throws IllegalValueException {
        assert time != null;
        time = time.trim();
        if (!isValidTime(time)) {
            throw new IllegalValueException(MESSAGE_TIME_CONSTRAINTS);
        }
        
        try {
            this.time = TimeParser.parseTime(time);
        } catch (DateTimeException dateTimeException) {
            throw new IllegalValueException(MESSAGE_TIME_INVALID);
        }
    }
    
    /**
     * Returns true if a given string is a valid task time.
     */
    public static boolean isValidTime(String test) {
        if (test.matches(TIME_VALIDATION_REGEX_FORMAT)) {
            return true;
        }
        return false;
    }
    
    /**
     * Returns true if this time is earlier than given time.
     */
    public boolean isBefore(TaskTime other) {
        return this.time.isBefore(other.getTime());
    }

    public LocalTime getTime() {
        return this.time;
    }
    
    @Override
    public String toString() {
        if (this.time != null) {
            return time.format(DateTimeFormatter.ofPattern(TIME_DISPLAY_FORMAT));
        }
        return null;
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
