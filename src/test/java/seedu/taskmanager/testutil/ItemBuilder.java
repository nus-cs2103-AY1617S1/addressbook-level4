package seedu.taskmanager.testutil;

import seedu.taskmanager.commons.exceptions.IllegalValueException;
import seedu.taskmanager.model.item.Date;
import seedu.taskmanager.model.item.ItemType;
import seedu.taskmanager.model.item.Name;
import seedu.taskmanager.model.item.Time;
import seedu.taskmanager.model.tag.Tag;

/**
 *
 */
public class ItemBuilder {

    private TestItem item;

    public ItemBuilder() {
        this.item = new TestItem();
    }

    public ItemBuilder withItemType(String itemType) throws IllegalValueException {
        this.item.setItemType(new ItemType(itemType));
        return this;
    }

    public ItemBuilder withTags(String ... tags) throws IllegalValueException {
        for (String tag: tags) {
            item.getTags().add(new Tag(tag));
        }
        return this;
    }

    public ItemBuilder withEndDate(String endDate) throws IllegalValueException {
        this.item.setEndDate(new Date(endDate));
        return this;
    }
    
    public ItemBuilder withEndTime(String endTime) throws IllegalValueException {
        this.item.setEndTime(new Time(endTime));
        return this;
    }

    public ItemBuilder withName(String name) throws IllegalValueException {
        this.item.setName(new Name(name));
        return this;
    }

    public ItemBuilder withStartDate(String startDate) throws IllegalValueException {
        this.item.setStartDate(new Date(startDate));
        return this;
    }
    
    public ItemBuilder withStartTime(String startTime) throws IllegalValueException {
        this.item.setStartTime(new Time(startTime));
        return this;
    }

    public TestItem build() {
        return this.item;
    }

}
