package seedu.forgetmenot.testutil;

import seedu.forgetmenot.model.TaskManager;
import seedu.forgetmenot.model.task.Task;
import seedu.forgetmenot.model.task.UniqueTaskList;

/**
 * A utility class to help with building Taskmanager objects.
 * Example usage: <br>
 *     {@code TaskManager ab = new TaskManagerBuilder().withTask("John", "Doe").build();}
 */
public class TaskManagerBuilder {

    private TaskManager taskManager;

    public TaskManagerBuilder(TaskManager taskManager){
        this.taskManager = taskManager;
    }

    public TaskManagerBuilder withTask(Task task) throws UniqueTaskList.DuplicateTaskException {
        taskManager.addTask(task);
        return this;
    }

    public TaskManager build(){
        return taskManager;
    }
}
