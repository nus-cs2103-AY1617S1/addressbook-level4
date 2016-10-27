package seedu.address.model.task;

import seedu.address.commons.core.Messages;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.logic.commands.CommandResult;
import seedu.address.model.tag.Tag;
import seedu.address.model.tag.UniqueTagList;

import java.util.Iterator;
import java.util.Objects;

/**
 * @@author A0138993L
 * Represents a Task in the end book.
 * Guarantees: details are present and not null, field values are validated.
 */
public class Task implements ReadOnlyTask, ModifyTask, Comparable<Task> {

    private Name name;
    private Date date;
    private Start start;
    private End end;
    private int overdue;
    private int taskCategory;
    private boolean isCompleted;

    private UniqueTagList tags;

    /**
     *@@author A0138993L
     * Every field must be present and not null.
     */
    public Task(Name name, Date date, Start start, End end, int taskCategory, int overdue, UniqueTagList tags) {
        assert !CollectionUtil.isAnyNull(name, date, start, end, tags);
        this.name = name;
        this.date = date;
        this.start = start;
        this.end = end;
        this.taskCategory = taskCategory;
        this.isCompleted = false;
        this.tags = new UniqueTagList(tags); // protect internal tags from changes in the arg list
        if (isOverdue(this.getDate(), this.getEnd()))
        	this.overdue =1;
        else
        	this.overdue =0;
    }

  	/**
  	 * @@author A0138993L
     * Copy constructor for deadline.
     */    
    public Task(Name name, Date date, End end, int taskCategory, int overdue, UniqueTagList tags) {
        this.name = name;
        this.date = date;
        this.start = null;
        this.end = end;
        this.taskCategory = taskCategory;
        this.isCompleted = false;
        this.tags = new UniqueTagList(tags); // protect internal tags from changes in the arg list
        if (isOverdue(this.getDate(), this.getEnd()))
        	this.overdue =1;
        else 
        	this.overdue =0;
    }

    /**
     * @@author A0138993L
     * Copy constructor for todo.
     */    
    public Task(Name name, int taskCategory, UniqueTagList tags) {
        this.name = name;
        this.date = null;
        this.start = null;
        this.end = null;
        this.taskCategory = taskCategory;
        this.isCompleted = false;
        this.overdue =0;
        this.tags = new UniqueTagList(tags); // protect internal tags from changes in the arg list
    }
    
    /**
     * @@author A0138993L
     * Copy constructor.
     */
    public Task(ReadOnlyTask source) {
        this(source.getName(), source.getDate(), source.getStart(), source.getEnd(), source.getTaskCategory(), source.getOverdue(), source.getTags());
    }
    //@@author A0138993L
    public boolean isOverdue(Date checkDate, End checkEnd) {
    	if (checkDate.isAfterCurrentDate(checkDate.toString()) == 0){
    		return true;
    	}
    	else if ((checkDate.isAfterCurrentDate(checkDate.toString()) ==2) &&  (checkEnd.isPastEndTime(checkEnd.toString()))){
    		return true;
    	}
    	else {
    		return false;
    	}
  	}


    @Override
    public int getOverdue() {
    	return overdue;
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
    public int getTaskCategory(){
    	return taskCategory;
    }
    
    
    public boolean getIsCompleted() {
        return isCompleted;
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

    @Override
    public void setTaskCategory(int taskCat) {
    	this.taskCategory = taskCat;
    }
    
    public void setCompleted(boolean completed) {
        this.isCompleted = completed;
    }
    
    @Override
    public void setOverdue(int overdue) {
    	this.overdue = overdue;
    }
    /**
     * Replaces this task's tags with the tags in the argument tag list.
     */
    public void setTags(UniqueTagList replacement) {
        tags.setTags(replacement);
    }
    /**
     * increase this task's tags with the tags in the argument tag list.
     */
    public void addTags(UniqueTagList addOn) {
        tags.mergeFrom(addOn);
    }
    
    
    /**
     * Replaces the specific tag found in this task's tags with the tags in the argument.
     * @return 
     * @throws IllegalValueException 

     */
    public boolean setTags(String specific_tag, String replacement) throws IllegalValueException{        
        Tag tempTag = new Tag(specific_tag);
        System.out.println(tags.contains(tempTag));
        Iterator<Tag> itr = tags.iterator();
        while(itr.hasNext()){
            Tag temp = itr.next();
            if(temp.equals(tempTag)){    
                temp.setTagName(replacement);
                return true;
            }
        }
        return false;
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

    @Override
    public int compareTo(Task other) {
        
        return this.date.compareTo(other.date);
    }
    

}
