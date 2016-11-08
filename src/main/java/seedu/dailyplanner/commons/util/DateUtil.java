package seedu.dailyplanner.commons.util;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import seedu.dailyplanner.logic.parser.nattyParser;
import seedu.dailyplanner.model.task.Date;
import seedu.dailyplanner.model.task.DateTime;
import seedu.dailyplanner.model.task.ReadOnlyTask;
import seedu.dailyplanner.model.task.Time;
//@@author A0146749N
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
        int keyDate = SubStringOfStringAsInt(0,2,keyword);
        int keyMonth = SubStringOfStringAsInt(3,5,keyword);
        int keyYear = SubStringOfStringAsInt(6,keyword);
        Date taskStart = task.getStart().m_date;
        Date taskEnd = task.getEnd().m_date;
        if (startDateTimeAndEndDateTimeIsEmpty(taskStart, taskEnd)) {
            return false;
        } else if (dateTimeIsEmpty(taskStart)) {
            taskStart = taskEnd;
        } else if (dateTimeIsEmpty(taskEnd)) {
            taskEnd = taskStart;
        }
        return isKeyBetweenStartAndEnd(keyDate, keyMonth, keyYear, taskStart, taskEnd);
    }

    private static boolean isKeyBetweenStartAndEnd(int keyDate, int keyMonth, int keyYear, Date taskStart,
            Date taskEnd) {
        Calendar start = Calendar.getInstance();
        start.set(taskStart.m_year + 1900, taskStart.m_month, taskStart.m_day);
        Calendar searchKey = Calendar.getInstance();
        searchKey.set(keyYear + 1900, keyMonth, keyDate);
        Calendar end = Calendar.getInstance();
        end.set(taskEnd.m_year + 1900, taskEnd.m_month, taskEnd.m_day);
        return (start.compareTo(searchKey) <= 0 && end.compareTo(searchKey) >= 0);
    }

    private static boolean dateTimeIsEmpty(Date taskStart) {
        return taskStart.m_value.equals("");
    }

    private static boolean startDateTimeAndEndDateTimeIsEmpty(Date taskStart, Date taskEnd) {
        return dateTimeIsEmpty(taskStart) && dateTimeIsEmpty(taskEnd);
    }
	
	private static int SubStringOfStringAsInt(int start, int end, String keyword) {
	    return Integer.parseInt(keyword.substring(start, end));
	}
	
	private static int SubStringOfStringAsInt(int start, String keyword) {
        return Integer.parseInt(keyword.substring(start));
    }
    
	/** Returns current time as DateTime object */
     public static DateTime nowAsDateTime() {
            nattyParser natty = new nattyParser();
            String dateTimeAsString = natty.parse(STRING_REPRESENTING_NOW);
            return getDateTimeFromString(dateTimeAsString);
        }

    private static DateTime getDateTimeFromString(String dateTimeAsString) {
        String[] dateTimeArray = dateTimeAsString.split(" ");
        Date nowDate = new Date(dateTimeArray[0]);
        Time nowTime = new Time(dateTimeArray[1]);
        return new DateTime(nowDate,nowTime);
    }
     
    public static int convertTo24HrFormat(Time firstTime) {
		if (firstTime.m_meridiem.equals("AM") && firstTime.m_hour == 12) {
			return 0;
		} else if (firstTime.m_meridiem.equals("PM") && firstTime.m_hour != 12) {
			return firstTime.m_hour + 12;
		} else {
			return firstTime.m_hour;
		}
	}
    
    /** Checks if string is in dd/mm/yyyy format 
     * @return */
    public static boolean isValidDayMonthAnd4DigitYearFormat(String date) {
        if (date.matches("([0-9]{2})/([0-9]{2})/([0-9]{4})") || date.matches("([0-9]{1})/([0-9]{2})/([0-9]{4})"))
            return true;
        else
           return false;
    }
    
    /** Checks if string is in dd/mm/yy format 
     * @return */
    public static boolean isValidDayMonthAnd2DigitYearFormat(String date) {
        if (date.matches("([0-9]{2})/([0-9]{2})/([0-9]{2})") || date.matches("([0-9]{1})/([0-9]{2})/([0-9]{2})"))
            return true;
        else
           return false;
    }
    
    /** Converts string from dd/mm/yy to dd/mm/20yy format  */
    public static String convertTo4DigitYearFormat(String date) {
        String dayAndMonth = date.substring(0,6);
        String yy = date.substring(6);
        return dayAndMonth + "20" + yy;
    }

}
