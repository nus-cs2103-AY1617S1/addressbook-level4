package harmony.mastermind.testutil;

import harmony.mastermind.commons.exceptions.IllegalValueException;
import harmony.mastermind.model.TaskManager;
import harmony.mastermind.model.tag.Tag;
import harmony.mastermind.model.task.Task;
import harmony.mastermind.model.task.UniqueTaskList;

/**
 * A utility class to help with building taskManager objects.
 * Example usage: <br>
 *     {@code taskManager ab = new taskManagerBuilder().withPerson("John", "Doe").withTag("Friend").build();}
 */
public class TaskManagerBuilder {

    private TaskManager taskManager;

    public TaskManagerBuilder(TaskManager taskManager){
        this.taskManager = taskManager;
    }

    public TaskManagerBuilder withPerson(Task person) throws UniqueTaskList.DuplicateTaskException {
        taskManager.addTask(person);
        return this;
    }

    public TaskManagerBuilder withTag(String tagName) throws IllegalValueException {
        taskManager.addTag(new Tag(tagName));
        return this;
    }

    public TaskManager build(){
        return taskManager;
    }
}
