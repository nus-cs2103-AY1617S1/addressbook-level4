package seedu.task.model.task;


import seedu.task.commons.exceptions.IllegalValueException;

/**
 * Represents a task's priority in the task manager.
 * Guarantees: immutable; is valid as declared in {@link #isValidPriority(String)}
 */
public class Priority {

    //@@ author A0139860X
    public static final String MESSAGE_PRIORITY_CONSTRAINTS =
            "Default priority is normal, otherwise it should be high or low";

    private final Integer value;

    /**
     * Validates given priority.
     *
     * @throws IllegalValueException if given task priority string is invalid.
     */
    public Priority(String priority) throws IllegalValueException {
        assert priority != null;
        if(priority.equals(""))
        	priority = "normal";
        else if (!isValidPriority(priority)) {
            throw new IllegalValueException(MESSAGE_PRIORITY_CONSTRAINTS);
        }
        switch (priority) {
        case "high":
            value = 3;
            break;
        case "low":
            value = 1;
            break;
        default:
            value = 2;
            break;
        }
    }

    /**
     * Returns if a given string is a valid priority.
     * Priority only has high or low
     */
    public static boolean isValidPriority(String test) {
        return test.equals("high")
               || test.equals("low")
               || test.equals("normal");
    }
    
    public boolean isRankedHigher(Priority priority) {
        return value > priority.value;
    }
    
    public boolean isRankedLower(Priority priority) {
        return value < priority.value;
    }

    @Override
    public String toString() {
        switch (value) {
        case 1:
            return "low";
        case 3:
            return "high";
        default:
            return "normal";
        }
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

}
