package seedu.tasklist.testutil;

import seedu.tasklist.commons.exceptions.IllegalValueException;
import seedu.tasklist.model.TaskList;
import seedu.tasklist.model.task.Task;
import seedu.tasklist.model.task.UniqueTaskList;

/**
 * A utility class to help with building TaskList objects.
 * Example usage: <br>
 *     {@code TaskList tl = new TaskListBuilder().withTaskDetails("Buy Eggs").build();}
 */
public class TaskListBuilder {

    private TaskList taskList;

    public TaskListBuilder(TaskList taskList){
        this.taskList = taskList;
    }

    public TaskListBuilder withTask(Task task) throws UniqueTaskList.DuplicateTaskException {
        taskList.addTask(task);
        return this;
    }

   /* public TaskListBuilder withTag(String tagName) throws IllegalValueException {
        taskList.addTag(new Tag(tagName));
        return this;
    }*/

    public TaskList build(){
        return taskList;
    }
}
