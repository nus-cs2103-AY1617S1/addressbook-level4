package seedu.address.testutil;

import seedu.jimi.commons.exceptions.IllegalValueException;
import seedu.jimi.model.AddressBook;
import seedu.jimi.model.tag.Tag;
import seedu.jimi.model.task.Task;
import seedu.jimi.model.task.UniqueTaskList;

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

    public AddressBookBuilder withTask(Task task) throws UniqueTaskList.DuplicatePersonException {
        addressBook.addTask(task);
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
