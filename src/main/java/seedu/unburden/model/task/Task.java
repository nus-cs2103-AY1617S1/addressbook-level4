package seedu.unburden.model.task;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Objects;

import seedu.unburden.commons.core.Messages;
import seedu.unburden.commons.exceptions.IllegalValueException;
import seedu.unburden.commons.util.CollectionUtil;
import seedu.unburden.model.tag.Tag;
import seedu.unburden.model.tag.UniqueTagList;
import seedu.unburden.model.tag.UniqueTagList.DuplicateTagException;

/**
 * Represents a Task in the address book.
 * Guarantees: details are present and not null, field values are validated.
 * @@author A0143095H
 */
public class Task implements ReadOnlyTask, Comparable<Task> {

    private Name name;
    private TaskDescription taskD;
    private Date date;
    private Time startTime;
    private Time endTime;
    private UniqueTagList tags;
    private boolean done;
    private String getDoneString;
	private static final SimpleDateFormat DATEFORMATTER = new SimpleDateFormat("dd-MM-yyyy");

    /**
     * Every field must be present and not null.
     * @throws IllegalValueException 
     */
    
    
    public Task(Name name, TaskDescription taskD, Date date, Time startTime, Time endTime, Boolean done,UniqueTagList tags) throws IllegalValueException {
        assert !CollectionUtil.isAnyNull(name, taskD, date, startTime, endTime, tags);
        if(startTime.compareTo(endTime) < 0){
        	throw new IllegalValueException(Messages.MESSAGE_STARTTIME_AFTER_ENDTIME);
        }
        this.name = name;
        this.taskD = taskD;
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
        this.done = done;
        this.tags = tags; // protect internal tags from changes in the arg list
    }
   
    public Task(ReadOnlyTask source) throws IllegalValueException {
        this(source.getName(), source.getTaskDescription(), source.getDate(), source.getStartTime(), source.getEndTime(), source.getDone(),source.getTags());
    }
    //@@Nathanael Chan A0139678J
    // adds event
    public Task(Name name, TaskDescription taskD, Date date, Time startTime, Time endTime, UniqueTagList tags) throws IllegalValueException {
        assert !CollectionUtil.isAnyNull(name, taskD, date, startTime, endTime, tags);
        if(startTime.compareTo(endTime) < 0){
        	throw new IllegalValueException(Messages.MESSAGE_STARTTIME_AFTER_ENDTIME);
        }
        this.name = name;
        this.taskD = taskD;
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
        //this.getDoneString = getDoneString;
        this.tags = tags; // protect internal tags from changes in the arg list
    }
    
    // adds event without description 
    public Task(Name name,Date date, Time startTime, Time endTime, UniqueTagList tags) throws IllegalValueException {
        assert !CollectionUtil.isAnyNull(name, date, startTime, endTime, tags);
        if(startTime.compareTo(endTime) < 0){
        	throw new IllegalValueException(Messages.MESSAGE_STARTTIME_AFTER_ENDTIME);
        }
        this.name = name;
        this.taskD = new TaskDescription("");
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
        this.tags = tags; // protect internal tags from changes in the arg list
    }
    
    // adds deadline
    public Task(Name name, TaskDescription taskD, Date date, Time endTime, UniqueTagList tags) throws IllegalValueException {
		assert!CollectionUtil.isAnyNull(name, taskD, date, endTime, tags);
		this.name = name;
		this.taskD = taskD;
		this.date = date;
		this.startTime = new Time("");
		this.endTime = endTime;
		this.tags = tags;
	}
    
    // adds deadline without task description
    public Task(Name name, Date date, Time endTime, UniqueTagList tags) throws IllegalValueException {
		assert!CollectionUtil.isAnyNull(name, date, endTime, tags);
		this.name = name;
		this.taskD = new TaskDescription("");
		this.date = date;
		this.startTime = new Time("");
		this.endTime = endTime;
		this.tags = tags;
	}
    
    // adds deadline without task description and time
    public Task(Name name, Date date, UniqueTagList tags) throws IllegalValueException {
		assert!CollectionUtil.isAnyNull(name, date, tags);
		this.name = name;
		this.taskD = new TaskDescription("");
		this.date = date;
		this.startTime = new Time("");
		this.endTime = new Time("");
		this.tags = tags;
	}
    
    // adds deadline without task description and date
    public Task(Name name, Time endTime, UniqueTagList tags) throws IllegalValueException {
		assert!CollectionUtil.isAnyNull(name, endTime, tags);
		this.name = name;
		this.taskD = new TaskDescription("");
		this.date = new Date("");
		this.startTime = new Time("");
		this.endTime = endTime;
		this.tags = tags;
	}
    
