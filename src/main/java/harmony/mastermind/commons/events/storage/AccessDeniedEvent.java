package harmony.mastermind.commons.events.storage;

import harmony.mastermind.commons.events.BaseEvent;

//@author A0139194X
public class AccessDeniedEvent extends BaseEvent {
    
    public final String filePath;
    public final String oldPath;

    public AccessDeniedEvent(String filePath, String oldPath) {
        this.filePath = filePath;
        this.oldPath = oldPath;
    }

    @Override
    public String toString() {
        return "Permission to access " + filePath + " denied!" 
                + "\n" + "Reverting save location back to " + oldPath;
    }
}
