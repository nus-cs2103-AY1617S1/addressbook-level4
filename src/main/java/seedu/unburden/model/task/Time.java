package seedu.unburden.model.task;

import seedu.unburden.commons.exceptions.IllegalValueException;

/**
 * Represents a Task's time in the Task Manager.
 * Guarantees: immutable; is valid as declared in {@link #isValidTime(String)}
 * @@author A0143095H
 */
public class Time {

    public static final String MESSAGE_TIME_CONSTRAINTS = "Task time should be in the format XXYY where X represents the number of hours and Y represents the number of minutes";
    public static final String TIME_VALIDATION_REGEX = ".+";
    
    //\\[0-9]{2}[0-9]{2}

    public final String fullTime;

    /**
     * Validates given time.
     *
     * @throws IllegalValueException if given time string is invalid.
     */
    public Time(String time) throws IllegalValueException {
        assert time != null;
       // time = time.trim();
        /*if (!isValidTime(time)) {
            throw new IllegalValueException(MESSAGE_TIME_CONSTRAINTS);
        }
        */
        this.fullTime = time;
    }

    /**
     * Returns true if a given string is a valid time.
     */
    public static boolean isValidTime(String test) {
        return test.matches(TIME_VALIDATION_REGEX);
    }


    @Override
    public String toString() {
        return fullTime;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Time // instanceof handles nulls
                && this.fullTime.equals(((Time) other).fullTime)); // state check
    }

    @Override
    public int hashCode() {
        return fullTime.hashCode();
    }

}
