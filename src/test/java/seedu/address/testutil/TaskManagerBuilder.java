package seedu.address.testutil;

import seedu.address.model.TaskManager;
import seedu.address.model.item.FloatingTask;
import seedu.address.model.item.UniqueFloatingTaskList;

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

    public TaskManagerBuilder withFloatingTask(FloatingTask person) throws UniqueFloatingTaskList.DuplicateFloatingTaskException {
        TaskManager.addFloatingTask(person);
        return this;
    }

    public TaskManager build(){
        return TaskManager;
    }
}
