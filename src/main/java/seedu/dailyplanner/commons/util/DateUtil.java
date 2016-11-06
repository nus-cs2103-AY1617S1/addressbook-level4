package seedu.dailyplanner.commons.util;

import java.util.Calendar;

import seedu.dailyplanner.logic.parser.nattyParser;
import seedu.dailyplanner.model.task.Date;
import seedu.dailyplanner.model.task.DateTime;
import seedu.dailyplanner.model.task.ReadOnlyTask;
import seedu.dailyplanner.model.task.Time;

public class DateUtil {
    private static final String STRING_REPRESENTING_NOW = "now";
    
	public static boolean hasStartandEndTime(ReadOnlyTask storedTask) {
		Time storedStartTime = storedTask.getStart().getTime();
		Time storedEndTime = storedTask.getEnd().getTime();

		if (!(storedStartTime.toString().equals("")) && !(storedEndTime.toString().equals("")))
			return true;

		return false;
	}
	
	public static boolean withinDateRange(ReadOnlyTask task, String keyword) {
        int keyDate = Integer.parseInt(keyword.substring(0, 2));
        int keyMonth = Integer.parseInt(keyword.substring(3, 5));
        int keyYear = Integer.parseInt(keyword.substring(6));
        Date taskStart = task.getStart().m_date;
        Date taskEnd = task.getEnd().m_date;
        if (taskStart.m_value.equals("") && taskEnd.m_value.equals("")) {
            return false;
        } else if (taskStart.m_value.equals("")) {
            taskStart = taskEnd;
        } else if (taskEnd.m_value.equals("")) {
            taskEnd = taskStart;
        }
        Calendar start = Calendar.getInstance();
        start.set(taskStart.m_year + 1900, taskStart.m_month, taskStart.m_day);
        Calendar searchKey = Calendar.getInstance();
        searchKey.set(keyYear + 1900, keyMonth, keyDate);
        Calendar end = Calendar.getInstance();
        end.set(taskEnd.m_year + 1900, taskEnd.m_month, taskEnd.m_day);
        return (start.compareTo(searchKey) <= 0 && end.compareTo(searchKey) >= 0);
    }
    
	/** Returns current time as DateTime object */
     public static DateTime nowAsDateTime() {
            nattyParser natty = new nattyParser();
            Date nowDate = new Date(natty.parseDate(STRING_REPRESENTING_NOW));
            Time nowTime = new Time(natty.parseTime(STRING_REPRESENTING_NOW));
            return new DateTime(nowDate,nowTime);
        }
     
     /** Checks if the date of the first argument comes before the second, returns true if so */  
     public static boolean checkDatePrecedence(DateTime first, DateTime second) {
         Date firstDate = first.m_date;
         Calendar firstDateAsCalendar = Calendar.getInstance();
         firstDateAsCalendar.set(firstDate.m_year + 1900, firstDate.m_month, firstDate.m_day);
         
         Date secondDate = second.m_date;
         Calendar secondDateAsCalendar = Calendar.getInstance();
         secondDateAsCalendar.set(secondDate.m_year + 1900, secondDate.m_month, secondDate.m_day);
         
         return firstDateAsCalendar.before(secondDateAsCalendar);
         
     }
     
    /** Checks if time for first DateTime argument is before second, returns true if so */
    public static boolean checkTimePrecendence(DateTime end, DateTime nowAsDateTime) {
        if (end.getTime().compareTo(nowAsDateTime.getTime())<0) {
            return true;
        } else {
            return false;
        }
        
    }

}
