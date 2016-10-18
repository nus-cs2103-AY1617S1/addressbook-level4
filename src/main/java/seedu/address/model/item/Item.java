package seedu.address.model.item;

import java.util.Objects;

import seedu.address.model.tag.UniqueTagList;

/**
 * Represents a Item in the address book. Guarantees: details are present and
 * not null, field values are validated.
 */
public class Item {


    private UniqueTagList tags;
	private Description description;
	private boolean isDone;
	
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
	    return this.isDone;
	}

	// @Override
	// public UniqueTagList getTags() {
	// return new UniqueTagList(tags);
	// }

    /**
	 * Replaces this Item's tags with the tags in the argument tag list.
	 */
    public void setTags(UniqueTagList replacement) {
        tags.setTags(replacement);
    }
    
    public void setIsDone(boolean doneness) {
        this.isDone = doneness;
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
