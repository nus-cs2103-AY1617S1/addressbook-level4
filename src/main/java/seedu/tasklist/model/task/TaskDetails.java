package seedu.tasklist.model.task;

import seedu.tasklist.commons.exceptions.IllegalValueException;

/**
 * Represents a task's details in the to-do list.
 * Guarantees: immutable; is valid as declared in {@link #isValidName(String)}
 */
public class TaskDetails {

    public static final String MESSAGE_NAME_CONSTRAINTS = "Task details should include only alphanumeric/whitespace characters and must not be empty.";
    public static final String NAME_VALIDATION_REGEX = "^[a-zA-Z0-9\\s]*$";

    public final String taskDetails;

    /**
     * Validates given task details.
     *
     * @throws IllegalValueException if given name string is invalid.
     */
    public TaskDetails(String name) throws IllegalValueException {
        assert name != null;
        name = name.trim();
        if (!isValidName(name)) {
            throw new IllegalValueException(MESSAGE_NAME_CONSTRAINTS);
        }
        this.taskDetails = name;
    }

    /**
     * Returns true if a given string is a valid task detail.
     */
    public static boolean isValidName(String test) {
        return test.matches(NAME_VALIDATION_REGEX) && (!test.trim().isEmpty());
    }


    @Override
    public String toString() {
        return taskDetails;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof TaskDetails // instanceof handles nulls
                && this.taskDetails.equals(((TaskDetails) other).taskDetails)); // state check
    }

    @Override
    public int hashCode() {
        return taskDetails.hashCode();
    }

}
