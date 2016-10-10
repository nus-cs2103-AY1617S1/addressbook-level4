package seedu.address.model.task;

import java.util.Date;

/*
 * An EventTask is a task that holds a start date and an end date
 */
public class EventTask extends Task implements FavoritableTask {

	private boolean isFavorite = false;
	private Date startDate;
	private Date endDate;

	public EventTask(String descriptionText, Date startDate, Date endDate) {
		this.description = new Description(descriptionText);
		this.startDate = startDate;
		this.endDate = endDate;
	}
	
	
	@Override
	public void setIsFavorite(boolean isFavorite) {
		this.isFavorite = isFavorite;
	}

	@Override
	public boolean getIsFavorite() {
		return isFavorite;
	}
	
	public Date getStartDate() {
		return startDate;
	}
	
	public Date getEndDate() {
		return endDate;
	}
}