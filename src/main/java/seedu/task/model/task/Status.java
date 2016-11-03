package seedu.task.model.task;

// @@author A0147335E
/**
 * Represents a Task status in the task manager.
 */
public class Status {

    private boolean isDone;

    private boolean isOverdue;

    private boolean isFavorite;

    public Status() {
        this.isDone = false;
        this.isOverdue = false;
        this.isFavorite = false;
    }

    public Status(boolean isDone, boolean isOverdue, boolean isFavorite) {
        this.isDone = isDone;
        this.isOverdue = isOverdue;
        this.isFavorite = isFavorite;
    }

    public void setDoneStatus(boolean doneStatus) {
        this.isDone = doneStatus;
    }

    public void setOverdueStatus(boolean overdueStatus) {
        this.isDone = overdueStatus;
    }

    public void setFavoriteStatus(boolean isFavorite) {
        this.isFavorite = isFavorite;
    }

    public boolean getDoneStatus() {
        return isDone;
    }

    public boolean getOverdueStatus() {
        return isOverdue;
    }

    public boolean getFavoriteStatus() {
        return isFavorite;
    }

    // @@author A0147944U
    /**
     * Compares the two Statuses based on DoneStatus.
     * 
     * @param anotherStatus
     *            Status of another Task to compare to
     */
    public int compareDoneStatusTo(Status anotherStatus) {
        Boolean isThisDone = new Boolean(this.getDoneStatus());
        Boolean isAnotherDone = new Boolean(anotherStatus.getDoneStatus());
        return isThisDone.compareTo(isAnotherDone);
    }

    /**
     * Compares the two Statuses based on OverdueStatus.
     * 
     * @param anotherStatus
     *            Status of another Task to compare to
     */
    public int compareOverdueStatusTo(Status anotherStatus) {
        Boolean isThisOverdue = new Boolean(this.getOverdueStatus());
        Boolean isAnotherOverdue = new Boolean(anotherStatus.getOverdueStatus());
        return isThisOverdue.compareTo(isAnotherOverdue);
    }
    // @@author
}
