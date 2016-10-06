package seedu.jimi.testutil;

import seedu.jimi.commons.exceptions.IllegalValueException;
import seedu.jimi.model.TaskBook;
import seedu.jimi.model.tag.Tag;
import seedu.jimi.model.task.Task;
import seedu.jimi.model.task.UniqueTaskList;

/**
 * A utility class to help with building Addressbook objects.
 * Example usage: <br>
 *     {@code TaskBook ab = new AddressBookBuilder().withPerson("John", "Doe").withTag("Friend").build();}
 */
public class AddressBookBuilder {

    private TaskBook taskBook;

    public AddressBookBuilder(TaskBook taskBook){
        this.taskBook = taskBook;
    }

    public AddressBookBuilder withTask(Task task) throws UniqueTaskList.DuplicateTaskException {
        taskBook.addTask(task);
        return this;
    }

    public AddressBookBuilder withTag(String tagName) throws IllegalValueException {
        taskBook.addTag(new Tag(tagName));
        return this;
    }

    public TaskBook build(){
        return taskBook;
    }
}
