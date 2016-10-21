package seedu.address.testutil;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.tag.Tag;
import seedu.address.model.task.*;

/**
 *
 */
public class DatedTaskBuilder {

    private TestTask person;

    public DatedTaskBuilder() {
        this.person = new TestTask();
    }

    public DatedTaskBuilder withName(String name) throws IllegalValueException {
        this.person.setName(new Name(name));
        return this;
    }

    public DatedTaskBuilder withTags(String ... tags) throws IllegalValueException {
        for (String tag: tags) {
            person.getTags().add(new Tag(tag));
        }
        return this;
    }

    public DatedTaskBuilder withStatus(String statusStr) throws IllegalValueException {
        this.person.setStatus(new Status(statusStr));
        return this;
    }
    
    public DatedTaskBuilder withDescription(String description) throws IllegalValueException {
        this.person.setDescription(new Description(description));
        return this;
    }

    public DatedTaskBuilder withDatetime(String date) throws IllegalValueException {
        this.person.setDatetime(new Datetime(date));
        return this;
    }

    public TestTask build() {
        return this.person;
    }

}
