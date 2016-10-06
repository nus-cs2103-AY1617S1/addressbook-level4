package seedu.oneline.testutil;

import seedu.oneline.commons.exceptions.IllegalValueException;
import seedu.oneline.model.TaskBook;
import seedu.oneline.model.tag.Tag;
import seedu.oneline.model.task.Task;
import seedu.oneline.model.task.UniqueTaskList;

/**
 * A utility class to help with building Addressbook objects.
 * Example usage: <br>
 *     {@code AddressBook ab = new AddressBookBuilder().withPerson("John", "Doe").withTag("Friend").build();}
 */
public class AddressBookBuilder {

    private TaskBook addressBook;

    public AddressBookBuilder(TaskBook addressBook){
        this.addressBook = addressBook;
    }

    public AddressBookBuilder withPerson(Task person) throws UniqueTaskList.DuplicateTaskException {
        addressBook.addTask(person);
        return this;
    }

    public AddressBookBuilder withTag(String tagName) throws IllegalValueException {
        addressBook.addTag(new Tag(tagName));
        return this;
    }

    public TaskBook build(){
        return addressBook;
    }
}
