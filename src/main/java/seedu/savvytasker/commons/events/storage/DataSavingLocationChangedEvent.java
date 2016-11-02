package seedu.savvytasker.commons.events.storage;

import seedu.savvytasker.commons.events.BaseEvent;
import seedu.savvytasker.model.ReadOnlySavvyTasker;

//@@author A0139915W
/**
 * Indicates a change in location of the storage
 */
public class DataSavingLocationChangedEvent extends BaseEvent {

    public final ReadOnlySavvyTasker data;
    public final String newPath;

    public DataSavingLocationChangedEvent(ReadOnlySavvyTasker data, String newPath) {
        this.data = data;
        this.newPath = newPath;
    }

    @Override
    public String toString() {
        return "number of tasks " + data.getReadOnlyListOfTasks().size() +
                " new path " + this.newPath;
    }

}
//@@author
