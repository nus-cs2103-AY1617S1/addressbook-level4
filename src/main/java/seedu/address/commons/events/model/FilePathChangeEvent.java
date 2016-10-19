package seedu.address.commons.events.model;

import seedu.address.commons.events.BaseEvent;
import seedu.address.model.ReadOnlyTaskMaster;

/** Indicates the TaskList in the model has changed*/
public class FilePathChangeEvent extends BaseEvent {

    public final String newFilePath;

    public FilePathChangeEvent(String newFilePath){
        this.newFilePath = newFilePath;
    }

    @Override
    public String toString() {
        return "File path changes to :" + newFilePath;
    }
}
