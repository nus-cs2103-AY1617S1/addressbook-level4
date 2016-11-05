package seedu.address.testutil;

import seedu.address.model.TaskManager;
import seedu.address.model.item.Task;
import seedu.address.model.item.UniqueTaskList;

/**
 * A utility class to help with building TaskManager objects.
 * Example usage: <br>
 *     {@code TaskManager ab = new TaskManagerBuilder().withPerson("John", "Doe").withTag("Friend").build();}
 */
public class TaskManagerBuilder {

    private TaskManager TaskManager;

    public TaskManagerBuilder(TaskManager TaskManager){
        this.TaskManager = TaskManager;
    }

    public TaskManagerBuilder withFloatingTask(Task person) {
        TaskManager.addTask(person);
        return this;
    }

    public TaskManager build(){
        return TaskManager;
    }
}
