package seedu.savvytasker.testutil;

import java.util.Date;

import seedu.savvytasker.commons.exceptions.IllegalValueException;
import seedu.savvytasker.model.task.PriorityLevel;
import seedu.savvytasker.model.task.RecurrenceType;

//@@author A0139915W
/**
 * Helper to build Task objects
 */
public class TaskBuilder {

    private TestTask task;

    public TaskBuilder() {
        this.task = new TestTask();
    }
    
    public TaskBuilder withId(int id) {
        this.task.setId(id);
        return this;
    }

    public TaskBuilder withTaskName(String taskName) throws IllegalValueException {
        this.task.setTaskName(taskName);
        return this;
    }
    
    public TaskBuilder withStartDateTime(Date startDateTime) {
        this.task.setStartDateTime(startDateTime);
        return this;
    }
    
    public TaskBuilder withEndDateTime(Date endDateTime) {
        this.task.setEndDateTime(endDateTime);
        return this;
    }
    
    public TaskBuilder withLocation(String location) {
        this.task.setLocation(location);
        return this;
    }
    
    public TaskBuilder withPriority(PriorityLevel priority) {
        this.task.setPriority(priority);
        return this;
    }
    
    public TaskBuilder withRecurringType(RecurrenceType recurringType) {
        this.task.setRecurringType(recurringType);
        return this;
    }
    
    public TaskBuilder withNumberOfRecurrence(int numberOfRecurrence) {
        this.task.setNumberOfRecurrence(numberOfRecurrence);
        return this;
    }
    
    public TaskBuilder withCategory(String category) {
        this.task.setCategory(category);
        return this;
    }
    
    public TaskBuilder withDescription(String description) {
        this.task.setDescription(description);
        return this;
    }
    
    public TaskBuilder withArchived(boolean isArchived) {
        this.task.setArchived(isArchived);
        return this;
    }

    public TestTask build() {
        return this.task;
    }

}
//@@author
