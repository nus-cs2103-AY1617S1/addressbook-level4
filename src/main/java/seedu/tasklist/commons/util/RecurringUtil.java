//@@author A0142102E
package seedu.tasklist.commons.util;

import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang.time.DateUtils;

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
	public static boolean recurringMatchesRequestedDate(Calendar task, String frequency, Calendar requested) {
	    if (!task.getTime().equals(new Date(0)) && !requested.getTime().equals(new Date (0))) {
	    	Calendar first = (Calendar) task.clone();
	    	
	    	for (int i = 0; i < 30; i++) {
	    		if (frequency.equals("daily")) {
	    			task.set(Calendar.DAY_OF_YEAR, i);
	    	    }
	    		else if (frequency.equals("weekly")) {
	    			task.set(Calendar.WEEK_OF_MONTH, i);
	    	    }
	    		else if (frequency.equals("monthly")) {
	    			task.set(Calendar.MONTH, i);
	    	    }
	    		else if (frequency.equals("yearly")) {
	    			task.set(Calendar.YEAR, i);
	    	    }
	    		if (DateUtils.isSameDay(first, requested)) {
	    			return true;
	    		}
	        }
	    }
	    return false;
	}
}