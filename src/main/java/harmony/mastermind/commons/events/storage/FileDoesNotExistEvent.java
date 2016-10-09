package harmony.mastermind.commons.events.storage;

import harmony.mastermind.commons.events.BaseEvent;

public class FileDoesNotExistEvent extends BaseEvent {
    
    public final String filePath;

    public FileDoesNotExistEvent(String filePath) {
        this.filePath = filePath;
    }

    @Override
    public String toString() {
        return filePath + " does not exist!";
    }
}
