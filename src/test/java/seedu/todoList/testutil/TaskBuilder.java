package seedu.todoList.testutil;


import seedu.todoList.commons.exceptions.IllegalValueException;
import seedu.todoList.model.task.*;
import seedu.todoList.model.task.attributes.*;
/**
 *
 */
public class TaskBuilder {

    private TestTask task;

    public TaskBuilder() {
        this.task = new TestTask();
    }

    public TaskBuilder withTodo(Todo todo) throws IllegalValueException {
        this.task.setTodo(new Todo(todo));
        return this;
    }
    
    public TaskBuilder withName(String name) throws IllegalValueException {
        this.task.setName(new Name(name));
        return this;
    }
    
    public TaskBuilder withDate(String date) throws IllegalValueException {
        this.task.setDate(new StartDate(date));
        return this;
    }

    public TaskBuilder withPriority(String priority) throws IllegalValueException {
        this.task.setPriority(new Priority(priority));
        return this;
    }

    public TestTask build() {
        return this.task;
    }

}
