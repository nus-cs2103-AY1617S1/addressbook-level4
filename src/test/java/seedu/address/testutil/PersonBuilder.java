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

    public PersonBuilder withType(String type) throws IllegalValueException {
        this.person.setType(new Type(type));
        return this;
    }

    public PersonBuilder withTags(String ... tags) throws IllegalValueException {
        for (String tag: tags) {
            person.getTags().add(new Tag(tag));
        }
        return this;
    }

    public PersonBuilder withName(String name) throws IllegalValueException {
        this.person.setName(new Name(name));
        return this;
    }
    
    public PersonBuilder withStartDate(String date) throws IllegalValueException {
        this.person.setStartDate(new TodoDate(date));
        return this;
    }
    
    public PersonBuilder withStartTime(String time) throws IllegalValueException {
        this.person.setStartTime(new TodoTime(time));
        return this;
    }
    
    public PersonBuilder withEndDate(String date) throws IllegalValueException {
        this.person.setEndDate(new TodoDate(date));
        return this;
    }
    
    public PersonBuilder withEndTime(String time) throws IllegalValueException {
        this.person.setEndTime(new TodoTime(time));
        return this;
    }

    public TestPerson build() {
        return this.person;
    }

}
