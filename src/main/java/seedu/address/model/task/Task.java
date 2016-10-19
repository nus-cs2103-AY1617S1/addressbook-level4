package seedu.address.model.task;

import seedu.address.commons.util.CollectionUtil;
import seedu.address.model.tag.UniqueTagList;

import java.util.Objects;

/**
 * Represents a Person in the address book.
 * Guarantees: details are present and not null, field values are validated.
 */
public class Task implements ReadOnlyTask {

    private Description description;
    private Time timeStart;
    private Time timeEnd;
    private Priority priority;
    private	boolean completeStatus = false;

    private UniqueTagList tags;

    /**
     * Every field must be present and not null.
     */
    public Task(Description description, Priority priority, Time timeStart, Time timeEnd, UniqueTagList tags) {
        assert !CollectionUtil.isAnyNull(description, priority, timeStart, timeEnd, tags);
        this.description = description;
        this.timeStart = timeStart;
        this.timeEnd = timeEnd;
        this.priority = priority;
        this.tags = new UniqueTagList(tags); // protect internal tags from changes in the arg list
    }

    public Task(Description description, Priority priority, Time timeStart, Time timeEnd, UniqueTagList tags, boolean completeStatus) {
        assert !CollectionUtil.isAnyNull(description, priority, timeStart, timeEnd, tags);
        this.description = description;
        this.timeStart = timeStart;
        this.timeEnd = timeEnd;
        this.priority = priority;
        this.tags = new UniqueTagList(tags); // protect internal tags from changes in the arg list
        this.completeStatus = completeStatus;
    }

    /**
     * Copy constructor.
     */
    public Task(ReadOnlyTask source) {
        this(source.getDescription(), source.getPriority(), source.getTimeStart(), source.getTimeEnd(), source.getTags());
    }

    @Override
    public Description getDescription() {
        return description;
    }

    public void setDescription(Description description){
    	this.description = description;
    }

    public void undoTask(){
    	completeStatus = false;
    }

    public boolean getCompleteStatus(){
		return completeStatus;
    }

    @Override
    public Time getTimeStart() {
        return timeStart;
    }

    public void setTimeStart(Time time){
    	this.timeStart = time;
    }

    @Override
    public Time getTimeEnd() {
        return timeEnd;
    }

    public void setTimeEnd(Time time){
    	this.timeEnd = time;
    }

    @Override
    public Priority getPriority() {
        return priority;
    }

    public void setPriority(Priority priority){
    	this.priority = priority;
    }

    @Override
    public UniqueTagList getTags() {
        return this.tags;
    }

    /**
     * Replaces this person's tags with the tags in the argument tag list.
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
        return Objects.hash(description, priority, timeStart, timeEnd, tags);
    }

    @Override
    public String toString() {
        return getAsText();
    }

}
