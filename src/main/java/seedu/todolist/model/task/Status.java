package seedu.todolist.model.task;


/**
 * Represents a Task's status in the to do list.
 * Guarantees: is one of the two values 'completed' or 'incomplete'
 */
public class Status {
    public static final String STATUS_COMPLETE = "complete";
    public static final String STATUS_INCOMPLETE = "incomplete";

    private String status;

    /**
     * Set task's status. Default status is incomplete.
     */
    public Status(String status) {
        if (status != null) {
            this.status = status;
        }
        else {
            this.status = STATUS_INCOMPLETE;
        }
    }
    
    public void setStatus(boolean complete) {
        if (complete) {
            this.status = STATUS_COMPLETE;
        }
        else {
            this.status = STATUS_INCOMPLETE;
        }
    }

    @Override
    public String toString() {
        return status;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Status // instanceof handles nulls
                && this.status.equals(((Status) other).status)); // state check
    }

    @Override
    public int hashCode() {
        return status.hashCode();
    }
}
