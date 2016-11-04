package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;

/**
 * Indicates to the UI to bring out the open window.
 */
public class OpenFileChooserEvent extends BaseEvent {

    public final String fileDirectory;
    
    public OpenFileChooserEvent(String fileDirectory) {
        this.fileDirectory = fileDirectory;
    }
    
    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
