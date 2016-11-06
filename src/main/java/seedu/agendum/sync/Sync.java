package seedu.agendum.sync;

import seedu.agendum.model.task.Task;

//@@author A0003878Y
public interface Sync {

    /** Enum used to persist SyncManager status **/
    enum SyncStatus {
        RUNNING, NOTRUNNING
    }

    /** Retrieve sync manager sync status **/
    SyncStatus getSyncStatus();

    /** Sets sync manager sync status **/
    void setSyncStatus(SyncStatus syncStatus);

    /** Turn on syncing **/
    void startSyncing();

    /** Turn off syncing **/
    void stopSyncing();

    /** Add Task to sync provider **/
    void addNewEvent(Task task);

    /** Remove task from sync provider **/
    void deleteEvent(Task task);
}
