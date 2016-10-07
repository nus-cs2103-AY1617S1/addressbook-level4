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

    public TaskBuilder withTags(String ... tags) throws IllegalValueException {
        for (String tag: tags) {
            task.getTags().add(new Tag(tag));
        }
        return this;
    }

    public TaskBuilder withDetail(String detail) throws IllegalValueException {
        this.task.setDetail(new Detail(detail));
        return this;
    }

    public TaskBuilder withFromDate(String dateString) throws IllegalValueException {
        this.task.setFromDate(new TaskDate(dateString));
        return this;
    }

    public TaskBuilder withTillDate(String dateString) throws IllegalValueException {
        this.task.setTillDate(new TaskDate(dateString));
        return this;
    }

    public TestTask build() {
        return this.task;
    }

}
