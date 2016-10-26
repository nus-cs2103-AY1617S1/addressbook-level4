package seedu.ggist.model.task;

import seedu.ggist.commons.exceptions.IllegalValueException;

/**
 * Represents a Task's name in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidName(String)}
 */
public class TaskName {
  //@@author A0138411N
    public String taskName;

    /**
     * Validates given task name.
     *
     * @throws IllegalValueException if given name string is invalid.
     */
    public TaskName(String taskName) throws IllegalValueException {
        assert taskName != null;
        taskName = taskName.trim();
        this.taskName = taskName;
    }
    
    public void editTaskName(String newTaskName) throws IllegalValueException {
        assert newTaskName != null;
        newTaskName = newTaskName.trim();
        this.taskName = newTaskName;
    }


    @Override
    public String toString() {
        return taskName;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof TaskName // instanceof handles nulls
                && this.taskName.equals(((TaskName) other).taskName)); // state check
    }

    @Override
    public int hashCode() {
        return taskName.hashCode();
    }

}
