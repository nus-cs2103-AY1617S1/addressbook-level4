package seedu.taskmanager.testutil;

import seedu.taskmanager.commons.exceptions.IllegalValueException;
import seedu.taskmanager.model.AddressBook;
import seedu.taskmanager.model.item.Item;
import seedu.taskmanager.model.item.UniquePersonList;
import seedu.taskmanager.model.tag.Tag;

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
