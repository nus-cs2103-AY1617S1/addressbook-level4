package seedu.taskitty.testutil;

import seedu.taskitty.commons.exceptions.IllegalValueException;
import seedu.taskitty.model.tag.Tag;
import seedu.taskitty.model.task.*;

/**
 *
 */
public class TaskBuilder {

    private TestTask person;

    public TaskBuilder() {
        this.person = new TestTask();
    }

    public TaskBuilder withName(String name) throws IllegalValueException {
        this.person.setName(new Name(name));
        return this;
    }

    public TaskBuilder withTags(String ... tags) throws IllegalValueException {
        for (String tag: tags) {
            person.getTags().add(new Tag(tag));
        }
        return this;
    }

    public TestTask build() {
        return this.person;
    }

}
