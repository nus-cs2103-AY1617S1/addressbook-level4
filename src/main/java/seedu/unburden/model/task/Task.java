package seedu.unburden.model.task;

import java.util.Objects;

import seedu.unburden.commons.exceptions.IllegalValueException;
import seedu.unburden.commons.util.CollectionUtil;
import seedu.unburden.model.tag.UniqueTagList;

/**
 * Represents a Task in the address book.
 * Guarantees: details are present and not null, field values are validated.
 */
public class Task implements ReadOnlyTask {

    private Name name;
    private Date date;
    private Time startTime;
    private Time endTime;
    private UniqueTagList tags;

    /**
     * Every field must be present and not null.
     */
    public Task(Name name,Date date, Time startTime, Time endTime, UniqueTagList tags) {
        assert !CollectionUtil.isAnyNull(name, tags);
        this.name = name;
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
        this.tags = new UniqueTagList(tags); // protect internal tags from changes in the arg list
    }

    /**
     * Copy constructor.
     */
    public Task(ReadOnlyTask source) {
        this(source.getName(), source.getDate(), source.getStartTime(), source.getEndTime(), source.getTags());
    }

    public Task(Name name, UniqueTagList tags) throws IllegalValueException {
		assert !CollectionUtil.isAnyNull(name, tags);
		this.name = name;
		this.date = new Date("NIL");
		this.startTime = new Time("NIL");
		this.endTime = new Time("NIL");
		this.tags = new UniqueTagList(tags);
	}

	public Task(Name name, Date date, UniqueTagList tags) throws IllegalValueException {
		assert!CollectionUtil.isAnyNull(name, date, tags);
		this.name = name;
		this.date = date;
		this.startTime = new Time("NIL");
		this.endTime = new Time("NIL");
		this.tags = new UniqueTagList(tags);
	}

	public Task(Name name, Date date, Time endTime, UniqueTagList tags) throws IllegalValueException {
		assert!CollectionUtil.isAnyNull(name, date, endTime, tags);
		this.name = name;
		this.date = date;
		this.startTime = new Time("NIL");
		this.endTime = endTime;
		this.tags = new UniqueTagList(tags);
	}

	@Override
    public Name getName() {
        return name;
    }
    
    @Override
    public Date getDate() {
        return date;
    }
    
    @Override
    public Time getStartTime() {
        return startTime;
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
    
    public void setName(Name name) {
        this.name = name;
    }
    
    public void setDate(Date date) {
        this.date = date;
    }
    
    public void setStartTime(Time startTime) {
        this.startTime = startTime;
    }
    
    public void setEndTime(Time endTime) {
        this.endTime = endTime;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ReadOnlyTask // instanceof handles nulls
                && this.isSameStateAs((ReadOnlyTask) other));
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name,date,startTime,endTime, tags);
    }

    @Override
    public String toString() {
        return getAsText();
    }

}
