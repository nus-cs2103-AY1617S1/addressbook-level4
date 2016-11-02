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
    public static boolean recurringMatchesRequestedDate(Calendar task, String frequency, Calendar requested) {
        if (!task.getTime().equals(new Date(0)) && !requested.getTime().equals(new Date (0))) {
            if (frequency.equals("daily")) {
                return true;
            }
            else if (frequency.equals("weekly") && task.get(Calendar.DAY_OF_WEEK) == requested.get(Calendar.DAY_OF_WEEK)) {
                return true;
            }
            else if (frequency.equals("monthly") && task.get(Calendar.DAY_OF_MONTH) == requested.get(Calendar.DAY_OF_MONTH)) {
                return true;
            }
            else if (frequency.equals("yearly") && task.get(Calendar.DAY_OF_YEAR) == requested.get(Calendar.DAY_OF_YEAR)) {
                return true;
            }
        }
        return false;
    }
}