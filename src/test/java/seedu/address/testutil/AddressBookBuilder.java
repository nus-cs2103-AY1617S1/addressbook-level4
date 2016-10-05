package seedu.Todo.testutil;

import seedu.todoList.commons.exceptions.IllegalValueException;
import seedu.todoList.model.TodoList;
import seedu.todoList.model.tag.Tag;
import seedu.todoList.model.task.Task;
import seedu.todoList.model.task.UniquetaskList;

/**
 * A utility class to help with building Todobook objects.
 * Example usage: <br>
 *     {@code TodoList ab = new TodoListBuilder().withtask("John", "Doe").withTag("Friend").build();}
 */
public class TodoListBuilder {

    private TodoList TodoList;

    public TodoListBuilder(TodoList TodoList){
        this.TodoList = TodoList;
    }

    public TodoListBuilder withtask(Task task) throws UniquetaskList.DuplicatetaskException {
        TodoList.addtask(task);
        return this;
    }

    public TodoListBuilder withTag(String tagName) throws IllegalValueException {
        TodoList.addTag(new Tag(tagName));
        return this;
    }

    public TodoList build(){
        return TodoList;
    }
}
