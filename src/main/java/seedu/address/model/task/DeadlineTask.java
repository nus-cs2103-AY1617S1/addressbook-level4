package seedu.address.model.task;

import java.util.Date;

/*
 * A DeadlineTask is a task that holds a date as the deadline
 */
public class DeadlineTask extends Task implements FavoritableTask {

	private boolean isFavorite = false;
	private Date deadline;

	public DeadlineTask(String descriptionText, Date deadline) {
		this.description = new Description(descriptionText);
		this.deadline = deadline;
	}
	
	
	@Override
	public void setIsFavorite(boolean isFavorite) {
		this.isFavorite = isFavorite;
	}

	@Override
	public boolean getIsFavorite() {
		return isFavorite;
	}
	
	public Date getDeadline() {
		return deadline;
	}
}