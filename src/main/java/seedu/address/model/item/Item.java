package seedu.address.model.item;

import java.util.Objects;

import java.time.LocalDateTime;
import java.time.Period;

import seedu.address.model.tag.UniqueTagList;

/**
 * Represents a Item in the address book. Guarantees: details are present and
 * not null, field values are validated.
 */
public class Item {


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
     * Every field must be present and not null.
     */
	public Item(Description desc) {
		// assert !CollectionUtil.isAnyNull(name, phone, email, address, tags);
		this.description = desc;
		this.isDone = false;
		// this.tags = new UniqueTagList(tags); // protect internal tags from
		// changes in the arg list
    }
	
	public Description getDescription() {
		return description;
    }

    public boolean getIsDone() {
	    return isDone;
	}

	
	public LocalDateTime getStartDate() {
        return startDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public boolean isRecurring() {
        return isRecurring;
    }

    public Period getRecurInterval() {
        return recurInterval;
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
        // use this method for custom fields hashing instead of implementing your own
		return Objects.hash(description);
    }

    @Override
    public String toString() {
		return description.toString();
    }

}
