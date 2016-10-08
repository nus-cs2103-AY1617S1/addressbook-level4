package seedu.task.model.task;

import java.util.Objects;

import seedu.task.commons.util.CollectionUtil;
import seedu.task.model.tag.Tag;
import seedu.task.model.tag.UniqueTagList;
import seedu.task.model.tag.UniqueTagList.DuplicateTagException;

/**
 * Represents a Task in the taskBook.
 * Guarantees: details are present and not null, field values are validated.
 */
public class Task implements ReadOnlyTask {

    private Name name;
    private DateTime startDate;
    private DateTime endDate;
    private Venue venue;
    private Status status = Status.ACTIVE;
    private Priority priority = Priority.LOW; //Default priority is low
    //Repeating, pin task
    private UniqueTagList tags;


    /**
     * Only Name, Priority and Status Should not be null
     */
    public Task(Name name, DateTime startDate, DateTime endDate, Venue venue, Priority priority, Status status, UniqueTagList tags) {
        assert !CollectionUtil.isAnyNull(name, priority, status);
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
        this.setVenue(venue);
        this.priority = priority;
        this.status = status;
        this.tags = new UniqueTagList(tags); // protect internal tags from changes in the arg list
    }
    
    public Task(Name name, DateTime endDate, Venue venue, Priority priority, Status status, UniqueTagList tags) {
    	this (name, new DateTime(""), endDate, venue, priority, status, tags);
    }
    
    public Task(Name name, DateTime endDate, Priority priority, Status status, UniqueTagList tags) {
    	this (name, new DateTime(""), endDate, new Venue(""), priority, status, tags);
    }

    public Task(Name name, DateTime endDate, Priority priority, Status status) {
    	this (name, new DateTime(""), endDate, new Venue(""), priority, status, new UniqueTagList());
    }
    
    public Task(Name name, Priority priority, Status status) {
    	this (name, new DateTime(""), new DateTime(""), new Venue(""), priority, status, new UniqueTagList());
    }
    
    /**
     * Copy constructor.
     */
    public Task(ReadOnlyTask source) {
        this(source.getName(), source.getStartDate(), source.getEndDate(), source.getVenue(), source.getPriority(), source.getStatus(), source.getTags());
    }
    
    /**
     * Empty Task Constructor.
     */
    public Task() {
    	this.startDate = new DateTime("");
    	this.endDate = new DateTime("");
    	this.venue = new Venue("");
    	this.tags = new UniqueTagList();
    }

    @Override
    public Name getName() {
        return name;
    }
    
	public void setName(Name name) {
		this.name = name;
	}

    @Override
    public Venue getVenue() {
        return venue;
    }

    @Override
    public UniqueTagList getTags() {
        return new UniqueTagList(tags);
    }

    /**
     * Replaces this task's tags with the tags in the argument tag list.
     */
    public void setTags(UniqueTagList replacement) {
        tags.setTags(replacement);
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
        return Objects.hash(getName(), getStartDate(), getEndDate(), getVenue(), getPriority(), getStatus(), getTags());
    }

    @Override
    public String toString() {
        return getAsText();
    }
    
    @Override
	public DateTime getStartDate() {
		return startDate;
	}

	public void setStartDate(DateTime startDate) {
		this.startDate = startDate;
	}
	
    @Override
	public DateTime getEndDate() {
		return endDate;
	}

	public void setEndDate(DateTime endDate) {
		this.endDate = endDate;
	}
	
    @Override
	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}
	
    @Override
	public Priority getPriority() {
		return priority;
	}

	public void setPriority(Priority priority) {
		this.priority = priority;
	}
	
	public void addTag(Tag tag) throws DuplicateTagException {
		this.tags.add(tag);
	}

	public void setVenue(Venue venue) {
		this.venue = venue;
	}

}
