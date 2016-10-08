package seedu.address.testutil;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.item.*;
import seedu.address.model.tag.Tag;

/**
 *
 */
public class PersonBuilder {

    private TestPerson person;

    public PersonBuilder() {
        this.person = new TestPerson();
    }

    public PersonBuilder withName(String name) throws IllegalValueException {
        this.person.setItemType(new ItemType(name));
        return this;
    }

    public PersonBuilder withTags(String ... tags) throws IllegalValueException {
        for (String tag: tags) {
            person.getTags().add(new Tag(tag));
        }
        return this;
    }

    public PersonBuilder withTime(String time) throws IllegalValueException {
        this.person.setAddress(new Time(time));
        return this;
    }

    public PersonBuilder withPhone(String phone) throws IllegalValueException {
        this.person.setName(new Name(phone));
        return this;
    }

    public PersonBuilder withDate(String date) throws IllegalValueException {
        this.person.setDate(new Date(date));
        return this;
    }

    public TestPerson build() {
        return this.person;
    }

}
