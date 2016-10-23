package seedu.task.model.task;


import seedu.task.commons.exceptions.IllegalValueException;

/**
 * Represents a Task deadline in the task manager.
 * Guarantees: immutable; is valid as declared in {@link #isValidLocation(String)}
 */
public class Deadline {
    //@@author A0147944U-reused
    public static final String MESSAGE_DEADLINE_CONSTRAINTS = "Task deadline should be in hh.mmam or hh.mmpm format";
    public static final String DEADLINE_VALIDATION_REGEX = "((1[012])|((0)?[0-9])).[0-5][0-9](?i)(am|pm)?";
    //@@author
    public static final String NO_DEADLINE = "no deadline";
    public final String value;


    /**
     * Validates given location.
     *
     * @throws IllegalValueException if given deadline string is invalid.
     */
    public Deadline(String deadline) throws IllegalValueException {
        assert deadline != null;
        deadline = deadline.trim();
        if(deadline.equals(NO_DEADLINE)) {
            this.value = deadline;
            return;
        }
        if (!isValidDeadline(deadline)) {
            throw new IllegalValueException(MESSAGE_DEADLINE_CONSTRAINTS);
        }
        this.value = deadline;
    }

    /**
     * Returns true if a given string is a valid task deadline.
     */
    public static boolean isValidDeadline(String test) {
        return test.matches(DEADLINE_VALIDATION_REGEX);
    }

    @Override
    public String toString() {
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