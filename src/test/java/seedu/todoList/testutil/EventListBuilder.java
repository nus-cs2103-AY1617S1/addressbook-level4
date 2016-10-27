package seedu.todoList.testutil;

import seedu.todoList.model.TaskList;
import seedu.todoList.model.task.Task;
import seedu.todoList.model.task.UniqueTaskList;

/**
 * A utility class to help with building TodoList  objects.
 * Example usage: <br>
 *     {@code TodoList ab = new EventListBuilder().withTask("Assignment 1").build();}
 */
//@@author A0132157M reused
public class EventListBuilder {

    private TaskList EventList;

    public EventListBuilder(TaskList EventList){
        this.EventList = EventList;
    }

    public EventListBuilder withTask(Task task) throws UniqueTaskList.DuplicatetaskException {
        EventList.addTask(task);
        return this;
    }

    public TaskList build(){
        return EventList;
    }
}
