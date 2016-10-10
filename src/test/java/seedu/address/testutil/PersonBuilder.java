package seedu.address.testutil;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.tag.Tag;
import seedu.address.model.person.*;

/**
 *
 */
public class PersonBuilder {

    private TestPerson person;

    public PersonBuilder() {
        this.person = new TestPerson();
    }

    public PersonBuilder withName(String description) throws IllegalValueException {
        this.person.setDescription(new Description(description));
        return this;
    }

    public PersonBuilder withTags(String ... tags) throws IllegalValueException {
        for (String tag: tags) {
            person.getTags().add(new Tag(tag));
        }
        return this;
    }

    public PersonBuilder withAddress(String priority) throws IllegalValueException {
        this.person.setPriority(new Priority(priority));
        return this;
    }

    public PersonBuilder withPhone(String time) throws IllegalValueException {
        this.person.setTime(new Time(time));
        return this;
    }

    public PersonBuilder withEmail(String venue) throws IllegalValueException {
        this.person.setVenue(new Venue(venue));
        return this;
    }

    public TestPerson build() {
        return this.person;
    }

}
