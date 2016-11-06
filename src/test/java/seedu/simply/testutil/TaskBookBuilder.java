package seedu.simply.testutil;

import seedu.simply.commons.exceptions.IllegalValueException;
import seedu.simply.model.TaskBook;
import seedu.simply.model.tag.Tag;
import seedu.simply.model.task.Task;
import seedu.simply.model.task.UniqueTaskList;
import seedu.simply.model.task.UniqueTaskList.DuplicateTaskException;

/**
 * A utility class to help with building Taskbook objects.
 * Example usage: <br>
 *     {@code TaskBook ab = new TaskBookBuilder().withPerson("John", "Doe").withTag("Friend").build();}
 */
public class TaskBookBuilder {

    private TaskBook taskBook;

    public TaskBookBuilder(TaskBook taskBook){
        this.taskBook = taskBook;
    }

    public TaskBookBuilder withPerson(Task person) throws DuplicateTaskException {
        taskBook.addTask(person);
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
