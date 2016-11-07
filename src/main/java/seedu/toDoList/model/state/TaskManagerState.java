package seedu.toDoList.model.state;

import seedu.toDoList.model.ReadOnlyTaskManager;
import seedu.toDoList.model.TaskManager;

//@@author A0146123R
/**
 * Represents the state of the task manager.
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
