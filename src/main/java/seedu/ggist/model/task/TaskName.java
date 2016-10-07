package seedu.ggist.model.task;

import seedu.ggist.commons.exceptions.IllegalValueException;

/**
 * Represents a Task's name in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidName(String)}
 */
public class TaskName {

    public static final String MESSAGE_NAME_CONSTRAINTS = "Tasks should be spaces or alphanumeric characters";
    public static final String NAME_VALIDATION_REGEX = "[\\p{Alnum} ]+";

    public final String taskName;

    /**
     * Validates given task name.
     *
     * @throws IllegalValueException if given name string is invalid.
     */
<<<<<<< HEAD:src/main/java/seedu/ggist/model/task/TaskName.java
    public TaskName(String name) throws IllegalValueException {
        assert name != null;
        name = name.trim();
        if (!isValidName(name)) {
=======
    public TaskName(String taskName) throws IllegalValueException {
        assert taskName != null;
        taskName = taskName.trim();
        if (!isValidName(taskName)) {
>>>>>>> 98294e894113134b92ff545cbd3732461dc69f44:src/main/java/seedu/ggist/model/task/TaskName.java
            throw new IllegalValueException(MESSAGE_NAME_CONSTRAINTS);
        }
        this.taskName = taskName;
    }

    /**
     * Returns true if a given string is a valid task name.
     */
    public static boolean isValidName(String test) {
        return test.matches(NAME_VALIDATION_REGEX);
    }


    @Override
    public String toString() {
        return taskName;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof TaskName // instanceof handles nulls
<<<<<<< HEAD:src/main/java/seedu/ggist/model/task/TaskName.java
                && this.fullName.equals(((TaskName) other).fullName)); // state check
=======
                && this.taskName.equals(((TaskName) other).taskName)); // state check
>>>>>>> 98294e894113134b92ff545cbd3732461dc69f44:src/main/java/seedu/ggist/model/task/TaskName.java
    }

    @Override
    public int hashCode() {
        return taskName.hashCode();
    }

}
