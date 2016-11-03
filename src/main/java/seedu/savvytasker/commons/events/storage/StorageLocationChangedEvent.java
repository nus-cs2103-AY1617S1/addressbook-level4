//@@author A0138431L

package seedu.savvytasker.commons.events.storage;

import seedu.savvytasker.commons.core.Config;
import seedu.savvytasker.commons.events.BaseEvent;

/**
 * Shows that storage location has changed.
 */

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
//@@author