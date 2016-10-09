package seedu.taskman.model.event;

import seedu.taskman.commons.exceptions.IllegalValueException;

/**
 * Represents a Task's deadline in the task man.
 * Guarantees: immutable; is valid as declared in {@link #isValidDeadline(String)}
 */
public class Deadline {

    public static final String MESSAGE_DEADLINE_CONSTRAINTS = "Task deadline should only contain dates and times in the format: [this/next] tdy/tmr/mon/tue/wed/thu/fri/sat/sun HHMM.";
    public static final String DEADLINE_VALIDATION_REGEX = "((tdy|tmr)|(((this)|(next))?\\s(mon|tue|wed|thu|fri|sat|sun)))\\s(((0|1)[0-9])|(2[0-3]))([0-5][0-9])";
    // ((tdy|tmr)|(((this)|(next))?\s(mon|tue|wed|thu|fri|sat|sun)))\s(((0|1)[0-9])|(2[0-3]))([0-5][0-9])
    // Examples: tdy 2359, tmr 0000, mon 0400, this tue 1600, next thu 2200

    public final String value;

    /**
     * Validates given deadline string.
     *
     * @throws IllegalValueException if given deadline string is invalid.
     */
    public Deadline(String deadline) throws IllegalValueException {
        // assert deadline != null; // commented out since deadline is optional
        deadline = deadline.trim();
        if (deadline.isEmpty()) {
        	deadline = "NIL";
        } else if (!isValidDeadline(deadline)) {
            throw new IllegalValueException(MESSAGE_DEADLINE_CONSTRAINTS);
        }
        this.value = deadline;
    }

    /**
     * Returns true if a given string is a valid deadline.
     */
    public static boolean isValidDeadline(String test) {
        // TODO: improve on validation, see above
        return test.matches(DEADLINE_VALIDATION_REGEX);
    }

    @Override
    public String toString() {
    	// TODO: check if direct return of original input without processing is OK
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Deadline // instanceof handles nulls
                && this.value.equals(((Deadline) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
