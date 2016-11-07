//@@author A0144939R
package seedu.task.commons.events.model;

import java.util.Optional;
import seedu.task.commons.events.BaseEvent;
import seedu.task.model.ReadOnlyTaskManager;
import seedu.task.model.TaskManager;

/** Indicates that the user wishes to load from an existing file*/
public class ReloadFromNewFileEvent extends BaseEvent {

    public final String filePath;
    public final ReadOnlyTaskManager taskManager;

    public ReloadFromNewFileEvent(String newFilePath, Optional<ReadOnlyTaskManager> newTaskManager) {
        this.filePath = newFilePath;
        this.taskManager = newTaskManager.orElse(new TaskManager());
    }

    @Override
    public String toString() {
        return "The new file path is "+filePath;
    }

}