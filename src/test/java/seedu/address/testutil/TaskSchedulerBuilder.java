package seedu.address.testutil;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.tag.Tag;
import seedu.address.model.task.Task;
import seedu.address.model.task.UniqueTaskList;
import seedu.address.model.TaskScheduler;

/**
 * A utility class to help with building TaskScheduler objects.
 * Example usage: <br>
 *     {@code TaskScheduler ab = new TaskSchedulerBuilder().withTask("John", "Doe").withTag("Friend").build();}
 */
public class TaskSchedulerBuilder {

    private TaskScheduler taskScheduler;

    public TaskSchedulerBuilder(TaskScheduler taskScheduler){
        this.taskScheduler = taskScheduler;
    }

    public TaskSchedulerBuilder withTask(Task task) throws UniqueTaskList.DuplicateTaskException {
        taskScheduler.addTask(task);
        return this;
    }

    public TaskSchedulerBuilder withTag(String tagName) throws IllegalValueException {
        taskScheduler.addTag(new Tag(tagName));
        return this;
    }

    public TaskScheduler build(){
        return taskScheduler;
    }
}
