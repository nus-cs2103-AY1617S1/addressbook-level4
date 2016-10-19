package seedu.address.model.item;

import java.util.Objects;

import java.time.LocalDateTime;
import java.time.Period;

import seedu.address.commons.util.CollectionUtil;
import seedu.address.model.tag.UniqueTagList;

/**
 * Represents a Item in the address book. Guarantees: details are present and
 * not null, field values are validated.
 */
public class Item implements ReadOnlyItem {

    private UniqueTagList tags;
    private Description description;
    private boolean isDone;

    private LocalDateTime startDate;
    private LocalDateTime endDate;

    // for recurring events only
    private boolean isRecurring;
    private Period recurInterval;
    private LocalDateTime recurEndDate;

    /**
     * constructor for floating item
     */
    public Item(Description desc) {
        // assert !CollectionUtil.isAnyNull(name, phone, email, address, tags);
        assert desc != null;
        this.description = desc;
        this.isDone = false;
        // this.tags = new UniqueTagList(tags); // protect internal tags from
        // changes in the arg list
    }

    /**
     * constructor for an item with a definite
     * start and end time (non-recurring)
     * @param desc
     * @param start
     * @param end
     * @author darren
     */
    public Item(Description desc, LocalDateTime start, LocalDateTime end) {
        assert !CollectionUtil.isAnyNull(desc, start, end);
        this.description = desc;
        this.startDate = start;
        this.endDate = end;
    }

    /**
     * constructor for an item with a definite
     * end time only (non-recurring)
     * @param desc
     * @param end
     * @author darren
     */
    public Item(Description desc, LocalDateTime end) {
        assert !CollectionUtil.isAnyNull(desc, end);
        this.description = desc;
        this.endDate = end;
    }

    @Override
    public UniqueTagList getTags() {
        return this.tags;
    }

    @Override
	public Description getDescription() {
        return description;
    }

    @Override
	public boolean getIsDone() {
        return isDone;
    }

    @Override
	public LocalDateTime getStartDate() {
        return startDate;
    }

    @Override
	public LocalDateTime getEndDate() {
        return endDate;
    }

<<<<<<< HEAD
    public boolean isRecurring() {
        return isRecurring;
    }

    public Period getRecurInterval() {
        return recurInterval;
=======
	public Item(ReadOnlyItem item){
	    this.description = item.getDescription();
	    this.isDone = item.getIsDone();
	}
	
    public Description getDescription() {
		return description;
>>>>>>> ad2ed0392748424ae50a4caf241aa2906469bb43
    }

    public LocalDateTime getRecurEndDate() {
        return recurEndDate;
    }

    /**
     * Replaces this Item's tags with the tags in the argument tag list.
     */
    public void setTags(UniqueTagList replacement) {
        tags.setTags(replacement);
    }

    @Override
	public void setIsDone(boolean doneness) {
        this.isDone = doneness;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }

    public void setRecurring(boolean isRecurring) {
        this.isRecurring = isRecurring;
    }

    public void setRecurInterval(Period recurInterval) {
        this.recurInterval = recurInterval;
    }

    public void setRecurEndDate(LocalDateTime recurEndDate) {
        this.recurEndDate = recurEndDate;
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing
        // your own
        return Objects.hash(description);
    }

    @Override
    public String toString() {
        return description.toString();
    }


    @Override
    public UniqueTagList getTags() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ReadOnlyItem // instanceof handles nulls
                && this.isSameStateAs((ReadOnlyItem) other));
    }

}
