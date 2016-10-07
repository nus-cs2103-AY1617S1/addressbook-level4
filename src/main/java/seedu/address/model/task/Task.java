package seedu.address.model.task;

import seedu.address.commons.util.CollectionUtil;
import seedu.address.model.tag.UniqueTagList;

import java.util.Objects;

/**
 * Represents a Task in the address book.
 * Guarantees: details are present and not null, field values are validated.
 */
public class Task implements ReadOnlyTask {

    private Name name;
    private Date date;
    private Time time;


//    private UniqueTagList tags;

    /**
     * Every field must be present and not null.
     */
    public Task(Name name , Date date, Time time) { //Address address, UniqueTagList tags) {
        //assert !CollectionUtil.isAnyNull(name, date, time, address, tags);
        this.name = name;
        this.date = date;
        this.time = time;
//        this.tags = new UniqueTagList(tags); // protect internal tags from changes in the arg list
    }
    
    public Task(Name name) {
		this.name = name;
	}

    /**
     * Copy constructor.
     */
    public Task(ReadOnlyTask source) {
        this(source.getName());
    }

	@Override
    public Name getName() {
        return name;
    }
     
    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ReadOnlyTask
                && this.isSameStateAs((ReadOnlyTask) other));
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name);//, date, time, address, tags);
    }

    @Override
    public String toString() {
        return getAsText();
    }

	@Override
	public Date getDate() {
		return this.date;
	}

	@Override
	public Time getTime() {
		return this.time;
	}

}
