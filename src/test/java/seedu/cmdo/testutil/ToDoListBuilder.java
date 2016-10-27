package seedu.cmdo.testutil;

import seedu.cmdo.commons.exceptions.IllegalValueException;
import seedu.cmdo.model.ToDoList;
import seedu.cmdo.model.tag.Tag;
import seedu.cmdo.model.task.Task;

/**
 * A utility class to help with building ToDoList objects.
 * Example usage: <br>
 *     {@code ToDoList ab = new ToDoListBuilder().withTask("John", "Doe").withTag("Friend").build();}
 */
public class ToDoListBuilder {

    private ToDoList toDoList;

    public ToDoListBuilder(ToDoList toDoList){
        this.toDoList = toDoList;
    }

    public ToDoListBuilder withTask(Task task) {
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
