package seedu.address.testutil;

import seedu.emeraldo.commons.exceptions.IllegalValueException;
import seedu.emeraldo.model.tag.Tag;
import seedu.emeraldo.model.task.*;

/**
 *
 */
public class PersonBuilder {

    private TestPerson person;

    public PersonBuilder() {
        this.person = new TestPerson();
    }

    public PersonBuilder withName(String name) throws IllegalValueException {
        this.person.setName(new Description(name));
        return this;
    }

    public PersonBuilder withTags(String ... tags) throws IllegalValueException {
        for (String tag: tags) {
            person.getTags().add(new Tag(tag));
        }
        return this;
    }

    public PersonBuilder withAddress(String address) throws IllegalValueException {
        this.person.setDateTime(new DateTime(address));
        return this;
    }

    public TestPerson build() {
        return this.person;
    }

}
