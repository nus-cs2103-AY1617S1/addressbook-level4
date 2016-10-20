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
    
    public TaskBuilder withInterval(String startDate, String startTime, String endDate, String endTime) throws IllegalValueException {
        this.task.setInterval(new Interval(startDate, startTime, endDate, endTime));
        return this;
    }

    public TaskBuilder withLocation(String location) throws IllegalValueException {
    	this.task.setLocation(new Location(location));
        return this;
    }
    
    public TaskBuilder withRemarks(String remarks) throws IllegalValueException {
        this.task.setRemarks(new Remarks(remarks));
        return this;
    }
    
    public TaskBuilder withStatus(String status) throws IllegalValueException {
        this.task.setStatus(new Status(status));
        return this;
    }

    public TestTask build() {
        return this.task;
    }

}
