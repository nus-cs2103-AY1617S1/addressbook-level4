package seedu.task.model.item;

import seedu.task.commons.exceptions.IllegalValueException;
import seedu.task.commons.util.CollectionUtil;

import java.util.Comparator;
import java.util.Objects;
import java.util.Optional;

/**
 * Represents a Task in the task book.
 * Implementations should guarantee:    Details are present and not null, with the exception of Deadline field. 
 *                                      Field values are validated.
 */

public class Task implements ReadOnlyTask {
    
    private Name name;
    private Description description;
    private Deadline deadline;
    private Boolean isTaskCompleted;

    /**
     * Name of a task must be present and not null.
     * Fields which are empty are to be null.
     * @throws IllegalValueException 
     */
//
//    public Task(String name, String description, String deadline, boolean status) throws IllegalValueException {
//        assert !CollectionUtil.isAnyNull(name,description,deadline,status);
//        this.name = new Name(name);
//        if (description.isEmpty()) {
//            this.description = new Description(description);
//        } else {
//            this.description = null;
//        } 
//        if (deadline.isEmpty()) {
//            this.deadline = new Deadline(deadline);
//        } else {
//            this.deadline = null;
//        }
//        this.isTaskCompleted = status;
//    }
//    
    public Task (Name name, Description description, Deadline deadline, boolean status) {
        assert !CollectionUtil.isAnyNull(name,status);
        this.name = name;
        this.description = description;
        this.deadline = deadline;
        this.isTaskCompleted = status;

    }

    /**
     * Copy constructor.
     * @throws IllegalValueException 
     */
    public Task(ReadOnlyTask source) {
            this(source.getTask(), source.getDescription().orElse(null), source.getDeadline().orElse(null) , source.getTaskStatus());
    }

    @Override
    public Name getTask() {
        return name;
    }

    @Override
    public Optional<Description> getDescription() {
        return Optional.ofNullable(this.description);
    }
    
   @Override
    public Optional<Deadline> getDeadline() { 
       return Optional.ofNullable(this.deadline);
    }

    @Override
    public Boolean getTaskStatus() {
        return isTaskCompleted;
    }
    
    public void toggleComplete() {
    	isTaskCompleted = !isTaskCompleted;
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

	/**
	 * Sort deadline from earliest to latest
	 * @param o
	 * @return
	 */
	public static Comparator<Task> getAscComparator() {
		//first by deadline
		Comparator<Task> byDeadline = (t1, t2) -> {
			if(!t1.getDeadline().isPresent() && !t2.getDeadline().isPresent())
				return 0;
			// if this is a floating task, it will be on the top
			if(!t1.getDeadline().isPresent())
				return -1;
			if(!t2.getDeadline().isPresent()) 
				return 1;
			
			//if both are not floating tasks 
			return t1.getDeadline().get().compareTo(t2.getDeadline().get());
		};
		
		//then by name
		Comparator<Task> byName = (t1, t2) -> t1.getTask().compareTo(t2.getTask());
		
		return byDeadline.thenComparing(byName);
	}
	
	/**
	 * Sort deadline from latest to earliest
	 * @param o
	 * @return
	 */
	public int sortDesc(Task o) {
		if(!this.getDeadline().isPresent() && !o.getDeadline().isPresent())
			return 0;
		// if this is a floating task, it will be on the top
		if(!this.getDeadline().isPresent())
			return -1;
		// if this is 
		if(!o.getDeadline().isPresent()) 
			return 1;
		return this.getDeadline().get().compareTo(o.getDeadline().get())*(-1);
		
	}

}
