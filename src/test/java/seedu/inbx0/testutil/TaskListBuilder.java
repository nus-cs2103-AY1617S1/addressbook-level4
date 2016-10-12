package seedu.inbx0.testutil;

import seedu.inbx0.commons.exceptions.IllegalValueException;
import seedu.inbx0.model.TaskList;
import seedu.inbx0.model.tag.Tag;
import seedu.inbx0.model.task.Task;
import seedu.inbx0.model.task.UniqueTaskList;

/**
 * A utility class to help with building TaskList objects.
 * Example usage: <br>
 *     {@code TaskList ab = new TaskListBuilder().withTask("John", "Doe").withTag("Friend").build();}
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

    public TaskListBuilder withTag(String tagName) throws IllegalValueException {
        taskList.addTag(new Tag(tagName));
        return this;
    }

    public TaskList build(){
        return taskList;
    }
}
