package seedu.lifekeeper.commons.events.model;

import seedu.lifekeeper.commons.events.BaseEvent;

/**
 * Indicates that the data file location has changed.
 */
public class SaveEvent extends BaseEvent {
    
    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
