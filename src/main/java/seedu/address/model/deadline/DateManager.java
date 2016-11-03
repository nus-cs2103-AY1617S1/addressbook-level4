package seedu.address.model.deadline;

import java.util.Calendar;

/**
 * Handles calculations with dates.
 * @author A0139097U
 *
 */
public class DateManager {
	
	private Calendar deadline;
	
	/**
	 * Creates a new DateManager.
	 * @param date Date
	 */
	public DateManager(Calendar date) {
		this.deadline = date;
	}
	
	/**
	 * Calculates the number of days remaining to a
	 * deadline. 
	 * @return int number of days
	 */
	public int calculateDaysRemaining() {
		Calendar cal = Calendar.getInstance();
		if(deadline.after(cal)) {
			int diff = (int) (Math.round(((deadline.getTimeInMillis() - cal.getTimeInMillis())) / (double) 86400000));
			return diff;
		}
		return 0;
	}
	
	/**
	 * Checks whether this date is overdue.
	 * @param current
	 * @param toCheck
	 * @return
	 */
	public boolean checkOverdue(){
		Calendar cal = Calendar.getInstance();
		if(cal.after(deadline)){
			return true;
		}
		else{
			return false;
		}
	}
}
