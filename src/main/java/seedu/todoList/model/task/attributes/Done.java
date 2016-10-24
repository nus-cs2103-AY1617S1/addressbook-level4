package seedu.todoList.model.task.attributes;

import seedu.todoList.commons.exceptions.IllegalValueException;

/**
 * Represents a task's name in the TodoList.
 * Guarantees: immutable; is valid as declared in {@link #isValidName(String)}
 */
public class Done {

    //public static final String MESSAGE_NAME_CONSTRAINTS = "Done should only contain numbers";
    //public static final String NAME_VALIDATION_REGEX = "\\d+";

    public String isDone;

    /**
     * Validates given name.
     *
     * @throws IllegalValueException if given name string is invalid.
     */
    public Done(String isDone) {
        this.isDone = isDone;
    }
}
