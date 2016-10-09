package seedu.taskman.model.event;

import seedu.taskman.commons.exceptions.IllegalValueException;
import seedu.taskman.model.Regex;

/**
 * Represents a deadline in task man.
 * Guarantees: immutable; is valid as declared in {@link #isValidDeadline(String)}
 */
public class Deadline {

    public static final String MESSAGE_DEADLINE_CONSTRAINTS =
            "Deadline should only contain dates and times in the format: " + Regex.DESCRIPTION_DATE_TIME_TYPIST_FRIENDLY;
    public static final String DEADLINE_VALIDATION_REGEX =
            "^" + Regex.DATE_TIME_TYPIST_FRIENDLY + "$";
    // todo: add regex for other time formats, eg: DDMMYYYY:TTTT

    public final String value;
    // todo: change to unix time

    /**
     * Validates given deadline string.
     *
     * @throws IllegalValueException if given deadline string is invalid.
     */
    public Deadline(String deadline) throws IllegalValueException {
        // assert deadline != null; // commented out since deadline is optional
        deadline = deadline.trim();
        if (!isValidDeadline(deadline)) {
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
        return value;
    }

    // todo: add relative time from now, eg: deadline in xxx seconds

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
