package taskle.commons.events.ui;

import taskle.commons.events.BaseEvent;

public class ChangeStorageLocationRequestEvent extends BaseEvent {
    
    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
