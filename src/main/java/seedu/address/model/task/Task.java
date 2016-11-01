package seedu.address.model.task;

import seedu.address.commons.util.CollectionUtil;
import seedu.address.model.tag.UniqueTagList;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map.Entry;

import org.hamcrest.core.IsInstanceOf;

import java.util.Objects;
import java.util.Optional;

/**
 * Represents a Task in the SmartyDo.
 * Guarantees: details are present and not null, field values are validated.
 */
public class Task implements ReadOnlyTask {

    private Name name;
    private Optional<Time> time;
    private Description description;
    private Location location;
    private boolean isCompleted;

    private UniqueTagList tags;

    /**
     * Every field must be present and not null.
     */
    public Task(Name name, Optional<Time> time, Description description, Location location, UniqueTagList tags) {
        assert !CollectionUtil.isAnyNull(name, description, location, tags);

        this.name = name;
        this.time = time;
        this.description = description;
        this.location = location;
        this.tags = new UniqueTagList(tags); // protect internal tags from changes in the arg list
        this.isCompleted = false;
    }

    /**
     * Copy constructor.
     */
    public Task(ReadOnlyTask source) {
        this(source.getName(), source.getTime(), source.getDescription(),
             source.getLocation(), source.getTags(), source.getCompleted());
    }

    /**
     * Load from xml
     */
    public Task(Name name, Optional<Time> time, Description description, Location location,
            UniqueTagList tags, boolean isCompleted) {
        this.name = name;
        this.time = time;
        this.description = description;
        this.location = location;
        this.tags = new UniqueTagList(tags); // protect internal tags from changes in the arg list
        this.isCompleted = isCompleted;
    }

    @Override
    public Name getName() {
        return name;
    }

    @Override
    public Optional<Time> getTime() {
        return time;
    }

    @Override
    public Description getDescription() {
        return description;
    }

    @Override
    public Location getLocation() {
        return location;
    }

    @Override
    public UniqueTagList getTags() {
        return new UniqueTagList(tags);
    }

    @Override
    public boolean getCompleted() {
    	return isCompleted;
    }

    public void toggleTaskStatus() {
        this.isCompleted = !this.isCompleted;
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
        return Objects.hash(name, time, description, location, tags);
    }

    @Override
    public String toString() {
        return getAsText();
    }

    @Override
    public TaskType getTaskType() {
        if (time.isPresent()) {
            if (time.get().getUntimedStatus()){
                return TaskType.UNTIMED;
            } else if (!time.get().getEndDate().isPresent()) {
                return TaskType.DEADLINE;
            } else if (time.get().getEndDate().get().toLocalDate()
                    .equals(time.get().getStartDate().toLocalDate())){
                return TaskType.TIMERANGE;
            } else {
                return TaskType.EVENT;
            }
        } else {
            return TaskType.FLOATING;
        }
    }

    public Task setFields(HashMap<Field, Object> changes) throws IllegalArgumentException, IllegalAccessException, NoSuchFieldException, SecurityException {
        for(Entry<Field, Object> entry : changes.entrySet()){
            Field field = entry.getKey();
            Object new_value = entry.getValue();
            if(new_value instanceof Time){
                new_value = Optional.of(new_value);
            }
            Task.class.getDeclaredField(field.getName()).set(this, new_value);
        }
        return this;
    }    
    
}
