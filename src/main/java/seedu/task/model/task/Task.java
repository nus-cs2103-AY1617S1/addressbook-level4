package seedu.task.model.task;

import seedu.task.commons.util.CollectionUtil;

import java.util.Objects;

/**
 * Represents a Person in the address book.
 * Guarantees: details are present and not null, field values are validated.
 */
public class Task implements ReadOnlyTask {

    private Name name;
    private Description description;
//    private Deadline deadline;
    private Boolean isTaskCompleted;

    /**
     * Every field must be present and not null.
     */
    public Task(Name name, Description description) {
    	this(name, description,false);    
    }
    
    public Task(Name name, Description description, Boolean status) {
        assert !CollectionUtil.isAnyNull(name, description);
        this.name = name;
        this.description = description;
        //this.deadline = deadline;
        isTaskCompleted = status;
    }

    /**
     * Copy constructor.
     */
    public Task(ReadOnlyTask source) {
        this(source.getTask(), source.getDescription());
    }

    @Override
    public Name getTask() {
        return name;
    }

    @Override
    public Description getDescription() {
        return description;
    }
    
/*    @Override
    public Deadline getDeadline() {
        return deadline;
    }*/

    @Override
    public Boolean getTaskStatus() {
        return isTaskCompleted;
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
        return Objects.hash(name, description);
    }

    @Override
    public String toString() {
        return getAsText();
    }

}
