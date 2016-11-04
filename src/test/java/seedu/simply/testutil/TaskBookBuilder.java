package seedu.simply.testutil;

import seedu.simply.commons.exceptions.IllegalValueException;
import seedu.simply.model.TaskBook;
import seedu.simply.model.tag.Tag;
import seedu.simply.model.task.Task;
import seedu.simply.model.task.UniqueTaskList;
import seedu.simply.model.task.UniqueTaskList.DuplicateTaskException;

/**
 * A utility class to help with building Addressbook objects.
 * Example usage: <br>
 *     {@code AddressBook ab = new AddressBookBuilder().withPerson("John", "Doe").withTag("Friend").build();}
 */
public class TaskBookBuilder {

    private TaskBook addressBook;

    public TaskBookBuilder(TaskBook addressBook){
        this.addressBook = addressBook;
    }

    public TaskBookBuilder withPerson(Task person) throws DuplicateTaskException {
        addressBook.addTask(person);
        return this;
    }

    public TaskBookBuilder withTag(String tagName) throws IllegalValueException {
        addressBook.addTag(new Tag(tagName));
        return this;
    }

    public TaskBook build(){
        return addressBook;
    }
}
