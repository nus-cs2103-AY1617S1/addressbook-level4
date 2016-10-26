package seedu.todoList.testutil;

import seedu.todoList.model.TaskList;
import seedu.todoList.model.task.Task;
import seedu.todoList.model.task.UniqueTaskList;

/**
 * A utility class to help with building TodoList  objects.
 * Example usage: <br>
 *     {@code TodoList ab = new TodoListBuilder().withTask("John", "Doe").withTag("Friend").build();}
 */
public class TodoListBuilder {

    private TaskList TodoList;

    public TodoListBuilder(TaskList TodoList){
        this.TodoList = TodoList;
    }

    public TodoListBuilder withTask(Task task) throws UniqueTaskList.DuplicatetaskException {
        TodoList.addTask(task);
        return this;
    }


    public TaskList build(){
        return TodoList;
    }
}
