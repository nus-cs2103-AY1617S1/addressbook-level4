package seedu.Tdoo.testutil;

import seedu.Tdoo.model.TaskList;
import seedu.Tdoo.model.task.Task;
import seedu.Tdoo.model.task.UniqueTaskList;

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
