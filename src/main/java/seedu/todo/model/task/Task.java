package seedu.todo.model.task;

import java.util.Objects;

import seedu.todo.commons.util.CollectionUtil;
import seedu.todo.model.tag.Tag;
import seedu.todo.model.tag.UniqueTagList;

/**
 * Represents a Task in DoDo-Bird
 * Guarantees: all fields are not null, field values are validated.
 */
public class Task implements ReadOnlyTask {

    private Name name;
    private Detail detail;
    private TaskDate onDate;
    private TaskDate byDate; //deadline
    private boolean done;
    private UniqueTagList tags;

    /**
     * Every field must be present and not null.
     */
    public Task(Name name, Detail detail, TaskDate fromDate, TaskDate tillDate) {
        assert !CollectionUtil.isAnyNull(name, detail, fromDate, tillDate);
        this.name = name;
        this.detail = detail;
        this.onDate = fromDate;
        this.byDate = tillDate;
        this.done = false;
        this.tags = new UniqueTagList(); // protect internal tags from changes in the arg list
    }
    
    public Task(Name name, Detail detail, boolean done, TaskDate fromDate, TaskDate tillDate, UniqueTagList tags) {
        assert !CollectionUtil.isAnyNull(name, detail, fromDate, tillDate);
        this.name = name;
        this.detail = detail;
        this.onDate = fromDate;
        this.byDate = tillDate;
        this.done = done;
        this.tags = new UniqueTagList(tags); // protect internal tags from changes in the arg list
    }

    /**
     * Copy constructor.
     */
    public Task(ReadOnlyTask source) {
        this(source.getName(), source.getDetail(), source.isDone(), source.getOnDate(), source.getByDate(), source.getTags());
    }

    @Override
    public Name getName() {
        return this.name;
    }

    @Override
    public Detail getDetail() {
        return this.detail;
    }

    @Override
    public TaskDate getOnDate() {
        return this.onDate;
    }

    @Override
    public TaskDate getByDate() {
        return this.byDate;
    }

    @Override
    public UniqueTagList getTags() {
        return new UniqueTagList(tags);
    }
    
    @Override
    public boolean isDone() {
        return this.done;
    }
    
    
    public void setName(Name n) {
        this.name = n;
    }

    public void setDetail(Detail d) {
        this.detail = d;
    }

    public void setOnDate(TaskDate fd) {
        this.onDate = fd;
    }

    public void setByDate(TaskDate td) {
        this.byDate = td;
    }
    
    public void setIsDone(boolean done) {
        this.done = done;
    }
    
    /**
     * Add a tag to the task's tag list 
     */
    public void addTag(Tag toAdd) throws UniqueTagList.DuplicateTagException {
        tags.add(toAdd);
    }
    
    /**
     * Remove a tag to the task's tag list 
     */
    public void removeTag(Tag toRemove) throws UniqueTagList.TagNotFoundException{
        tags.remove(toRemove);
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
        return Objects.hash(name, detail, onDate, byDate, tags);
    }

    @Override
    public String toString() {
        return getAsText();
    }

}
