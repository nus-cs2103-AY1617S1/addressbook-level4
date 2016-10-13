package seedu.address.model.task;

import seedu.address.commons.util.CollectionUtil;
import seedu.address.model.tag.UniqueTagList;

import java.util.Objects;

/**
 * Represents a Person in the end book.
 * Guarantees: details are present and not null, field values are validated.
 */
public class Task implements ReadOnlyTask, ModifyTask {

    private Name name;
    private Date date;
    private Start start;
    private End end;

    private UniqueTagList tags;

    /**
     * Every field must be present and not null.
     */
    public Task(Name name, Date date, Start start, End end, UniqueTagList tags) {
        assert !CollectionUtil.isAnyNull(name, date, start, end, tags);
        this.name = name;
        this.date = date;
        this.start = start;
        this.end = end;
        this.tags = new UniqueTagList(tags); // protect internal tags from changes in the arg list
    }
    
    /**
     * Copy constructor for todo.
     */    
    public Task(Name name, UniqueTagList tags) {
        this.name = name;
        this.date = null;
        this.start = null;
        this.end = null;
        this.tags = new UniqueTagList(tags); // protect internal tags from changes in the arg list
    }
    
    /**
     * Copy constructor for deadline.
     */    
    public Task(Name name, Date date, End end, UniqueTagList tags) {
        this.name = name;
        this.date = date;
        this.start = null;
        this.end = end;
        this.tags = new UniqueTagList(tags); // protect internal tags from changes in the arg list
    }


    /**
     * Copy constructor.
     */
    public Task(ReadOnlyTask source) {
        this(source.getName(), source.getDate(), source.getStart(), source.getEnd(), source.getTags());
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
    public Start getStart() {
        return start;
    }

    @Override
    public End getEnd() {
        return end;
    }

    @Override
    public UniqueTagList getTags() {
        return new UniqueTagList(tags);
    }
    
    /**
     * This section contains the setter methods
     */
    
    @Override 
    public void setName(Name name) {
        this.name = name;
    }
    
    @Override 
    public void setDate(Date date) {
        this.date = date;
    }
    
    @Override
    public void setStart(Start start) {
        this.start = start;
    }
    
    @Override
    public void setEnd(End end) {
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
        return Objects.hash(name, date, start, end);//, tags);
    }

    @Override
    public String toString() {
        return getAsText();
    }

}
