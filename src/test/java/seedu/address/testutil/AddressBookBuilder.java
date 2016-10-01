package seedu.address.testutil;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.tag.Tag;
import seedu.address.model.TaskList;
import seedu.address.model.person.Person;
import seedu.address.model.person.UniqueTaskList;

/**
 * A utility class to help with building Addressbook objects.
 * Example usage: <br>
 *     {@code AddressBook ab = new AddressBookBuilder().withPerson("John", "Doe").withTag("Friend").build();}
 */
public class AddressBookBuilder {

    private TaskList taskList;

    public AddressBookBuilder(TaskList taskList){
        this.taskList = taskList;
    }

    public AddressBookBuilder withPerson(Person person) throws UniqueTaskList.DuplicatePersonException {
        taskList.addPerson(person);
        return this;
    }

    public AddressBookBuilder withTag(String tagName) throws IllegalValueException {
        taskList.addTag(new Tag(tagName));
        return this;
    }

    public TaskList build(){
        return taskList;
    }
}