    // adds deadline without date
    public Task(Name name, TaskDescription taskD, Time endTime, UniqueTagList tags) throws IllegalValueException {
		assert!CollectionUtil.isAnyNull(name, taskD, endTime, tags);
		this.name = name;
		this.taskD = taskD;
		this.date = new Date("");
		this.startTime = new Time("");
		this.endTime = endTime;
		this.tags = tags;
	}
    // adds deadline without time
    public Task(Name name, TaskDescription taskD, Date date, UniqueTagList tags) throws IllegalValueException {
		assert!CollectionUtil.isAnyNull(name, taskD, date, tags);
		this.name = name;
		this.taskD = taskD;
		this.date = date;
		this.startTime = new Time("");
		this.endTime = new Time("");
		this.tags = tags;
	}
    
    // adds floating Task 
    public Task(Name name, TaskDescription taskD, UniqueTagList tags) throws IllegalValueException {
		assert !CollectionUtil.isAnyNull(name, taskD, tags);
		this.name = name;
		this.taskD = taskD;
		this.date = new Date("");
		this.startTime = new Time("");
		this.endTime = new Time("");
		this.tags = tags;
	}

    // adds floating task without task description
	public Task(Name name, UniqueTagList tags) throws IllegalValueException {
		assert!CollectionUtil.isAnyNull(name, tags);
		this.name = name;
		this.taskD = new TaskDescription("");
		this.date = new Date("");
		this.startTime = new Time("");
		this.endTime = new Time("");
		this.tags = tags;
	}
	//@@Nathanael Chan
	
	/**
     * Copy constructor.
     */
	
	//@@Gauri Joshi A0143095H
//    public Task(ReadOnlyTask source) {
//        this(source.getName(), source.getTaskDescription(), source.getDate(), source.getStartTime(), source.getEndTime(), source.getTags());
//    }

	@Override
    public Name getName() {
        return name;
    }
	
	@Override
    public TaskDescription getTaskDescription() {
        return taskD;
    }
    
    @Override
    public Date getDate() {
        return date;
    }
    
    @Override
    public Time getStartTime() {
        return startTime;
    }
    
    @Override
    public Time getEndTime() {
        return endTime;
    }

    @Override
    public UniqueTagList getTags() {
        return new UniqueTagList(tags);
    }
    
    //@@Gauri Joshi A0143095H
	@Override
	public boolean getDone() {
		return done;
	}
	
	public String getDoneString(){
		if(done){
			getDoneString = "Done!";
		}
		else {
			getDoneString = "Undone!";
		}
		return getDoneString;
	}
    //@@Gauri Joshi A0143095H
    /**
     * Replaces this person's tags with the tags in the argument tag list.
     */
    public void setTags(UniqueTagList replacement) {
        tags.setTags(replacement);
    }
    
    public void setName(Name name) {
        this.name = name;
    }
    
    public void setTaskDescription(TaskDescription taskD) {
        this.taskD = taskD;
    }
    
    public void setDate(Date date) {
        this.date = date;
    }
    
    public void setStartTime(Time startTime) {
        this.startTime = startTime;
    }
    
    public void setEndTime(Time endTime) {
        this.endTime = endTime;
    }
    
    public void setDone(boolean done) {
        this.done = done;
    }
    
    public boolean checkOverDue() throws IllegalValueException{
    	Calendar calendar = Calendar.getInstance();
    	calendar.setTime(calendar.getTime());
    	if(this.getDate().compareTo(new Date(DATEFORMATTER.format(calendar.getTime()))) < 0){
    		return true;
    	}
    	return false;
    }
    
    public void setOverdue() throws DuplicateTagException, IllegalValueException{
    	Tag overdue = new Tag("Overdue");
    	if(!this.tags.contains(overdue)){
    		this.tags.add(overdue);
    	}
    }
    
    
    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ReadOnlyTask // instanceof handles nulls
                && this.isSameStateAs((ReadOnlyTask) other));
    }
    
    @Override
    public int compareTo(Task task) {
    	if (this.getDate().compareTo(task.getDate()) == 0) { // two objects have the same date, compare end times
    		if (this.getEndTime().compareTo(task.getEndTime()) == 0) {
    			return this.getStartTime().compareTo(task.getStartTime());
       		}
    		else {
    			return this.getEndTime().compareTo(task.getEndTime());
    		}
    	}
    	else {
    		return this.getDate().compareTo(task.getDate());
    	}
    }

    
    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name,taskD,date,startTime,endTime, tags);
    }
    //@@Gauri Joshi 


    @Override
    public String toString() {
        return getAsText();
    }

}
