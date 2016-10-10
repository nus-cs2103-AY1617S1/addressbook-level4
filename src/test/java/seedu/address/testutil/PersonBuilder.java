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

    public PersonBuilder withItemType(String itemType) throws IllegalValueException {
        this.person.setItemType(new ItemType(itemType));
        return this;
    }

    public PersonBuilder withTags(String ... tags) throws IllegalValueException {
        for (String tag: tags) {
            person.getTags().add(new Tag(tag));
        }
        return this;
    }

    public PersonBuilder withEndDate(String endDate) throws IllegalValueException {
        this.person.setEndDate(new Date(endDate));
        return this;
    }
    
    public PersonBuilder withEndTime(String endTime) throws IllegalValueException {
        this.person.setEndTime(new Time(endTime));
        return this;
    }

    public PersonBuilder withName(String name) throws IllegalValueException {
        this.person.setName(new Name(name));
        return this;
    }

    public PersonBuilder withStartDate(String startDate) throws IllegalValueException {
        this.person.setStartDate(new Date(startDate));
        return this;
    }
    
    public PersonBuilder withStartTime(String startTime) throws IllegalValueException {
        this.person.setStartTime(new Time(startTime));
        return this;
    }

    public TestPerson build() {
        return this.person;
    }

}
