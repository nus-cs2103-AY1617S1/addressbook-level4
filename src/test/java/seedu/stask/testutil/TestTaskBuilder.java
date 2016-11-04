package seedu.stask.testutil;

import seedu.stask.commons.exceptions.IllegalValueException;
import seedu.stask.model.tag.Tag;
import seedu.stask.model.task.*;

/**
 *
 */
public class TestTaskBuilder {

    private TestTask person;

    public TestTaskBuilder() {
        this.person = new TestTask();
    }

    public TestTaskBuilder withName(String name) throws IllegalValueException {
        this.person.setName(new Name(name));
        return this;
    }

    public TestTaskBuilder withTags(String ... tags) throws IllegalValueException {
        for (String tag: tags) {
            person.getTags().add(new Tag(tag));
        }
        return this;
    }

    public TestTaskBuilder withStatus(String statusStr) throws IllegalValueException {
        this.person.setStatus(new Status(statusStr));
        return this;
    }
    
    public TestTaskBuilder withDescription(String description) throws IllegalValueException {
        this.person.setDescription(new Description(description));
        return this;
    }

    public TestTaskBuilder withDatetime(String date) throws IllegalValueException {
        this.person.setDatetime(new Datetime(date));
        return this;
    }

    public TestTask build() {
        return this.person;
    }

}
