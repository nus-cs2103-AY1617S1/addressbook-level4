package seedu.address.testutil;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.tag.Tag;
import seedu.address.model.task.*;

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
    
    public TaskBuilder withRecurrence(String recurrence) throws IllegalValueException {
        this.task.setRecurrence(new Recurrence(recurrence));
        return this;
    }

    public TaskBuilder withTags(String ... tags) throws IllegalValueException {
        for (String tag: tags) {
            task.getTags().add(new Tag(tag));
        }
        return this;
    }

    public TaskBuilder withEndTime(String end) throws IllegalValueException {
        this.task.setEndTime(new Time(end));
        return this;
    }

    public TaskBuilder withDone(Boolean done) throws IllegalValueException {
        this.task.setDone(new Done(done));
        return this;
    }

    public TaskBuilder withStartTime(String start) throws IllegalValueException {
        this.task.setStartTime(new Time(start));
        return this;
    }

    public TestTask build() {
        return this.task;
    }

}
