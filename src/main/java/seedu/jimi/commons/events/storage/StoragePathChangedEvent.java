package seedu.jimi.commons.events.storage;

import seedu.jimi.commons.core.Config;
import seedu.jimi.commons.events.BaseEvent;

public class StoragePathChangedEvent extends BaseEvent {
    
    public final Config data;
    
    public StoragePathChangedEvent(Config data){
        this.data = data;
    }

    @Override
    public String toString() {
        return "Storage Path changed " + data.getTaskBookFilePath();
    }
}
