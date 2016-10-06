package harmony.testutil;

import harmony.commons.exceptions.IllegalValueException;
import harmony.model.TaskManager;
import harmony.model.tag.Tag;
import harmony.model.task.Task;
import harmony.model.task.UniqueTaskList;

/**
 * A utility class to help with building Addressbook objects.
 * Example usage: <br>
 *     {@code AddressBook ab = new AddressBookBuilder().withPerson("John", "Doe").withTag("Friend").build();}
 */
public class AddressBookBuilder {

    private TaskManager addressBook;

    public AddressBookBuilder(TaskManager addressBook){
        this.addressBook = addressBook;
    }

    public AddressBookBuilder withPerson(Task person) throws UniqueTaskList.DuplicatePersonException {
        addressBook.addPerson(person);
        return this;
    }

    public AddressBookBuilder withTag(String tagName) throws IllegalValueException {
        addressBook.addTag(new Tag(tagName));
        return this;
    }

    public TaskManager build(){
        return addressBook;
    }
}
