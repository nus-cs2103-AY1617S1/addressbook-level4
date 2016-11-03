
package seedu.task.commons.events.storage;

/** Handles event changes in storage location
 * @@author A0125534L
 * */

import seedu.taskcommons.core.Config;
import seedu.task.commons.events.BaseEvent;

public class StorageLocationChangedEvent extends BaseEvent {
    
    private Config config;
    
    public StorageLocationChangedEvent(Config config) {
        this.config = config;
    }
    
    public Config getConfig() {
        return config;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

}