package seedu.address.testutil;

import seedu.emeraldo.commons.exceptions.IllegalValueException;
import seedu.emeraldo.model.tag.Tag;
import seedu.emeraldo.model.task.*;

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

    public TaskBuilder withDateTime(String dateTime) throws IllegalValueException {
        this.task.setDateTime(new DateTime(dateTime));
        return this;
    }

    public TestTask build() {
        return this.task;
    }

}
