package seedu.address.testutil;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.tag.Tag;
import seedu.address.model.task.*;

/**
 *@@author A0138993L
 */
public class PersonBuilder {

    private TestPerson person;

    public PersonBuilder() {
        this.person = new TestPerson();
    }

    public PersonBuilder withName(String name) throws IllegalValueException {
        this.person.setName(new Name(name));
        return this;
    }

    public PersonBuilder withTags(String ... tags) throws IllegalValueException {
        for (String tag: tags) {
            person.getTags().add(new Tag(tag));
        }
        return this;
    }

    public PersonBuilder withEnd(String address) throws IllegalValueException {
        this.person.setEnd(new End(address));
        return this;
    }

    public PersonBuilder withDate(String phone) throws IllegalValueException {
        this.person.setDate(new Date(phone));
        return this;
    }

    public PersonBuilder withStart(String email) throws IllegalValueException {
        this.person.setStart(new Start(email));
        return this;
    }

    public TestPerson build() {
        return this.person;
    }

}
