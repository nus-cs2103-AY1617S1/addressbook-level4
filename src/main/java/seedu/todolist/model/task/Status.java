package seedu.todolist.model.task;

//@@author A0138601M
/**
 * Represents a Task's status in the to do list.
 * Guarantees: is one of the two values 'complete' or 'incomplete'
 */
public class Status {
    public static final String STATUS_COMPLETE = "complete";
    public static final String STATUS_INCOMPLETE = "incomplete";

    private boolean isComplete;

    /**
     * Set task's status. Default status is incomplete.
     */
    public Status(boolean isComplete) {
        setStatus(isComplete);
    }
    
    public Status(String isComplete) {
        if (isComplete.equals(STATUS_COMPLETE)) {
            this.isComplete = true;
        }
        else {
            this.isComplete = false;
        }
    }
    
    public void setStatus(boolean isComplete) {
        this.isComplete = isComplete;
    }
    
    public boolean isComplete() {
        return this.isComplete;
    }

    @Override
    public String toString() {
        if (this.isComplete) {
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
                && this.isComplete == ((Status) other).isComplete); // state check
    }

}
