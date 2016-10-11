package seedu.oneline.model.task;

import seedu.oneline.commons.exceptions.IllegalValueException;
import seedu.oneline.model.tag.Tag;

public class TaskTime {

    public static final String MESSAGE_TASK_TIME_CONSTRAINTS =
            "Task time should ...";

    public final String value;

    /**
     * Validates given email.
     *
     * @throws IllegalValueException if given email address string is invalid.
     */
    public TaskTime(String time) throws IllegalValueException {
        assert time != null;
        time = time.trim();
        if (!isValidTaskTime(time)) {
            throw new IllegalValueException(MESSAGE_TASK_TIME_CONSTRAINTS);
        }
        this.value = time;
    }

    /**
     * Returns if a given string is a valid task time.
     */
    public static boolean isValidTaskTime(String test) {
        return true; // TODO
    }

    /**
     * Returns the default time value
     */
    public static TaskTime getDefault() {
        try {
            return new TaskTime("");
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
                || (other instanceof TaskTime // instanceof handles nulls
                && this.value.equals(((TaskTime) other).value)); // state check TODO
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
    public static TaskTime deserialize(String args) throws IllegalValueException {
        return new TaskTime(args);
    }
    
}
