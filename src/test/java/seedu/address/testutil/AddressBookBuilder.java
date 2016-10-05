package seedu.address.testutil;

import seedu.smartscheduler.commons.exceptions.IllegalValueException;
import seedu.smartscheduler.model.TaskList;
import seedu.smartscheduler.model.tag.Tag;
import seedu.smartscheduler.model.task.Task;
import seedu.smartscheduler.model.task.UniqueTaskList;

/**
 * A utility class to help with building Addressbook objects.
 * Example usage: <br>
 *     {@code AddressBook ab = new AddressBookBuilder().withPerson("John", "Doe").withTag("Friend").build();}
 */
public class AddressBookBuilder {

    private TaskList taskList;

    public AddressBookBuilder(TaskList taskList){
        this.taskList = taskList;
    }

    public AddressBookBuilder withPerson(Task task) throws UniqueTaskList.DuplicatePersonException {
        taskList.addPerson(task);
        return this;
    }

    public AddressBookBuilder withTag(String tagName) throws IllegalValueException {
        taskList.addTag(new Tag(tagName));
        return this;
    }

    public TaskList build(){
        return taskList;
    }
}
