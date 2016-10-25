package seedu.address.model.task;

import seedu.address.commons.util.CollectionUtil;

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

    /**
     * Every field must be present and not null.
     */
    public Task(Name name, Done done, Time start, Time end) {
        assert !CollectionUtil.isAnyNull(name, done, start, end);
        this.name = name;
        this.done = done;
        this.start = start;
        this.end = end;
    }

    /**
     * Copy constructor.
     */
    public Task(ReadOnlyTask source) {
        this(source.getName(), source.getDone(), source.getStartTime(), source.getEndTime());
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
    
    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ReadOnlyTask // instanceof handles nulls
                && this.isSameStateAs((ReadOnlyTask) other));
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, done, start, end);
    }

    @Override
    public String toString() {
        return getAsText();
    }
    
}
