package seedu.ggist.testutil;

import seedu.ggist.commons.exceptions.IllegalValueException;
import seedu.ggist.model.TaskManager;
import seedu.ggist.model.tag.Tag;
import seedu.ggist.model.task.Task;
import seedu.ggist.model.task.UniqueTaskList;

/**
 * A utility class to help with building GGist objects.
 * Example usage: <br>
 *     {@code TaskManager ab = new TaskManagerBuilder().withTask("milk", "dance").withTag("floating").build();}
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
