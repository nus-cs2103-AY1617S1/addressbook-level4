//@@author A0140060A-unused
//Functionality scrapped
package seedu.taskmanager.model.item;

import seedu.taskmanager.commons.exceptions.IllegalValueException;

/**
 * Represents a Item's priority in the task manager.
 */
public class Priority {

	public static final String HIGH_PRIORITY_WORD = "event";
	public static final String MEDIUM_PRIORITY_WORD = "deadline";
	public static final String LOW_PRIORITY_WORD = "task";
	
    public static final String MESSAGE_PRIORITY_CONSTRAINTS = "Item types should only be 'task', 'deadline' or 'event'.";
    public static final String PRIORITY_VALIDATION_REGEX = HIGH_PRIORITY_WORD 
                                                           + "|" + MEDIUM_PRIORITY_WORD 
                                                           + "|" + LOW_PRIORITY_WORD;

    public final String value;

    /**
     * Validates given priority.
     *
     * @throws IllegalValueException if given priority string is invalid.
     */
    public Priority(String priority) throws IllegalValueException {
        assert priority != null;
        priority = priority.trim();
        if (!isValidPriority(priority.toLowerCase())) {
            throw new IllegalValueException(MESSAGE_PRIORITY_CONSTRAINTS);
        }
        this.value = priority;
    }

    /**
     * Returns true if a given string is a valid priority.
     */
    public static boolean isValidPriority(String test) {
        return test.matches(PRIORITY_VALIDATION_REGEX);
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

}
