package seedu.address.testutil;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.tag.Tag;
import seedu.address.model.task.Task;
import seedu.address.model.task.UniqueTaskList;
import seedu.address.model.ToDoList;

/**
 * A utility class to help with building Addressbook objects.
 * Example usage: <br>
 *     {@code ToDoList ab = new AddressBookBuilder().withPerson("John", "Doe").withTag("Friend").build();}
 */
public class AddressBookBuilder {

    private ToDoList toDoList;

    public AddressBookBuilder(ToDoList toDoList){
        this.toDoList = toDoList;
    }

    public AddressBookBuilder withPerson(Task task) throws UniqueTaskList.DuplicateTaskException {
        toDoList.addPerson(task);
        return this;
    }

    public AddressBookBuilder withTag(String tagName) throws IllegalValueException {
        toDoList.addTag(new Tag(tagName));
        return this;
    }

    public ToDoList build(){
        return toDoList;
    }
}
