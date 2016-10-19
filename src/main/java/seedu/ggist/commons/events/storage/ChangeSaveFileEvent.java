package seedu.ggist.commons.events.storage;

import seedu.ggist.commons.events.BaseEvent;

public class ChangeSaveFileEvent extends BaseEvent{

    public static String path;
    
    public ChangeSaveFileEvent(String path) {
        this.path = path;
    }
    
    @Override
    public String toString() {
        return "new file path: " + path;
    }

}
