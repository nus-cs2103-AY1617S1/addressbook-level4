package seedu.malitio.model.task;

import java.util.Objects;

import seedu.malitio.commons.util.CollectionUtil;
import seedu.malitio.model.tag.UniqueTagList;

public class FloatingTask implements ReadOnlyFloatingTask {

    private Name name;
    private Boolean completed;
    private boolean marked;
    
    private UniqueTagList tags;
    
    //@@author A0129595N
    /**
     * Constructor for floating tasks.
     */
    public FloatingTask(Name name, UniqueTagList tags) {
        assert !CollectionUtil.isAnyNull(name, tags);
        this.name = name;
        this.tags = new UniqueTagList(tags); // protect internal tags from changes in the arg list
        this.completed = false;
        this.marked = false;
    }
    
    public FloatingTask(Name name, Boolean completed, boolean marked, UniqueTagList tags) {
        assert !CollectionUtil.isAnyNull(name, tags);
        this.name = name;
        this.completed = completed;
        this.marked = marked;
        this.tags = new UniqueTagList(tags); // protect internal tags from changes in the arg list
    }

    /**
     * Copy constructor.
     */
    public FloatingTask(ReadOnlyFloatingTask source) {
        this(source.getName(), source.getTags());
        this.completed = source.getCompleted();
        this.marked = source.isMarked();
    }

    @Override
    public Name getName() {
        return name;
    }


    public boolean getCompleted() {
		return this.completed;
	}

	public void setCompleted() {
		this.completed = true;
	}
	
	public boolean isMarked() {
	    return this.marked;
	}
	
	public void setMarked(boolean marked) {
	    this.marked = marked;
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
                || (other instanceof ReadOnlyFloatingTask // instanceof handles nulls
                && this.isSameStateAs((ReadOnlyFloatingTask) other));
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, tags);
    }

    @Override
    public String toString() {
        return getAsText();
    }

}
