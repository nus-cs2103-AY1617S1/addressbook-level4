package seedu.ggist.commons.events.storage;

import seedu.ggist.commons.events.BaseEvent;
//@@author A0138411N
/**
 * An event indicating the save file location or the file name has been changed
 */
public class ChangeSaveFileEvent extends BaseEvent{

    public static String path;
    
    public ChangeSaveFileEvent(String path) {
        this.path = path;
    }
    
    @Override
    public String toString() {
        return "new save file path: " + path;
    }

}
