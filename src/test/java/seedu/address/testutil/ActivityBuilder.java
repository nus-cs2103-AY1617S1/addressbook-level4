package seedu.address.testutil;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.activity.Name;
import seedu.address.model.activity.Reminder;
import seedu.address.model.activity.task.*;
import seedu.address.model.tag.Tag;

/**
 *
 */
public class PersonBuilder {

    private TestActivity person;

    public PersonBuilder() {
        this.person = new TestActivity();
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

    public PersonBuilder withAddress(String address) throws IllegalValueException {
        this.person.setReminder(new Reminder(address));
        return this;
    }

    public PersonBuilder withPhone(String phone) throws IllegalValueException {
        this.person.setPhone(new DueDate(phone));
        return this;
    }

    public PersonBuilder withEmail(String email) throws IllegalValueException {
        this.person.setEmail(new Priority(email));
        return this;
    }

    public TestActivity build() {
        return this.person;
    }

}
