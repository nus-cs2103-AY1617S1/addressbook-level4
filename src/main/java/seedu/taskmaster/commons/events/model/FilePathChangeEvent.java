package seedu.taskmaster.commons.events.model;

import seedu.taskmaster.commons.events.BaseEvent;

//@@author A0147967J
/** Indicates the file path of the task master should change. */
public class FilePathChangeEvent extends BaseEvent {

    public final String newFilePath;

    public FilePathChangeEvent(String newFilePath) {
        this.newFilePath = newFilePath;
    }

    @Override
    public String toString() {
        return "File path changes to :" + newFilePath;
    }
}
