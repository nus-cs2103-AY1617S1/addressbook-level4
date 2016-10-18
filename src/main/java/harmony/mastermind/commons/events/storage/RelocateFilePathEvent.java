package harmony.mastermind.commons.events.storage;

import harmony.mastermind.commons.events.BaseEvent;

//@author A013914X
public class RelocateFilePathEvent extends BaseEvent {

    public final String newFilePath;
    
    public RelocateFilePathEvent(String newFilePath) {
        this.newFilePath = newFilePath;
    }
    
    @Override
    public String toString() {
        return "Change save location file path to: " + newFilePath;
    }
}
