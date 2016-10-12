package seedu.task.model.person;

import seedu.task.commons.exceptions.IllegalValueException;

/**
 * Represents a Task start time in the task manager.
 * Guarantees: immutable; is valid as declared in {@link #isValidStartTime(String)}
 */
public class StartTime {

    public static final String MESSAGE_STARTTIME_CONSTRAINTS = "Task start times should be in hhmm hrs format";
    public static final String STARTTIME_VALIDATION_REGEX = "^([0-9]{4})+(hrs)";

    public final String value;

    /**
     * Validates given start time.
     *
     * @throws IllegalValueException if given start time string is invalid.
     */
    public StartTime(String startTime) throws IllegalValueException {
        assert startTime != null;
        startTime = startTime.trim();
        if (!isValidStartTime(startTime)) {
            throw new IllegalValueException(MESSAGE_STARTTIME_CONSTRAINTS);
        }
        this.value = startTime;
    }

    /**
     * Returns true if a given string is a valid task start time.
     */
    public static boolean isValidStartTime(String test) {
        return test.matches(STARTTIME_VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof StartTime // instanceof handles nulls
                && this.value.equals(((StartTime) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
