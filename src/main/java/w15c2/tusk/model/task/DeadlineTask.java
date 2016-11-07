package w15c2.tusk.model.task;

import java.util.Date;

import w15c2.tusk.commons.util.DateUtil;

//@@author A0139817U
/**
 * A DeadlineTask is a task that holds a date as the deadline
 */
public class DeadlineTask extends Task {

	private Date deadline;

	public DeadlineTask(String descriptionText, Date deadline) {
		this.description = new Description(descriptionText);
		this.deadline = deadline;
	}
		
	/**
	 * Retrieves the deadline that is stored within the task.
	 * 
	 * @return	Deadline of the task.
	 */
	public Date getDeadline() {
		return deadline;
	}
	
	/**
	 * Make an exact duplicate of the task, including its pin and completion status.
	 * 
	 * @return 	Duplicate of the task.
	 */
	@Override
	public DeadlineTask copy() {
		String newDescription = this.description.getContent();
		Date newDeadline = new Date(this.deadline.getTime());
		DeadlineTask newTask = new DeadlineTask(newDescription, newDeadline);
		
		// Copy pin status
		if (this.isPinned()) {
			newTask.setAsPin();
		} else {
			newTask.setAsNotPin();
		}
		
		// Copy completed status
		if (this.isCompleted()) {
			newTask.setAsComplete();
		} else {
			newTask.setAsUncomplete();
		}
		return newTask;
	}
	
	/**
	 * Returns the string representation of the task, given by its description.
	 * 
	 * @return String representation of the task.
	 */
	@Override
	public String toString() {
		return description.toString();
	}
	
	@Override
	// Return the specifics of each task (with or without time)
	public String getTaskDetails(boolean withTime) {
		if (withTime) {
			return String.format("[Deadline Task][Description: %s][Deadline: %s]", 
					description, DateUtil.dateFormatWithTime.format(deadline));
		} else {
			return String.format("[Deadline Task][Description: %s][Deadline: %s]", 
					description, DateUtil.dateFormat.format(deadline));
		}
	}
	
	/**
	 * Checks if the deadline has passed the current time.
	 * 
	 * @return Whether the deadline is over.
	 */
	@Override
	public boolean isOverdue() {
	    return deadline.before(new Date());
	}
}