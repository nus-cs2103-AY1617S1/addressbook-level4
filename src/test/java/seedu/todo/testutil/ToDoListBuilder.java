package seedu.todo.testutil;

import seedu.todo.commons.exceptions.IllegalValueException;
import seedu.todo.model.ToDoList;
import seedu.todo.model.tag.Tag;
import seedu.todo.model.task.Task;
import seedu.todo.model.task.UniqueTaskList;

/**
 * A utility class to help with building Addressbook objects.
 * Example usage: <br>
 *     {@code ToDoList ab = new AddressBookBuilder().withTask("John", "Doe").withTag("Friend").build();}
 */
public class ToDoListBuilder {

    private ToDoList toDoList;

    public ToDoListBuilder(ToDoList toDoList){
        this.toDoList = toDoList;
    }

    public ToDoListBuilder withTask(Task task) throws UniqueTaskList.DuplicateTaskException {
        toDoList.addTask(task);
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
