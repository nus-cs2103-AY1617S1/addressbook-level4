package seedu.address.testutil;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.tag.Tag;
import seedu.address.model.person.*;

/**
 *
 */
public class PersonBuilder {

    private TestEntry person;

    public PersonBuilder() {
        this.person = new TestEntry();
    }

    public PersonBuilder withName(String name) throws IllegalValueException {
        this.person.setName(new Title(name));
        return this;
    }

    public PersonBuilder withTags(String ... tags) throws IllegalValueException {
        for (String tag: tags) {
            person.getTags().add(new Tag(tag));
        }
        return this;
    }

    public TestEntry build() {
        return this.person;
    }

}
