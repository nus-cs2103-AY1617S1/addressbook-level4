package seedu.address.model.item;

import seedu.address.commons.exceptions.IllegalValueException;

public abstract class Task {
    public static final String NAME_VALIDATION_REGEX = "[\\p{Alnum} ]+";
    public static final String MESSAGE_NAME_CONSTRAINTS = "Task names should be spaces or alphanumeric characters";
    
    protected final String taskName;

    public Task(String taskName) throws IllegalValueException {
        assert taskName != null;
        if (!isValidName(taskName)) {
            throw new IllegalValueException(MESSAGE_NAME_CONSTRAINTS);
        }
        this.taskName = taskName;
    }
    
    /**
     * Returns true if a given value is a valid priority value.
     */
    public static boolean isValidName(String taskName) {
        return taskName.matches(NAME_VALIDATION_REGEX);
    }
}
