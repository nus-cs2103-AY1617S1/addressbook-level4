package harmony.testutil;

import harmony.commons.exceptions.IllegalValueException;
import harmony.model.tag.Tag;
import harmony.model.task.*;

/**
 *
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

    public PersonBuilder withPhone(String phone) throws IllegalValueException {
        this.person.setPhone(new Time(phone));
        return this;
    }

    public PersonBuilder withEmail(String email) throws IllegalValueException {
        this.person.setEmail(new Date(email));
        return this;
    }

    public TestPerson build() {
        return this.person;
    }

}
