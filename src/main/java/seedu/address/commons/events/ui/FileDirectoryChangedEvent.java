package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;

public class FileDirectoryChangedEvent extends BaseEvent {

    public final String filePath;
    
    public FileDirectoryChangedEvent(String filePath) {
        this.filePath = filePath;
    }
    
    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
