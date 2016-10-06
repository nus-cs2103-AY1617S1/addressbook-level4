package seedu.task.testutil;

import seedu.task.model.TaskBook;
import seedu.task.model.task.Task;
import seedu.task.model.task.UniqueTaskList;

/**
 * A utility class to help with building TaskBook objects.
 * Example usage: <br>
 *     {@code TaskBook tb = new TaskBookBuilder().withTask("CS2103 Project v0.2", "Publish on gitHub").build();}
 */
public class TaskBookBuilder {

    private TaskBook taskBook;

    public TaskBookBuilder(TaskBook taskBook){
        this.taskBook = taskBook;
    }

    public TaskBookBuilder withTask(Task task) throws UniqueTaskList.DuplicateTaskException {
        taskBook.addTask(task);
        return this;
    }

    public TaskBook build(){
        return taskBook;
    }
}
