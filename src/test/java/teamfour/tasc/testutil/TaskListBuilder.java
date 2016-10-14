package teamfour.tasc.testutil;

import teamfour.tasc.commons.exceptions.IllegalValueException;
import teamfour.tasc.model.TaskList;
import teamfour.tasc.model.tag.Tag;
import teamfour.tasc.model.task.Task;
import teamfour.tasc.model.task.UniqueTaskList;

/**
 * A utility class to help with building TaskList objects.
 * Example usage: <br>
 *     {@code TaskList tl = new TaskListBuilder().withTask("Eat lunch", "Meeting").withTag("Important").build();}
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
