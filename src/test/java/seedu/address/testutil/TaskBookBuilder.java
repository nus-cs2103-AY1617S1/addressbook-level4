package seedu.address.testutil;

import seedu.address.model.TaskBook;
import seedu.address.model.task.Task;

/**
 * A utility class to help with building Addressbook objects.
 * Example usage: <br>
 *     {@code TaskBook ab = new TaskBookBuilder().withPerson("John", "Doe").withTag("Friend").build();}
 */
public class TaskBookBuilder {

    private TaskBook addressBook;

    public TaskBookBuilder(TaskBook addressBook) {
        this.addressBook = addressBook;
    }

    public TaskBookBuilder withPerson(Task person) {
        addressBook.addTask(person);
        return this;
    }

    public TaskBook build() {
        return addressBook;
    }
}
