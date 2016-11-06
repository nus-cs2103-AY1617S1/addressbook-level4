package seedu.agendum.sync;

import seedu.agendum.model.task.Task;

//@@author A0003878Y
public abstract class SyncProvider {

    /** Sync provider's keep a reference to the manager so that they can set it's
     * sync status **/
    protected Sync syncManager;

    /** Start sync provider and perform initialization **/
    public abstract void start();

    /** Start sync provider if it needs to be started **/
    public abstract void startIfNeeded();

    /** Stop sync provider and perform cleanup **/
    public abstract void stop();

    /** Add event into sync provider **/
    public abstract void addNewEvent(Task task);

    /** Delete event from sync provider **/
    public abstract void deleteEvent(Task task);

    /** Set sync provider's sync manager **/
    public void setManager(Sync syncManager) {
        this.syncManager = syncManager;
    }
}
