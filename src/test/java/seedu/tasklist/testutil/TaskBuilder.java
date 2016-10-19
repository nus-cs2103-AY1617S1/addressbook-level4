package seedu.tasklist.testutil;

import seedu.tasklist.commons.exceptions.IllegalValueException;
import seedu.tasklist.model.tag.Tag;
import seedu.tasklist.model.task.*;

/**
 *
 */
public class TaskBuilder {

    private TestTask task;

    public TaskBuilder() {
        this.task = new TestTask();
    }

    public TaskBuilder withTaskDetails(String taskDetails) throws IllegalValueException {
        this.task.setTaskDetails(new TaskDetails(taskDetails));
        return this;
    }

    public TaskBuilder withTags(String ... tags) throws IllegalValueException {
        for (String tag: tags) {
            task.getTags().add(new Tag(tag));
        }
        return this;
    }

    public TaskBuilder withUniqueID(int uniqueID) throws IllegalValueException {
        this.task.setUniqueID(uniqueID);
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
    
    public TaskBuilder withPriority(String priority) throws IllegalValueException {
    	this.task.setPriority(new Priority(priority));
    	return this;
    }

    public TestTask build() {
        return this.task;
    }

}
