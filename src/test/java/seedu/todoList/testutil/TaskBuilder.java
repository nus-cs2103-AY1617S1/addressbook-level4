package seedu.todoList.testutil;


import seedu.todoList.commons.exceptions.IllegalValueException;
import seedu.todoList.model.task.*;
/**
 *
 */
public class TaskBuilder {

    private TestTask task;

    public TaskBuilder() {
        this.task = new TestTask();
    }

    public TaskBuilder withTodo(String Todo) throws IllegalValueException {
        this.task.setTodo(new Todo(Todo));
        return this;
    }

    public TaskBuilder withPriority(String priority) throws IllegalValueException {
        this.task.setPriority(new Priority(priority));
        return this;
    }
    
    public TaskBuilder withStartTime(String startTime) throws IllegalValueException {
        this.task.setStartTime(new StartTime(startTime));
        return this;
    }
    
    public TaskBuilder withEndTime(String endTime) throws IllegalValueException {
        this.task.setEndTime(new EndTime(endTime));
        return this;
    }

    public TestTask build() {
        return this.task;
    }

}
