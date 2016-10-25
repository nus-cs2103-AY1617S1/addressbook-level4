package seedu.oneline.testutil;

import seedu.oneline.commons.exceptions.IllegalValueException;
import seedu.oneline.model.tag.Tag;
import seedu.oneline.model.task.*;

/**
 *
 */
public class TaskBuilder {

    private TestTask task;

    public TaskBuilder() {
        this.task = new TestTask();
    }

    public TaskBuilder withName(String name) throws IllegalValueException {
        this.task.setName(new TaskName(name));
        return this;
    }

    public TaskBuilder withStartTime(String startTime) throws IllegalValueException {
        this.task.setStartTime(new TaskTime(startTime));
        return this;
    }

    public TaskBuilder withEndTime(String endTime) throws IllegalValueException {
        this.task.setEndTime(new TaskTime(endTime));
        return this;
    }

    public TaskBuilder withDeadline(String deadline) throws IllegalValueException {
        this.task.setDeadline(new TaskTime(deadline));
        return this;
    }

    public TaskBuilder withRecurrence(String recurrence) throws IllegalValueException {
        this.task.setRecurrence(new TaskRecurrence(recurrence));
        return this;
    }

    public TaskBuilder withTag(String tag) throws IllegalValueException {
        this.task.setTag(Tag.getTag(tag));
        return this;
    }
    
    public TestTask build() {
        return this.task;
    }

}
