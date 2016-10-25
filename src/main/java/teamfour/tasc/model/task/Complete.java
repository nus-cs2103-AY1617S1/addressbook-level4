//@@author A0140011L
package teamfour.tasc.model.task;

import java.util.Objects;

/**
 * Represents a task's completion status in the task list.
 * Guarantees: immutable
 */
public class Complete {
    public static final String TO_STRING_COMPLETED = "Completed";
    public static final String TO_STRING_NOT_COMPLETED = "Not Completed";
    
    private final boolean isCompleted;
    
    /**
     * Constructor for complete status.
     */
    public Complete(boolean isCompleted) {
        this.isCompleted = isCompleted;
    }
    
    @Override
    public String toString() {
        return isCompleted() ? TO_STRING_COMPLETED : TO_STRING_NOT_COMPLETED;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Complete // instanceof handles nulls
                && this.isCompleted() == (((Complete) other).isCompleted())); // state check
    }

    @Override
    public int hashCode() {
        return Objects.hash(isCompleted());
    }

    public boolean isCompleted() {
        return isCompleted;
    }
}
