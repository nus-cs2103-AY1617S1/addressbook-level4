package w15c2.tusk.model.task;

import java.util.Date;

import w15c2.tusk.commons.util.DateUtil;
import w15c2.tusk.model.Copiable;

/**
 * A DeadlineTask is a task that holds a date as the deadline
 */
//@@author A0139817U
public class DeadlineTask extends Task implements PinnableTask, CompletableTask {

	private Date deadline;

	public DeadlineTask(String descriptionText, Date deadline) {
		this.description = new Description(descriptionText);
		this.deadline = deadline;
	}
		
	public Date getDeadline() {
		return deadline;
	}
	
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
	
	@Override
	public boolean isOverdue() {
	    return deadline.before(new Date());
	}
}