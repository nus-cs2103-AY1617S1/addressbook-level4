//@@author A0144939R
package seedu.task.commons.events.storage;

import seedu.task.commons.events.BaseEvent;

/** Indicates that the file path was successfully changed in the config*/
public class ConfigFilePathChangedEvent extends BaseEvent {

    public final String newFilePath;
    
    public ConfigFilePathChangedEvent(String newFilePath){
        this.newFilePath = newFilePath;
    }

    @Override
    public String toString() {
        return "The file path in Config has changed to " + newFilePath;
    }

}
