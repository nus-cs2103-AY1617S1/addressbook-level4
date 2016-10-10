package seedu.task.testutil;

import seedu.todolist.commons.exceptions.IllegalValueException;
import seedu.todolist.model.task.*;

/**
 *
 */
public class TaskBuilder {

    private TestTask task;

    public TaskBuilder() {
        this.task = new TestTask();
    }

    public TaskBuilder withName(String name) throws IllegalValueException {
        this.task.setName(new Name(name));
        return this;
    }

    public TaskBuilder withLocationParameter(String locationParameter) throws IllegalValueException {
    	this.task.setLocationParameter(new LocationParameter(locationParameter));
        return this;
    }

    public TestTask build() {
        return this.task;
    }

}
