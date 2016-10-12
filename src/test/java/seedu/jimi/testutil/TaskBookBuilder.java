package seedu.jimi.testutil;

import seedu.jimi.commons.exceptions.IllegalValueException;
import seedu.jimi.model.TaskBook;
import seedu.jimi.model.tag.Tag;
import seedu.jimi.model.task.FloatingTask;
import seedu.jimi.model.task.UniqueTaskList;

/**
 * A utility class to help with building Taskbook objects.
 * Example usage: <br>
 *     {@code TaskBook ab = new TaskBookBuilder().withTask("John", "Doe").withTag("Friend").build();}
 */
public class TaskBookBuilder {

    private TaskBook taskBook;

    public TaskBookBuilder(TaskBook taskBook){
        this.taskBook = taskBook;
    }

    public TaskBookBuilder withFloatingTask(FloatingTask floatingTask) throws UniqueTaskList.DuplicateTaskException {
        taskBook.addTask(floatingTask);
        return this;
    }

    public TaskBookBuilder withTag(String tagName) throws IllegalValueException {
        taskBook.addTag(new Tag(tagName));
        return this;
    }

    public TaskBook build(){
        return taskBook;
    }
}
