package seedu.taskman.testutil;

import seedu.taskman.commons.exceptions.IllegalValueException;
import seedu.taskman.model.tag.Tag;
import seedu.taskman.model.task.Task;
import seedu.taskman.model.task.UniqueTaskList;
import seedu.taskman.model.AddressBook;

/**
 * A utility class to help with building Addressbook objects.
 * Example usage: <br>
 *     {@code AddressBook ab = new AddressBookBuilder().withPerson("John", "Doe").withTag("Friend").build();}
 */
public class AddressBookBuilder {

    private AddressBook addressBook;

    public AddressBookBuilder(AddressBook addressBook){
        this.addressBook = addressBook;
    }

    public AddressBookBuilder withPerson(Task task) throws UniqueTaskList.DuplicatePersonException {
        addressBook.addPerson(task);
        return this;
    }

    public AddressBookBuilder withTag(String tagName) throws IllegalValueException {
        addressBook.addTag(new Tag(tagName));
        return this;
    }

    public AddressBook build(){
        return addressBook;
    }
}
