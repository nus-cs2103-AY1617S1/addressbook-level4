package seedu.address.testutil;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.tag.Tag;
import seedu.address.model.TaskList;
import seedu.address.model.person.Person;
import seedu.address.model.person.UniquePersonList;

/**
 * A utility class to help with building Addressbook objects.
 * Example usage: <br>
 *     {@code AddressBook ab = new AddressBookBuilder().withPerson("John", "Doe").withTag("Friend").build();}
 */
public class AddressBookBuilder {

    private TaskList addressBook;

    public AddressBookBuilder(TaskList addressBook){
        this.addressBook = addressBook;
    }

    public AddressBookBuilder withPerson(Person person) throws UniquePersonList.DuplicatePersonException {
        addressBook.addPerson(person);
        return this;
    }

    public AddressBookBuilder withTag(String tagName) throws IllegalValueException {
        addressBook.addTag(new Tag(tagName));
        return this;
    }

    public TaskList build(){
        return addressBook;
    }
}
