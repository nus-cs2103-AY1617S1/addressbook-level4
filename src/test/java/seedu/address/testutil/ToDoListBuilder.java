package seedu.address.testutil;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.tag.Tag;
import seedu.address.model.AddressBook;
import seedu.address.model.person.Person;
import seedu.address.model.person.UniquePersonList;

/**
 * A utility class to help with building Addressbook objects.
 * Example usage: <br>
 *     {@code AddressBook ab = new AddressBookBuilder().withPerson("John", "Doe").withTag("Friend").build();}
 */
public class ToDoListBuilder {

    private ToDoList toDoList;

    public ToDoListBuilder(ToDoList toDoList){
        this.toDoList = toDoList;
    }

    public ToDoListBuilder withPerson(Person person) throws UniquePersonList.DuplicatePersonException {
        toDoList.addPerson(person);
        return this;
    }

    public ToDoListBuilder withTag(String tagName) throws IllegalValueException {
        toDoList.addTag(new Tag(tagName));
        return this;
    }

    public ToDoList build(){
        return toDoList;
    }
}
