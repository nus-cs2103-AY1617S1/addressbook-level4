package seedu.tasklist.model.task;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

import seedu.tasklist.commons.exceptions.IllegalValueException;

/**
 * Represents a Task's time in the task list. Guarantees: immutable; is valid as
 * declared in {@link #isValidLocalTime(String)}
 */
public class Time {

    private static final String MESSAGE_TIME_CONSTRAINTS = "Invalid Time format\n24-hour Time format: HHMM";
    private static final String TIME_VALIDATION_REGEX = "^(?:[0-9 ]+|)$";

    private final LocalTime localTime;

    /**
     * Validates given time.
     *
     * @throws IllegalValueException
     *             if given time string is invalid.
     */
    public Time(String localTime) throws IllegalValueException {
        assert localTime != null;
        if (!isValidLocalTime(localTime)) {
            throw new IllegalValueException(MESSAGE_TIME_CONSTRAINTS);
        }

        if (!localTime.isEmpty() && localTime.length() == 4) {
            this.localTime = LocalTime.of(Integer.parseInt(localTime.substring(0, 2)), Integer.parseInt(localTime.substring(2, 4)));
        } else {
            this.localTime = null;
        }
    }

    /**
     * Returns true if a given string is a valid time.
     */
    public static boolean isValidLocalTime(String test) {
        return test.matches(TIME_VALIDATION_REGEX);
    }

    public LocalTime getLocalTime() {
        return localTime;
    }

    @Override
    public String toString() {
        if (localTime != null) {
            DateTimeFormatter df = DateTimeFormatter.ofPattern("HH:mm");
            return df.format(localTime);
        } else {
            return "";
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Time // instanceof handles nulls
                        && this.localTime.equals(((Time) other).localTime)); // state
                                                                   // check
    }

    @Override
    public int hashCode() {
        return localTime.hashCode();
    }
    
    //@@author A0153837X
    public String hoursFromNow(){
    	long hours = ChronoUnit.HOURS.between(LocalTime.now(), localTime);
    	
    	return Long.toString(hours);
    
    }
}
