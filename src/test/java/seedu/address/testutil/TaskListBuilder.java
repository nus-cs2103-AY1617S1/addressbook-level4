package seedu.address.testutil;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.tag.Tag;
import seedu.address.model.task.Task;
import seedu.address.model.task.UniqueTaskList;
import seedu.address.model.task.UniqueTaskList.TimeslotOverlapException;
import seedu.address.model.TaskMaster;

/**
 * A utility class to help with building TaskList objects.
 * Example usage: <br>
 *     {@code TaskList ab = new TaskListBuilder().withTask("John", "Doe").withTag("Friend").build();}
 */
public class TaskListBuilder {

    private TaskMaster taskList;

    public TaskListBuilder(TaskMaster taskList){
        this.taskList = taskList;
    }

    public TaskListBuilder withTask(Task task) throws UniqueTaskList.DuplicateTaskException, TimeslotOverlapException {
        taskList.addTask(task);
        return this;
    }

    public TaskListBuilder withTag(String tagName) throws IllegalValueException {
        taskList.addTag(new Tag(tagName));
        return this;
    }

    public TaskMaster build(){
        return taskList;
    }
}
