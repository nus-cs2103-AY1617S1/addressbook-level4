package tars.testutil;

import tars.commons.exceptions.IllegalValueException;
import tars.model.task.*;
import tars.model.tag.Tag;

/**
 *
 */
public class PersonBuilder {

    private TestPerson task;

    public PersonBuilder() {
        this.task = new TestPerson();
    }

    public PersonBuilder withName(String name) throws IllegalValueException {
        this.task.setName(new Name(name));
        return this;
    }

    public PersonBuilder withTags(String ... tags) throws IllegalValueException {
        for (String tag: tags) {
            task.getTags().add(new Tag(tag));
        }
        return this;
    }

    public PersonBuilder withAddress(String address) throws IllegalValueException {
        this.task.setAddress(new Address(address));
        return this;
    }

    public PersonBuilder withPhone(String phone) throws IllegalValueException {
        this.task.setPhone(new Phone(phone));
        return this;
    }

    public PersonBuilder withEmail(String email) throws IllegalValueException {
        this.task.setEmail(new Email(email));
        return this;
    }

    public TestPerson build() {
        return this.task;
    }

}
