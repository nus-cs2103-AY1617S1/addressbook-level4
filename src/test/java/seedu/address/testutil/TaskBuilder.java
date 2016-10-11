package seedu.address.testutil;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.task.Name;

/**
 *
 */
public class TaskBuilder {

    private Name name;

    public TaskBuilder() {
    }

    public TaskBuilder withName(String name) throws IllegalValueException {
        this.name = new Name(name);
        return this;
    }

    public TestTask build() {
        return new TestTask(name);
    }

}
