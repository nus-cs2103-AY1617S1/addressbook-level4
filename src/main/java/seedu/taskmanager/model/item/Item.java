package seedu.taskmanager.model.item;

import seedu.taskmanager.commons.util.CollectionUtil;
import seedu.taskmanager.model.tag.UniqueTagList;

import java.util.Objects;

/**
 * Represents a Item in the task manager.
 * Guarantees: details are present and not null, field values are validated.
 * Date and Time can be empty strings.
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

    public void setName(Name name) {
        this.name = name;
    }
    
    @Override
    public Date getStartDate() {
        return startDate;
    }

    @Override
    public Time getStartTime() {
        return startTime;
    }    
    
    @Override
    public Date getEndDate() {
        return endDate;
    }

    @Override
    public Time getEndTime() {
        return endTime;
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
