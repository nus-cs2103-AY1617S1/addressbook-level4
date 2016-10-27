package seedu.address.model.activity.task;

//@@author A0125680H
import seedu.address.commons.exceptions.IllegalValueException;

/**
 * Represents a Task's priority value in the Lifekeeper.
 * Guarantees: immutable; is valid as declared in {@link #isValidPriority(String)}
 */
public class Priority {

    public static final String MESSAGE_PRIORITY_CONSTRAINTS =
            "Task priority should be an integer between 0 and 3 inclusive.";
    public static final String PRIORITY_VALIDATION_REGEX = "[0-3]";

    public final String value;

    /**
     * Validates given priority value.
     *
     * @throws IllegalValueException if given priority string is invalid.
     */
    public Priority(String priority) throws IllegalValueException {
        assert priority != null;
        priority = priority.trim();
        if (!isValidPriority(priority)) {
            throw new IllegalValueException(MESSAGE_PRIORITY_CONSTRAINTS);
        }
        this.value = priority;
    }

    /**
     * Returns if a given string is a valid person email.
     */
    public static boolean isValidPriority(String test) {
        return test.matches(PRIORITY_VALIDATION_REGEX) || test.equals("");
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Priority // instanceof handles nulls
                && this.value.equals(((Priority) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
    
    public String forDisplay() {
        switch (value) {
        case "1":
            return "Priority:\t\tLow";
        case "2":
            return "Priority:\t\tMedium";
        case "3":
            return "Priority:\t\tHigh";
        default:
            return "Priority:\t\tNone";
        }
    }

}
