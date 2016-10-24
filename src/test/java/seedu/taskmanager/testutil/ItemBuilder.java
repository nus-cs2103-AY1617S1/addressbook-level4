package seedu.taskmanager.testutil;

import seedu.taskmanager.commons.exceptions.IllegalValueException;
import seedu.taskmanager.model.item.ItemDate;
import seedu.taskmanager.model.item.ItemType;
import seedu.taskmanager.model.item.Name;
import seedu.taskmanager.model.item.ItemTime;
import seedu.taskmanager.model.tag.Tag;

//@@author A0140060A-reused
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
        this.item.setEndDate(new ItemDate(endDate));
        return this;
    }
    
    public ItemBuilder withEndTime(String endTime) throws IllegalValueException {
        this.item.setEndTime(new ItemTime(endTime));
        return this;
    }

    public ItemBuilder withName(String name) throws IllegalValueException {
        this.item.setName(new Name(name));
        return this;
    }

    public ItemBuilder withStartDate(String startDate) throws IllegalValueException {
        this.item.setStartDate(new ItemDate(startDate));
        return this;
    }
    
    public ItemBuilder withStartTime(String startTime) throws IllegalValueException {
        this.item.setStartTime(new ItemTime(startTime));
        return this;
    }

    public TestItem build() {
        return this.item;
    }

}
