
package seedu.task.commons.events.storage;

import seedu.task.commons.core.Config;
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