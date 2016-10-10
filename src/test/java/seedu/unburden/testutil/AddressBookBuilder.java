package seedu.unburden.testutil;

import seedu.unburden.commons.exceptions.IllegalValueException;
import seedu.unburden.model.ListOfTask;
import seedu.unburden.model.tag.Tag;
import seedu.unburden.model.task.Task;
import seedu.unburden.model.task.UniqueTaskList;

/**
 * A utility class to help with building Addressbook objects.
 * Example usage: <br>
 *     {@code ListOfTask ab = new AddressBookBuilder().withPerson("John", "Doe").withTag("Friend").build();}
 */
public class AddressBookBuilder {

    private ListOfTask listOfTask;

    public AddressBookBuilder(ListOfTask listOfTask){
        this.listOfTask = listOfTask;
    }

    public AddressBookBuilder withTask(Task task) throws UniqueTaskList.DuplicatePersonException {
        listOfTask.addTask(task);
        return this;
    }

    public AddressBookBuilder withTag(String tagName) throws IllegalValueException {
        listOfTask.addTag(new Tag(tagName));
        return this;
    }

    public ListOfTask build(){
        return listOfTask;
    }
}
