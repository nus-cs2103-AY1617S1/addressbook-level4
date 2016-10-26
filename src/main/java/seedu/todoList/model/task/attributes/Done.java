package seedu.todoList.model.task.attributes;

import seedu.todoList.commons.exceptions.IllegalValueException;

/**
 * Represents a task's name in the TodoList.
 * Guarantees: immutable; is valid as declared in {@link #isValidName(String)}
 */
public class Done {

    //public static final String MESSAGE_NAME_CONSTRAINTS = "Done should only contain numbers";
    //public static final String NAME_VALIDATION_REGEX = "\\d+";

    public String isDone = "Not Done";

    /**
     * Validates given name.
     *
     * @throws IllegalValueException if given name string is invalid.
     */
    public Done(String done) {
        done = done.trim();
        this.isDone = done;
    }
    
    @Override
    public String toString() {
        return isDone;
    }
    
    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Done // instanceof handles nulls
                && this.isDone.equals(((Done) other).isDone)); // state check
    }

    @Override
    public int hashCode() {
        return isDone.hashCode();
    }

}
