package seedu.inbx0.testutil;

import seedu.inbx0.commons.exceptions.IllegalValueException;
import seedu.inbx0.model.AddressBook;
import seedu.inbx0.model.tag.Tag;
import seedu.inbx0.model.task.Task;
import seedu.inbx0.model.task.UniqueTaskList;

/**
 * A utility class to help with building Addressbook objects.
 * Example usage: <br>
 *     {@code AddressBook ab = new AddressBookBuilder().withTask("John", "Doe").withTag("Friend").build();}
 */
public class AddressBookBuilder {

    private AddressBook addressBook;

    public AddressBookBuilder(AddressBook addressBook){
        this.addressBook = addressBook;
    }

    public AddressBookBuilder withTask(Task task) throws UniqueTaskList.DuplicateTaskException {
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
