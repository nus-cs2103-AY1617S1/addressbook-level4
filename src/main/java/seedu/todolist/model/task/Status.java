package seedu.todolist.model.task;

//@@author A0138601M
/**
 * Represents a Task's status in the to do list.
 * Guarantees: is one of the two values 'complete' or 'incomplete'
 */
public class Status {
    public static final String STATUS_COMPLETE = "complete";
    public static final String STATUS_INCOMPLETE = "incomplete";

    private boolean status;

    /**
     * Set task's status. Default status is incomplete.
     */
    public Status(boolean status) {
        setStatus(status);
    }
    
    public Status(String status) {
        if (status.equals(STATUS_COMPLETE)) {
            this.status = true;
        }
        else {
            this.status = false;
        }
    }
    
    public void setStatus(boolean status) {
        this.status = status;
    }
    
    public boolean isComplete() {
        return this.status;
    }

    @Override
    public String toString() {
        if (this.status) {
            return STATUS_COMPLETE;
        }
        else {
            return STATUS_INCOMPLETE;
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Status // instanceof handles nulls
                && this.status == ((Status) other).status); // state check
    }

}
