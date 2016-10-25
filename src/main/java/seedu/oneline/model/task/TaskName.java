package seedu.oneline.model.task;

import seedu.oneline.commons.exceptions.IllegalValueException;

public class TaskName implements Comparable<TaskName> {

    public static final String MESSAGE_TASK_NAME_CONSTRAINTS = "Task names should be spaces or alphanumeric characters";
    public static final String TASK_NAME_VALIDATION_REGEX = "[\\p{Alnum} ]+";

    public final String name;

    /**
     * Validates given name.
     *
     * @throws IllegalValueException if given name string is invalid.
     */
    public TaskName(String name) throws IllegalValueException {
        assert name != null;
        name = name.trim();
        if (!isValidTaskName(name)) {
            throw new IllegalValueException(MESSAGE_TASK_NAME_CONSTRAINTS);
        }
        this.name = name;
    }

    /**
     * Returns true if a given string is a valid task name.
     */
    public static boolean isValidTaskName(String test) {
        return test.matches(TASK_NAME_VALIDATION_REGEX);
    }


    @Override
    public String toString() {
        return name;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof TaskName // instanceof handles nulls
                && this.name.equals(((TaskName) other).name)); // state check
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }
    
    /**
     * Serialize field for storage
     */
    public String serialize() {
        return name;
    }
    
    /**
     * Deserialize from storage
     */
    public static TaskName deserialize(String args) throws IllegalValueException {
        return new TaskName(args);
    }

    //@@author A0138848M
    @Override
    public int compareTo(TaskName o) {
        assert name != null;
        return this.name.compareTo(o.name);
    }
}
