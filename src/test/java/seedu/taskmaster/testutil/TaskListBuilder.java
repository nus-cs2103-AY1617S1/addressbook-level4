package seedu.taskmaster.testutil;

import seedu.taskmaster.commons.exceptions.IllegalValueException;
import seedu.taskmaster.model.TaskMaster;
import seedu.taskmaster.model.tag.Tag;
import seedu.taskmaster.model.task.Task;
import seedu.taskmaster.model.task.UniqueTaskList;
import seedu.taskmaster.model.task.UniqueTaskList.TimeslotOverlapException;

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
