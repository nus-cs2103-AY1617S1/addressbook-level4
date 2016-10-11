package seedu.ggist.model.task;


import java.util.regex.Matcher;

import seedu.ggist.commons.core.Messages;
import seedu.ggist.commons.exceptions.IllegalValueException;

/**
 * Represents a Task's time in GGist 
 * Guarantees: immutable; is valid as declared in {@Time #isValidTime(String)}
 */
public class Time {
    

    public static final String MESSAGE_TIME_CONSTRAINTS =
            "TIME 24-hour format is HHMM";
    public static final String TIME_VALIDATION_REGEX = "([01]?[0-9]|2[0-3])([0-5][0-9])";
    public String value;

    /**
     * Validates given time.
     *
     * @throws IllegalValueException if given time string is invalid.
     */
    public Time(String time) throws IllegalValueException {
        assert time != null;
        time = time.trim();
        if (!time.equals(Messages.MESSAGE_NO_START_TIME_SET) && !time.equals(Messages.MESSAGE_NO_END_TIME_SET)
             && !isValidTime(time)) {
            throw new IllegalValueException(MESSAGE_TIME_CONSTRAINTS);
        }
        this.value = time;
    }
    
    public void editTime(String newTime) throws IllegalValueException {
        assert newTime != null;
        newTime = newTime.trim();
        if (!newTime.equals(Messages.MESSAGE_NO_START_TIME_SET) && !newTime.equals(Messages.MESSAGE_NO_END_TIME_SET)
             && !isValidTime(newTime)) {
            throw new IllegalValueException(MESSAGE_TIME_CONSTRAINTS);
        }
        this.value = newTime;
    }
    /**
     * Returns if a given string is a valid time format.
     */
    public static boolean isValidTime(String test) {
    	return test.matches(TIME_VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Time // instance of handles nulls
                && this.value.equals(((Time) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
