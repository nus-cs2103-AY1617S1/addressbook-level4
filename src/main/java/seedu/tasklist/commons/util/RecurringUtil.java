package seedu.tasklist.commons.util;

import java.util.Calendar;
import java.util.Date;

public class RecurringUtil {
	
	public static Calendar updateRecurringDate(Calendar toUpdate, String frequency) {
		
		if (!toUpdate.getTime().equals(new Date(0))) {
		    switch (frequency) {
		    case "daily": toUpdate.add(Calendar.DAY_OF_YEAR, 1); break;
		    case "weekly": toUpdate.add(Calendar.WEEK_OF_YEAR, 1); break;
		    case "monthly": toUpdate.add(Calendar.MONTH, 1); break;
		    case "yearly": toUpdate.add(Calendar.YEAR, 1); break;
		    }
		}
		
		return toUpdate;
	}
}