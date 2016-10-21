package seedu.address.model.task;

import seedu.address.commons.util.CollectionUtil;
import seedu.address.model.tag.UniqueTagList;

import java.util.Objects;

/**
 * Represents a Task in ForgetMeNot.
 * Guarantees: details are present and not null, field values are validated.
 */
public class Task implements ReadOnlyTask {

    private Name name;
    private Date date;
    private Time start;
    private Time end;
    
    private UniqueTagList tags;

    /**
     * Every field must be present and not null.
     */
    public Task(Name name, Date date, Time start, Time end, UniqueTagList tags) {
        assert !CollectionUtil.isAnyNull(name, date, start, end, tags);
        this.name = name;
        this.date = date;
        this.start = start;
        this.end = end;
        this.tags = new UniqueTagList(tags); // protect internal tags from changes in the arg list
    }

    /**
     * Copy constructor.
     */
    public Task(ReadOnlyTask source) {
        this(source.getName(), source.getDate(), source.getStartTime(), source.getEndTime(), source.getTags());
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
        return start;
    }

    @Override
    public Time getEndTime() {
        return end;
    }

    @Override
    public UniqueTagList getTags() {
        return new UniqueTagList(tags);
    }
    
    @Override
    public String getDone() {
    	return date.getDate();
    }

    public void setName(Name name) {
        this.name = name;
    }
    
    public void setStartTime(Time start) {
        this.start = start;
    }
    
    public void setEndTime(Time end) {
        this.end = end;
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
        return Objects.hash(name, date, start, end, tags);
    }

    @Override
    public String toString() {
        return getAsText();
    }
    
    public void setDone() {
    	System.out.println("done");
    	this.date.setDate("true");
    }
    
    public void setUndone() {
    	System.out.println("undone");
    	this.date.setDate("false");
    }

}
