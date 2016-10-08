package seedu.address.testutil;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.item.*;
import seedu.address.model.tag.Tag;

/**
 *
 */
public class ItemBuilder {

    private TestItem person;

    public ItemBuilder() {
        this.person = new TestItem();
    }

    public ItemBuilder withItemType(String itemType) throws IllegalValueException {
        this.person.setItemType(new ItemType(itemType));
        return this;
    }

    public ItemBuilder withTags(String ... tags) throws IllegalValueException {
        for (String tag: tags) {
            person.getTags().add(new Tag(tag));
        }
        return this;
    }

    public ItemBuilder withEndDate(String endDate) throws IllegalValueException {
        this.person.setEndDate(new Date(endDate));
        return this;
    }
    
    public ItemBuilder withEndTime(String endTime) throws IllegalValueException {
        this.person.setEndTime(new Time(endTime));
        return this;
    }

    public ItemBuilder withName(String name) throws IllegalValueException {
        this.person.setName(new Name(name));
        return this;
    }

    public ItemBuilder withStartDate(String startDate) throws IllegalValueException {
        this.person.setStartDate(new Date(startDate));
        return this;
    }
    
    public ItemBuilder withStartTime(String startTime) throws IllegalValueException {
        this.person.setStartTime(new Time(startTime));
        return this;
    }

    public TestItem build() {
        return this.person;
    }

}
