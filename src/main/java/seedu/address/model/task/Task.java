package seedu.address.model.task;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.model.tag.UniqueTagList;

import java.util.Objects;

/**
 * Represents a Task in ForgetMeNot.
 * Guarantees: details are present and not null, field values are validated.
 */
public class Task implements ReadOnlyTask {

    private Name name;
    private Done done;
    private Time start;
    private Time end;
    
    private UniqueTagList tags;

    /**
     * Every field must be present and not null.
     */
    public Task(Name name, Done done, Time start, Time end, UniqueTagList tags) {
        assert !CollectionUtil.isAnyNull(name, done, start, end, tags);
        this.name = name;
        this.done = done;
        this.start = start;
        this.end = end;
        this.tags = new UniqueTagList(tags); // protect internal tags from changes in the arg list
    }

    /**
     * Copy constructor.
     */
    public Task(ReadOnlyTask source) {
        this(source.getName(), source.getDone(), source.getStartTime(), source.getEndTime(), source.getTags());
    }

    @Override
    public Name getName() {
        return name;
    }

    @Override
    public Done getDone() {
        return done;
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

    public void setName(Name name) {
        this.name = name;
    }
    
    public void setStartTime(Time start) {
        this.start = start;
    }
    
    public void setEndTime(Time end) {
        this.end = end;
    }
    
    public void setDone(Done done) {
    	this.done = done;
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
        return Objects.hash(name, done, start, end, tags);
    }

    @Override
    public String toString() {
        return getAsText();
    }
    
    /**
     * @return true if the tasks is past the current time
     * @throws IllegalValueException
     */
    public boolean checkOverdue() throws IllegalValueException {
        if (start.isMissing() && !end.isMissing())
            return end.time.compareTo(new Time("today").time) < 0;
        
        if (!start.isMissing())
            return start.time.compareTo(new Time("today").time) < 0;
        
        return false;
            
    }
    
}
