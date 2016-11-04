package tars.commons.events.storage;

import tars.commons.core.Config;
import tars.commons.events.BaseEvent;

/**
 * An event where the user changes the Tars Storage Directory/File Path
 * 
 * @@author A0124333U
 */
public class TarsStorageDirectoryChangedEvent extends BaseEvent {

    private final String newFilePath;
    private final Config newConfig;

    public TarsStorageDirectoryChangedEvent(String newFilePath,
            Config newConfig) {
        this.newFilePath = newFilePath;
        this.newConfig = newConfig;
    }

    public String getNewFilePath() {
        return this.newFilePath;
    }

    public Config getNewConfig() {
        return this.newConfig;
    }

    @Override
    public String toString() {
        return "File Path changed to " + this.newFilePath;
    }

}
