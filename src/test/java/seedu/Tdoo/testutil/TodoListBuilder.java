package seedu.Tdoo.testutil;

import seedu.Tdoo.model.TaskList;
import seedu.Tdoo.model.task.Task;
import seedu.Tdoo.model.task.UniqueTaskList;

/**
 * A utility class to help with building TodoList  objects.
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
