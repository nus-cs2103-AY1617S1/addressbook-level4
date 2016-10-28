package seedu.task.testutil;

import seedu.todolist.model.ToDoList;
import seedu.todolist.model.task.Task;
import seedu.todolist.model.task.UniqueTaskList;

/**
 * A utility class to help with building ToDoList objects.
 * Example usage: <br>
 *     {@code ToDoList ab = new ToDoListBuilder().withTask("John", "Doe").withTag("Friend").build();}
 */
public class ToDoListBuilder {

    private ToDoList ToDoList;

    public ToDoListBuilder(ToDoList ToDoList){
        this.ToDoList = ToDoList;
    }

    public ToDoListBuilder withTask(Task task) throws UniqueTaskList.DuplicateTaskException {
        ToDoList.addTask(task);
        return this;
    }

    public ToDoList build(){
        return ToDoList;
    }
}
