//@@author A0144939R
package seedu.task.commons.events.storage;

import seedu.task.commons.events.BaseEvent;

/** Indicates that the user has specified a new file path*/
public class FilePathChangedEvent extends BaseEvent {

    public final String newFilePath;

    public FilePathChangedEvent(String newFilePath){
        this.newFilePath = newFilePath;
    }

    @Override
    public String toString() {
        return "The new file path specified is "+newFilePath;
    }

}
