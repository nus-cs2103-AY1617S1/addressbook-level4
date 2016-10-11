package seedu.oneline.model.task;

import seedu.oneline.commons.exceptions.IllegalValueException;
import seedu.oneline.model.tag.Tag;

public class TaskRecurrence {

    public static final String MESSAGE_TASK_RECURRENCE_CONSTRAINTS =
            "Task recurrence should ...";

    public final String value;

    /**
     * Validates given email.
     *
     * @throws IllegalValueException if given email address string is invalid.
     */
    public TaskRecurrence(String recurrence) throws IllegalValueException {
        assert recurrence != null;
        recurrence = recurrence.trim();
        if (!isValidTaskRecurrence(recurrence)) {
            throw new IllegalValueException(MESSAGE_TASK_RECURRENCE_CONSTRAINTS);
        }
        this.value = recurrence;
    }

    /**
     * Returns if a given string is a valid task time.
     */
    public static boolean isValidTaskRecurrence(String test) {
        return true; // TODO
    }

    /**
     * Returns the default recurrence value
     */
    public static TaskRecurrence getDefault() {
        try {
            return new TaskRecurrence("");
        } catch (IllegalValueException e) {
            assert false; // This function should return a correct value!
        }
        return null;
    }
    
    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof TaskRecurrence // instanceof handles nulls
                && this.value.equals(((TaskRecurrence) other).value)); // state check TODO
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
    
    /**
     * Serialize field for storage
     */
    public String serialize() {
        return value;
    }
    
    /**
     * Deserialize from storage
     */
    public static TaskRecurrence deserialize(String args) throws IllegalValueException {
        return new TaskRecurrence(args);
    }
    
}
