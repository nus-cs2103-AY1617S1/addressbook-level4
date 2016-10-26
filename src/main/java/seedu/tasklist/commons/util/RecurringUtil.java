//@@author A0142102E
package seedu.tasklist.commons.util;

import java.util.Calendar;
import java.util.Date;

public class RecurringUtil {
	
	/**
	 * Updates the dates of a task based on its recurring frequency.
	 */
	public static Calendar updateRecurringDate(Calendar toUpdate, String frequency, int value) {
		if (!toUpdate.getTime().equals(new Date(0))) {
		    switch (frequency) {
		    case "daily": toUpdate.add(Calendar.DAY_OF_YEAR, value); break;
		    case "weekly": toUpdate.add(Calendar.WEEK_OF_YEAR, value); break;
		    case "monthly": toUpdate.add(Calendar.MONTH, value); break;
		    case "yearly": toUpdate.add(Calendar.YEAR, value); break;
		    }
		}
		return toUpdate;
	}
	
	/**
	 * Checks if the date of the task matches user requested date based on its recurring frequency.
	 */
	public static boolean recurringMatchesDate(Calendar task, String frequency, Calendar requested) {
	    if (!task.getTime().equals(new Date(0)) && !requested.getTime().equals(new Date (0))) {
	    	for (int i = 0; i < 3; i++) {
	    	    switch (frequency) {
	    	    case "daily": return task.DAY_OF_YEAR + i == requested.DAY_OF_YEAR;
	    	    case "weekly": return task.getWeekYear() + i == requested.getWeekYear();
	    	    case "monthly": return task.MONTH + i == requested.MONTH;
	    	    case "yearly": return task.YEAR + i == requested.YEAR;
	    	    }
	        }
	    }
	    return false;
	}
}