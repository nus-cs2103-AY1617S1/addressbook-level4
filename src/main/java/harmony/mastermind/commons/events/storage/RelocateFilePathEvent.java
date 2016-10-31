package harmony.mastermind.commons.events.storage;

import harmony.mastermind.commons.events.BaseEvent;

//@@author A0139194X
/*
 * Event that holds the new file path the user wants to relocate to
 */
public class RelocateFilePathEvent extends BaseEvent {

    private final String newFilePath;
    
    public RelocateFilePathEvent(String newFilePath) {
        this.newFilePath = newFilePath;
    }
    
    @Override
    public String toString() {
        return "Change save location file path to: " + newFilePath;
    }
    
    public String getFilePath() {
        return this.newFilePath;
    }
}
