package seedu.address.testutil;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.tag.Tag;
import seedu.address.model.TaskList;
import seedu.address.model.person.Task;
import seedu.address.model.person.UniquePersonList;

/**
 * A utility class to help with building TaskList objects.
 * Example usage: <br>
 *     {@code TaskList tl = new TaskListBuilder().withPerson("John", "Doe").withTag("Friend").build();}
 */
public class TaskListBuilder {

    private TaskList taskList;

    public TaskListBuilder(TaskList taskList){
        this.taskList = taskList;
    }

    public TaskListBuilder withPerson(Task person) throws UniquePersonList.DuplicatePersonException {
        taskList.addPerson(person);
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
