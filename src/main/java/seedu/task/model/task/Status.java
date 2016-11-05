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
     * @return zero if this done status represents the same boolean value as the
     *         argument; a positive value if this done status represents true
     *         and the argument represents false; and a negative value if this
     *         done status represents false and the argument represents true
     */
    public int compareDoneStatusTo(Status anotherStatus) {
        return (Boolean.valueOf(this.getDoneStatus()).compareTo(Boolean.valueOf(anotherStatus.getDoneStatus())));
    }

    /**
     * Compares the two Statuses based on OverdueStatus.
     * 
     * @return zero if this done status represents the same boolean value as the
     *         argument; a positive value if this done status represents true
     *         and the argument represents false; and a negative value if this
     *         done status represents false and the argument represents true
     */
    public int compareOverdueStatusTo(Status anotherStatus) {
        return (Boolean.valueOf(this.getOverdueStatus()).compareTo(Boolean.valueOf(anotherStatus.getOverdueStatus())));
    }
    // @@author

}
