package tars.model.task;

/**
 * Represents a Task's status in tars.
 */
public class Status {
    private static final String MESSAGE_STATUS_DONE = "Done";
    private static final String MESSAGE_STATUS_UNDONE = "Undone";

    private boolean status;

    public Status() {
        status = false;
    }

    @Override
    public String toString() {
        if (status) {
            return MESSAGE_STATUS_DONE;
        } else {
            return MESSAGE_STATUS_UNDONE;
        }
    }

    public boolean isDone() {
        return status;
    }

    public void setDone() {
        this.status = true;
    }
    
    public void setUndone() {
        this.status = false;
    }

}
