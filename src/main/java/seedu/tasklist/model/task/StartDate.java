package seedu.tasklist.model.task;

import seedu.tasklist.commons.exceptions.IllegalValueException;

/**
 * Represents a Task's start date in the task list.
 * Guarantees: immutable; is valid as declared in {@link #isValidStartDate(String)}
 */
public class StartDate {

    public static final String MESSAGE_STARTDATE_CONSTRAINTS = "StartDate should be digits only";
    public static final String DATE_VALIDATION_REGEX = "^(?:\\d+|)$";

    public final String startDate;

    /**
     * Validates given start date.
     *
     * @throws IllegalValueException if given startDate string is invalid.
     */
    public StartDate(String startDate) throws IllegalValueException {
        assert startDate != null;
        startDate = startDate.trim();
        if (!isValidStartDate(startDate)) {
            throw new IllegalValueException(MESSAGE_STARTDATE_CONSTRAINTS);
        }
        this.startDate = startDate;
    }

    /**
     * Returns true if a given string is a valid start date.
     */
    public static boolean isValidStartDate(String test) {
        return test.matches(DATE_VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return startDate;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof StartDate // instanceof handles nulls
                && this.startDate.equals(((StartDate) other).startDate)); // state check
    }

    @Override
    public int hashCode() {
        return startDate.hashCode();
    }

}
