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

    public TaskBuilder withLocationParameter(String locationParameter) throws IllegalValueException {
    	this.task.setLocationParameter(new LocationParameter(locationParameter));
        return this;
    }
    
    public TaskBuilder withRemarksParameter(String remarksParameter) throws IllegalValueException {
        this.task.setRemarksParameter(new RemarksParameter(remarksParameter));
        return this;
    }

    public TestTask build() {
        return this.task;
    }

}
