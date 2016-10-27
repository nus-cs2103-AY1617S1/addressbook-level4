package seedu.oneline.testutil;

import seedu.oneline.commons.exceptions.IllegalValueException;
import seedu.oneline.model.TaskBook;
import seedu.oneline.model.tag.Tag;
import seedu.oneline.model.task.Task;
import seedu.oneline.model.task.UniqueTaskList;

/**
 * A utility class to help with building Task book objects.
 * Example usage: <br>
 *     {@code Task book tb = new TaskBookBuilder().withTask("John", "Doe").withTag("Friend").build();}
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
