package seedu.tasklist.model.task;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import seedu.tasklist.commons.exceptions.IllegalValueException;

/**
 * Represents a Task's time in the task list. Guarantees: immutable; is valid as
 * declared in {@link #isValidTime(String)}
 */
public class Time {

    private static final String MESSAGE_TIME_CONSTRAINTS = "Invalid Time format\n24-hour Time format: HHMM";
    private static final String TIME_VALIDATION_REGEX = "^(?:[0-9 ]+|)$";

    private final LocalTime time;

    /**
     * Validates given time.
     *
     * @throws IllegalValueException
     *             if given time string is invalid.
     */
    public Time(String time) throws IllegalValueException {
        assert time != null;
        if (!isValidTime(time)) {
            throw new IllegalValueException(MESSAGE_TIME_CONSTRAINTS);
        }

        if (!time.isEmpty() && time.length() == 4) {
            this.time = LocalTime.of(Integer.parseInt(time.substring(0, 2)), Integer.parseInt(time.substring(2, 4)));
        } else {
            this.time = null;
        }
    }

    /**
     * Returns true if a given string is a valid time.
     */
    public static boolean isValidTime(String test) {
        return test.matches(TIME_VALIDATION_REGEX);
    }

    public LocalTime getTime() {
        return time;
    }

    @Override
    public String toString() {
        if (time != null) {
            DateTimeFormatter df = DateTimeFormatter.ofPattern("HH:mm");
            return df.format(time);
        } else {
            return "";
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Time // instanceof handles nulls
                        && this.time.equals(((Time) other).time)); // state
                                                                   // check
    }

    @Override
    public int hashCode() {
        return time.hashCode();
    }
    
}
