package seedu.task.model.task;


import seedu.task.commons.exceptions.IllegalValueException;

/**
 * Represents a Task end time in the task manager.
 * Guarantees: immutable; is valid as declared in {@link #isValidEndTime(String)}
 */
public class EndTime {
  //@@author A0147944U-reused
    public static final String MESSAGE_ENDTIME_CONSTRAINTS =
            "Task end times should be in hh.mmam or hh.mmpm format";
    public static final String ENDTIME_VALIDATION_REGEX = "((1[012])|((0)?[0-9])).[0-5][0-9](?i)(am|pm)?";
    //@@author
    public static final String NO_ENDTIME = "no end time";

    public final String value;

    /**
     * Validates given end time.
     *
     * @throws IllegalValueException if given end time string is invalid.
     */
    public EndTime(String endTime) throws IllegalValueException {
        assert endTime != null;
        endTime = endTime.trim();
        if(endTime.equals(NO_ENDTIME)) {
            this.value = endTime;
            return;
        }
        if (!isValidEndTime(endTime)) {
            throw new IllegalValueException(MESSAGE_ENDTIME_CONSTRAINTS);
        }
        this.value = endTime;
    }

    /**
     * Returns if a given string is a valid task end time.
     */
    public static boolean isValidEndTime(String test) {
        return test.matches(ENDTIME_VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof EndTime // instanceof handles nulls
                && this.value.equals(((EndTime) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
