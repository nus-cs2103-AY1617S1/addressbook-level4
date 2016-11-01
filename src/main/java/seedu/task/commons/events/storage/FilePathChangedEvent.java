//@@author A0144939R
package seedu.task.commons.events.storage;

import seedu.task.commons.events.BaseEvent;
import seedu.task.model.ReadOnlyTaskManager;

/** Indicates that the user has specified a new file path*/
public class FilePathChangedEvent extends BaseEvent {

    public final String newFilePath;
    public final ReadOnlyTaskManager taskManager;

    public FilePathChangedEvent(String newFilePath, ReadOnlyTaskManager taskManager) {
        this.newFilePath = newFilePath;
        this.taskManager = taskManager;
    }

    @Override
    public String toString() {
        return "The new file path specified is "+newFilePath;
    }

}
