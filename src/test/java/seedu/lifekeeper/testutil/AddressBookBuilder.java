package seedu.lifekeeper.testutil;

import seedu.lifekeeper.commons.exceptions.IllegalValueException;
import seedu.lifekeeper.model.LifeKeeper;
import seedu.lifekeeper.model.activity.Activity;
import seedu.lifekeeper.model.activity.UniqueActivityList;
import seedu.lifekeeper.model.tag.Tag;

/**
 * A utility class to help with building Addressbook objects.
 * Example usage: <br>
 *     {@code AddressBook ab = new AddressBookBuilder().withPerson("John", "Doe").withTag("Friend").build();}
 */
public class AddressBookBuilder {

    private LifeKeeper addressBook;

    public AddressBookBuilder(LifeKeeper addressBook){
        this.addressBook = addressBook;
    }

    public AddressBookBuilder withPerson(Activity person) throws UniqueActivityList.DuplicateTaskException {
        addressBook.addPerson(person);
        return this;
    }

    public AddressBookBuilder withTag(String tagName) throws IllegalValueException {
        addressBook.addTag(new Tag(tagName));
        return this;
    }

    public LifeKeeper build(){
        return addressBook;
    }
}
