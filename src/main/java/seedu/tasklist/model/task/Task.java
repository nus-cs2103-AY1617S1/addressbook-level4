package seedu.tasklist.model.task;

import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Objects;

import seedu.tasklist.commons.exceptions.IllegalValueException;
import seedu.tasklist.commons.util.CollectionUtil;
import seedu.tasklist.model.tag.UniqueTagList;

/**
 * Represents a Person in the address book.
 * Guarantees: details are present and not null, field values are validated.
 */
public class Task implements ReadOnlyTask {

	private static int currentID = 0;

	private TaskDetails taskDetails;
	private StartTime startTime;
	private EndTime endTime;
	private Priority priority;
	private Recurring recurring;
	private int uniqueID;
	private boolean isComplete;
	private UniqueTagList tags;
	public static int floatCounter;
	public static int IncompleteCounter;
	public static int overdueCounter;
	
	/**
	 * Every field must be present and not null.
	 */
	public Task(TaskDetails taskDetails, StartTime startTime, EndTime endTime, Priority priority, UniqueTagList tags, Recurring recurring) {
		assert !CollectionUtil.isAnyNull(taskDetails, startTime, endTime, priority, tags);
		this.taskDetails = taskDetails;
		this.startTime = startTime;
		this.endTime = endTime;
		this.priority = priority;
		this.uniqueID = currentID++;
		this.tags = new UniqueTagList(tags); // protect internal tags from changes in the arg list
		this.recurring = recurring;
		this.isComplete = false;
	}

	/**
	 * Copy constructor.
	 */
	public Task(ReadOnlyTask source) {
		this(source.getTaskDetails(), source.getStartTime(), source.getEndTime(), source.getPriority(), source.getTags(), source.getRecurring());
	}

	@Override
	public TaskDetails getTaskDetails() {
		return taskDetails;
	}

	@Override
	public StartTime getStartTime() {
		return startTime;
	}

	@Override
	public Priority getPriority() {
		return priority;
	}
	
	@Override
	public Recurring getRecurring() {
	    return recurring;
	}

	@Override
	public int getUniqueID() {
		return uniqueID;
	}

	public void setUniqueId(int newuniqueId){
		uniqueID = newuniqueId;
	}

	@Override
	public UniqueTagList getTags() {
		return new UniqueTagList(tags);
	}

	/**
	 * Replaces this person's tags with the tags in the argument tag list.
	 */
	public void setTags(UniqueTagList replacement) {
		tags.setTags(replacement);
	}
    
	public boolean isRecurring() {
	    return this.recurring.isRecurring();
	}
	
	@Override
	public boolean isComplete() {
		return isComplete;
	}

	public void markAsComplete() {
		isComplete = true;
	}
	
    public void markAsIncomplete() {
        isComplete = false;
    }

	public EndTime getEndTime(){
		return endTime;
	}

	public void setTaskDetails(TaskDetails taskDetails){
		this.taskDetails = taskDetails;
	}

	public void setStartTime(StartTime startTime){
		this.startTime = startTime;
	}

	public void setEndTime(EndTime endTime){
		this.endTime = endTime;
	}

	public void setPriority(Priority priority){
		this.priority = priority;
	}

	public void setRecurringTime() throws IllegalValueException {
	    if (recurring.isRecurring() && isComplete) {
	        Calendar newtime = recurring.setRecurringTime(this.startTime.starttime);
	        setStartTime(new StartTime(newtime.toString()));
	        isComplete = false;
	    }
	}
	
	public boolean isFloating(){
		return endTime.isMissing()&&startTime.isMissing();
	}
	
	@Override
	public boolean equals(Object other) {
		return other == this // short circuit if same object
				|| (other instanceof ReadOnlyTask // instanceof handles nulls
						&& this.isSameStateAs((ReadOnlyTask) other));
	}

	public boolean isOverDue(){
		if(!isFloating()){
			if(!endTime.endtime.getTime().equals(new Date(0))){
				return endTime.endtime.getTimeInMillis() < System.currentTimeMillis();
			}
			else return false;
		}
		else 
			return false;
	}

	@Override
	public int hashCode() {
		// use this method for custom fields hashing instead of implementing your own
		return Objects.hash(taskDetails, startTime, endTime, priority, uniqueID, tags);
	}

	@Override
	public String toString() {
		return getAsText();
	}
}
