package seedu.address.testutil;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.tag.Tag;
import seedu.address.model.AddressBook;
import seedu.address.model.item.Item;
import seedu.address.model.item.UniquePersonList;

/**
 * A utility class to help with building Addressbook objects.
 * Example usage: <br>
 *     {@code AddressBook ab = new TaskManagerBuilder().withPerson("John", "Doe").withTag("Friend").build();}
 */
public class TaskManagerBuilder {

    private AddressBook addressBook;

    public TaskManagerBuilder(AddressBook addressBook){
        this.addressBook = addressBook;
    }

    public TaskManagerBuilder withItem(Item item) throws UniquePersonList.DuplicatePersonException {
        addressBook.addItem(item);
        return this;
    }

    public TaskManagerBuilder withTag(String tagName) throws IllegalValueException {
        addressBook.addTag(new Tag(tagName));
        return this;
    }

    public AddressBook build(){
        return addressBook;
    }
}
