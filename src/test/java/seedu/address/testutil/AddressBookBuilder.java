package seedu.address.testutil;

import seedu.address.model.AddressBook;
import seedu.address.model.item.FloatingTask;
import seedu.address.model.person.UniqueFloatingTaskList;

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

    public AddressBookBuilder withFloatingTask(FloatingTask person) throws UniqueFloatingTaskList.DuplicateFloatingTaskException {
        addressBook.addFloatingTask(person);
        return this;
    }

    public AddressBook build(){
        return addressBook;
    }
}
