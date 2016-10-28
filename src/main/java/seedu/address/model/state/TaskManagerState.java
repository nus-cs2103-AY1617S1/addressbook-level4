package seedu.address.model.state;

import seedu.address.model.ReadOnlyTaskManager;
import seedu.address.model.TaskManager;

//@@author A0146123R
/**
 * Represent the state of task manager.
 */
public class TaskManagerState {
    
    private final ReadOnlyTaskManager taskManager;
    private final String message;
    
    public TaskManagerState(ReadOnlyTaskManager taskManager, String message) {
        this.taskManager = new TaskManager(taskManager);
        this.message = message;
    }
    
    public ReadOnlyTaskManager getTaskManager() {
        return taskManager;
    }
    
    public String getMessage() {
        return message;
    }
}
