package seedu.address.model.task;

import seedu.address.commons.util.CollectionUtil;
import seedu.address.model.tag.UniqueTagList;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map.Entry;

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
    
    //@@author A0135812L
    public Task cloneWithChangedFields(HashMap<Field, Object> changes) {
        Task newTask = new Task(this);
        for(Entry<Field, Object> entry : changes.entrySet()){
            Field field = entry.getKey();
            Object new_value = entry.getValue();
            newTask.set(field.getName(), new_value);
        }
        return newTask;
    }  
    
    private void set(String fieldName, Object newValue) {
        if(fieldName.equalsIgnoreCase("time")){
            assert (Time.class.isInstance(newValue));
            Time newTime = (Time) newValue;
            setTime(newTime);
        }else if(fieldName.equalsIgnoreCase("name")){
            assert (Name.class.isInstance(newValue));
            Name newName = (Name) newValue;
            setName(newName);
        }else if(fieldName.equalsIgnoreCase("description")){
            assert (Description.class.isInstance(newValue));
            Description newDes = (Description) newValue;
            setDescription(newDes);
        }else if(fieldName.equalsIgnoreCase("tags")){
            assert (UniqueTagList.class.isInstance(newValue));
            UniqueTagList newTags = (UniqueTagList) newValue;
            setTags(newTags);
        }else if(fieldName.equalsIgnoreCase("location")){
            assert (Location.class.isInstance(newValue));
            Location newLoc = (Location) newValue;
            setLocation(newLoc);
        }
    }

    public void setTime(Time time){
        this.time = Optional.of(time);
    }
    
    public void setDescription(Description des){
        this.description = des;
    }
    
    public void setName(Name name){
        this.name = name;
    }
    
    public void setLocation(Location loc){
        this.location = loc;
    }
    //@@author
    
}
