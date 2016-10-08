package seedu.address.testutil;

import seedu.menion.commons.exceptions.IllegalValueException;
import seedu.menion.model.ActivityManager;
import seedu.menion.model.activity.Activity;
import seedu.menion.model.activity.UniqueActivityList;
import seedu.menion.model.tag.Tag;

/**
 * A utility class to help with building Addressbook objects.
 * Example usage: <br>
 *     {@code AddressBook ab = new AddressBookBuilder().withPerson("John", "Doe").withTag("Friend").build();}
 */
public class AddressBookBuilder {

    private ActivityManager addressBook;

    public AddressBookBuilder(ActivityManager addressBook){
        this.addressBook = addressBook;
    }

    public AddressBookBuilder withPerson(Activity person) throws UniqueActivityList.DuplicateTaskException {
        addressBook.addTask(person);
        return this;
    }

    public AddressBookBuilder withTag(String tagName) throws IllegalValueException {
        addressBook.addTag(new Tag(tagName));
        return this;
    }

    public ActivityManager build(){
        return addressBook;
    }
}
