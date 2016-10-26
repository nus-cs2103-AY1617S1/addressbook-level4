package seedu.ggist.model.task;


import java.util.regex.Matcher;

import seedu.ggist.commons.core.Messages;
import seedu.ggist.commons.exceptions.IllegalValueException;

/**
 * Represents a Task's time in GGist 
 * Guarantees: immutable; is valid as declared in {@TaskTime #isValidTime(String)}
 */
public class TaskTime {
    

    public static final String MESSAGE_TIME_CONSTRAINTS =
            "Only accepts time in 24hr format [hhmm] or 12hr format [hh.mm am/pm]\n"
            + "Example: 2359 or 11.59pm\n";
    public static final String TIME_VALIDATION_REGEX = ".+";
    public String value;
  //@@author A0138411N
    /**
     * Validates given time.
     *
     * @throws IllegalValueException if given time string is invalid.
     */
    public TaskTime(String time) throws IllegalValueException {
        assert time != null;
        time = time.trim();
        if (!time.equals(Messages.MESSAGE_NO_START_TIME_SET) && !time.equals(Messages.MESSAGE_NO_END_TIME_SET)
             && !isValidTime(time)) {
            throw new IllegalValueException(MESSAGE_TIME_CONSTRAINTS);
        }
        this.value = time;
    }
  //@@author  
    /**
     * Changes the value attribute
     * @param String
     * @throws IllegalValueException
     */
    public void editTime(String newTime) throws IllegalValueException {
        assert newTime != null;
        newTime = newTime.trim();
        if (!isValidTime(newTime)) {
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
                || (other instanceof TaskTime // instance of handles nulls
                && this.value.equals(((TaskTime) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
