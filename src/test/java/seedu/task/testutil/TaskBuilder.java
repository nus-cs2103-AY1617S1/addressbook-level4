package seedu.task.testutil;

import seedu.task.commons.exceptions.IllegalValueException;
import seedu.task.model.tag.Tag;
import seedu.task.model.task.*;

/**
 *
 */
public class TaskBuilder {

    private TestTask person;

    public TaskBuilder() {
        this.person = new TestTask();
    }

    public TaskBuilder withDescription(String description) throws IllegalValueException {
        this.person.setDescription(new Description(description));
        return this;
    }

    public TaskBuilder withTags(String ... tags) throws IllegalValueException {
        for (String tag: tags) {
            person.getTags().add(new Tag(tag));
        }
        return this;
    }

    public TaskBuilder withPriority(String priority) throws IllegalValueException {
        this.person.setPriority(new Priority(priority));
        return this;
    }

    public TaskBuilder withTimeStart(String timeStart) throws IllegalValueException {
        this.person.setTimeStart(new Time(timeStart));
        return this;
    }

    public TaskBuilder withTimeEnd(String timeEnd) throws IllegalValueException {
        this.person.setTimeEnd(new Time(timeEnd));
        return this;
    }

    public TestTask build() {
        return this.person;
    }

}
