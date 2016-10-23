package seedu.task.testutil;

import seedu.task.commons.exceptions.IllegalValueException;
import seedu.task.model.tag.Tag;
import seedu.task.model.task.*;

/**
 *
 */
public class TaskBuilder {

    private TestTask task;

    public TaskBuilder() {
        this.task = new TestTask();
    }

    public TaskBuilder withDescription(String description) throws IllegalValueException {
        this.task.setDescription(new Description(description));
        return this;
    }

    public TaskBuilder withTags(String ... tags) throws IllegalValueException {
        for (String tag: tags) {
            task.getTags().add(new Tag(tag));
        }
        return this;
    }

    public TaskBuilder withPriority(String priority) throws IllegalValueException {
        this.task.setPriority(new Priority(priority));
        return this;
    }

    public TaskBuilder withTimeStart(String timeStart) throws IllegalValueException {
        this.task.setTimeStart(new Time(timeStart));
        return this;
    }

    public TaskBuilder withTimeEnd(String timeEnd) throws IllegalValueException {
        this.task.setTimeEnd(new Time(timeEnd));
        return this;
    }

    public TestTask build() {
        return this.task;
    }

}
