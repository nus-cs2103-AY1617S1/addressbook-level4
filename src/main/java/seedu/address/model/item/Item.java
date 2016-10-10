package seedu.address.model.item;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.model.tag.UniqueTagList;

import java.util.Objects;
import java.util.Set;

/**
 * Represents a Item in the address book.
 * Guarantees: details are present and not null, field values are validated.
 */
public class Item implements ReadOnlyItem {

    private ItemType itemType;
    private Name name;
    private Date startDate;
    private Time startTime;
    private Date endDate;
    private Time endTime;

    private UniqueTagList tags;

    /**
     * Convenience constructor for tasks. Calls primary constructor with empty fields for startDate, startTime, endDate, endTime
     *
     */
    public Item(ItemType itemType, Name name, UniqueTagList tags) {
        this(itemType, name, new Date(), new Time(), new Date(), new Time(), tags);
    }
    
    /**
     * Convenience constructor for deadlines. Calls primary constructor with empty fields for startDate and startTime
     *
     */
    public Item(ItemType itemType, Name name, Date endDate, Time endTime, UniqueTagList tags) {
        this(itemType, name, new Date(), new Time(), endDate, endTime, tags);
    }
    
    
    /**
     * Every field must be present and not null.
     */
    public Item(ItemType itemType, Name name, Date startDate, Time startTime, Date endDate, Time endTime, UniqueTagList tags) {
        assert !CollectionUtil.isAnyNull(itemType, name, endDate, endTime, tags);
        this.itemType = itemType;
        this.name = name;
        this.startDate = startDate;
        this.startTime = startTime;
        this.endDate = endDate;
        this.endTime = endTime;
        this.tags = new UniqueTagList(tags); // protect internal tags from changes in the arg list
    }

    /**
     * Copy constructor.
     */
    public Item(ReadOnlyItem source) {
        this(source.getItemType(), source.getName(), source.getStartDate(), source.getStartTime(), source.getEndDate(), source.getEndTime(), source.getTags());
    }

    @Override
    public ItemType getItemType() {
        return itemType;
    }

    @Override
    public Name getName() {
        return name;
    }

    public void setName(String name) throws IllegalValueException {
        this.name = new Name(name);
    }
    
    @Override
    public Date getStartDate() {
        return startDate;
    }
    
    public void setStartDate(String startDate) throws IllegalValueException {
        this.startDate = new Date(startDate);
    }

    @Override
    public Time getStartTime() {
        return startTime;
    }    
    
    public void setStartTime(String startTime) throws IllegalValueException {
        this.startTime = new Time(startTime);
    }
    
    @Override
    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) throws IllegalValueException {
        this.endDate = new Date(endDate);
    }
    
    @Override
    public Time getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) throws IllegalValueException {
        this.endTime = new Time(endTime);
    }
    
    @Override
    public UniqueTagList getTags() {
        return new UniqueTagList(tags);
    }

    /**
     * Replaces this person's tags with the tags in the argument tag list.
     */
    public void setTags(UniqueTagList replacement) {
        tags.setTags(replacement);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ReadOnlyItem // instanceof handles nulls
                && this.isSameStateAs((ReadOnlyItem) other));
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(itemType, name, endDate, endTime, tags);
    }

    @Override
    public String toString() {
        return getAsText();
    }

}
