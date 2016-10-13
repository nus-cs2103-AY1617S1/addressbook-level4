package seedu.address.testutil;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.tag.Tag;
import seedu.address.model.task.*;

/**
 *
 */
public class TaskBuilder {

    private TestTask person;

    public TaskBuilder() {
        this.person = new TestTask();
    }

    public TaskBuilder withName(String description) throws IllegalValueException {
        this.person.setDescription(new Description(description));
        return this;
    }

    public TaskBuilder withTags(String ... tags) throws IllegalValueException {
        for (String tag: tags) {
            person.getTags().add(new Tag(tag));
        }
        return this;
    }

    public TaskBuilder withAddress(String priority) throws IllegalValueException {
        this.person.setPriority(new Priority(priority));
        return this;
    }

    public TaskBuilder withPhone(String time) throws IllegalValueException {
        this.person.setTime(new Time(time));
        return this;
    }

    public TaskBuilder withEmail(String date) throws IllegalValueException {
        this.person.setDate(new Date(date));
        return this;
    }

    public TestTask build() {
        return this.person;
    }

}
