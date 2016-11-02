package seedu.task.model.task;

/**
 * Represents a Task status in the task manager.
 * @@author A0147335E
 */
public class Status {

    private boolean isDone;

    private boolean isOverdue;

    private boolean newlyAdded;

    public Status() {
        this.isDone = false;
        this.isOverdue = false;
        this.newlyAdded = false;
    }

    public Status(boolean isDone, boolean isOverdue, boolean newlyAdded) {
        this.isDone = isDone;
        this.isOverdue = isOverdue;
        this.newlyAdded = newlyAdded;
    }

    public void setDoneStatus(boolean doneStatus) {
        this.isDone = doneStatus;
    }

    public void setOverdueStatus(boolean overdueStatus) {
        this.isDone = overdueStatus;
    }

    public void setNewlyAdded(boolean newlyAdded) {
        this.newlyAdded = newlyAdded;
    }

    public boolean getDoneStatus() {
        return isDone;
    }

    public boolean getOverdueStatus() {
        return isOverdue;
    }

    public boolean getNewlyAddedStatus() {
        return newlyAdded;
    }
    
    /**
     * Compares the two Statuses.
     * @@author A0147944U
     */
    public int compareDoneStatusTo(Status anotherStatus) {
        Boolean thisDoneStatus = new Boolean(this.getDoneStatus());
        Boolean anotherDoneStatus = new Boolean (anotherStatus.getDoneStatus());
        return thisDoneStatus.compareTo(anotherDoneStatus);
    }
    
    public int compareOverdueStatusTo(Status anotherStatus) {
        Boolean thisOverdueStatus = new Boolean(this.getOverdueStatus());
        Boolean anotherOverdueStatus = new Boolean (anotherStatus.getOverdueStatus());
        return thisOverdueStatus.compareTo(anotherOverdueStatus);
    }
    //@@author
}
