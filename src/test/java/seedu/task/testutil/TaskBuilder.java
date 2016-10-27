package seedu.task.testutil;

import seedu.task.commons.exceptions.IllegalValueException;
import seedu.task.model.item.*;


public class TaskBuilder {

    private TestTask task;

    public TaskBuilder() {
        this.task = new TestTask();
    }

    public TaskBuilder withName(String name) throws IllegalValueException {
        this.task.setName(new Name(name));
        return this;
    }


    public TaskBuilder withDescription(String description) throws IllegalValueException {
        this.task.setDescription(new Description(description));
        return this;
    }
    
    public TaskBuilder withDeadline(String deadline) throws IllegalValueException {
        assert deadline != null;
        this.task.setDeadline(new Deadline(deadline));
        return this;
    
    }
    
    public TaskBuilder withStatus(boolean status) {
    	this.task.setStatus(status);
    	return this;
    }

    public TestTask build() {
        return this.task;
    }

}
