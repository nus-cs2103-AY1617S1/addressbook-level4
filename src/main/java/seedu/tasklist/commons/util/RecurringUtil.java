//@@author A0142102E
package seedu.tasklist.commons.util;

import java.util.Calendar;
import java.util.Date;

public class RecurringUtil {
	
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
}