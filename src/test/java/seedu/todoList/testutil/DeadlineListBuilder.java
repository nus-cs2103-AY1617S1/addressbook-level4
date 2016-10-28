package seedu.todoList.testutil;

import seedu.todoList.model.TaskList;
import seedu.todoList.model.task.Task;
import seedu.todoList.model.task.UniqueTaskList;

/**
 * A utility class to help with building TodoList  objects.
 * Example usage: <br>
 *     {@code TodoList ab = new DeadlineListBuilder().withTask("Assignment 1").build();}
 */
//@@author A0132157M reused
public class DeadlineListBuilder {

    private TaskList DeadlineList;

    public DeadlineListBuilder(TaskList DeadlineList){
        this.DeadlineList = DeadlineList;
    }

    public DeadlineListBuilder withTask(Task task) throws UniqueTaskList.DuplicatetaskException {
        DeadlineList.addTask(task);
        return this;
    }

    public TaskList build(){
        return DeadlineList;
    }
}
