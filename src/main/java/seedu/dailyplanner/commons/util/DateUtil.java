package seedu.dailyplanner.commons.util;

import java.util.Calendar;

import seedu.dailyplanner.logic.parser.nattyParser;
import seedu.dailyplanner.model.task.Date;
import seedu.dailyplanner.model.task.DateTime;
import seedu.dailyplanner.model.task.ReadOnlyTask;
import seedu.dailyplanner.model.task.Time;

//@@author A0146749N
public class DateUtil {

    private static final String STRING_REPRESENTING_NOW = "now";

    /**
     * Checks if given task has both start time and end time
     * 
     * @return true if task has both, false otherwise
     */
    public static boolean hasStartandEndTime(ReadOnlyTask storedTask) {
	Time storedStartTime = storedTask.getStart().getTime();
	Time storedEndTime = storedTask.getEnd().getTime();
	if (!(storedStartTime.toString().equals("")) && !(storedEndTime.toString().equals(""))) {
	    return true;
	}
	return false;
    }

    /**
     * Checks if date given by keyword falls within a task's start and end dates
     * Guaranteed that keyword is of the format DD/MM/YYYY
     */
    public static boolean withinDateRange(ReadOnlyTask task, String keyword) {
	int keyDate = SubStringOfStringAsInt(0, 2, keyword);
	int keyMonth = SubStringOfStringAsInt(3, 5, keyword);
	int keyYear = subStringOfStringAsInt(6, keyword);
	Date taskStart = task.getStart().getDate();
	Date taskEnd = task.getEnd().getDate();
	if (startDateTimeAndEndDateTimeIsEmpty(taskStart, taskEnd)) {
	    return false;
	} else if (dateTimeIsEmpty(taskStart)) {
	    taskStart = taskEnd;
	} else if (dateTimeIsEmpty(taskEnd)) {
	    taskEnd = taskStart;
	}
	return isKeyBetweenStartAndEnd(keyDate, keyMonth, keyYear, taskStart, taskEnd);
    }

    /** Helper method for withinDateRang method */
    private static boolean isKeyBetweenStartAndEnd(int keyDate, int keyMonth, int keyYear, Date taskStart,
	    Date taskEnd) {
	Calendar start = Calendar.getInstance();
	start.set(taskStart.getYear() + 1900, taskStart.getMonth(), taskStart.getDay());
	Calendar searchKey = Calendar.getInstance();
	searchKey.set(keyYear + 1900, keyMonth, keyDate);
	Calendar end = Calendar.getInstance();
	end.set(taskEnd.getYear() + 1900, taskEnd.getMonth(), taskEnd.getDay());
	return (start.compareTo(searchKey) <= 0 && end.compareTo(searchKey) >= 0);
    }

    private static boolean dateTimeIsEmpty(Date taskStart) {
	return taskStart.toString().equals("");
    }

    private static boolean startDateTimeAndEndDateTimeIsEmpty(Date taskStart, Date taskEnd) {
	return dateTimeIsEmpty(taskStart) && dateTimeIsEmpty(taskEnd);
    }

    private static int SubStringOfStringAsInt(int start, int end, String keyword) {
	return Integer.parseInt(keyword.substring(start, end));
    }

    private static int subStringOfStringAsInt(int start, String keyword) {
	return Integer.parseInt(keyword.substring(start));
    }

    /** Returns current time as DateTime object */
    public static DateTime nowAsDateTime() {
	nattyParser natty = new nattyParser();
	String dateTimeAsString = natty.parse(STRING_REPRESENTING_NOW);
	return getDateTimeFromString(dateTimeAsString);
    }

    /**
     * Converts given string into DateTime object. Guaranteed that String is in
     * DD/MM/YYYY HH.MMam or HH.MMpm format
     */
    public static DateTime getDateTimeFromString(String dateTimeAsString) {
	String[] dateTimeArray = dateTimeAsString.split(" ");
	Date nowDate = new Date(dateTimeArray[0]);
	Time nowTime = new Time(dateTimeArray[1]);
	return new DateTime(nowDate, nowTime);
    }

    /**
     * Converts time from 12HR to 24HR format
     */
    public static int convertTo24HrFormat(Time firstTime) {
	if (firstTime.m_meridiem.equals("AM") && firstTime.m_hour == 12) {
	    return 0;
	} else if (firstTime.m_meridiem.equals("PM") && firstTime.m_hour != 12) {
	    return firstTime.m_hour + 12;
	} else {
	    return firstTime.m_hour;
	}
    }

    /**
     * Checks if string is in dd/mm/yyyy format
     */
    public static boolean isValidDayMonthAnd4DigitYearFormat(String date) {
	return (date.matches("([0-9]{2})/([0-9]{2})/([0-9]{4})") || date.matches("([0-9]{1})/([0-9]{2})/([0-9]{4})"));
    }

    /**
     * Checks if string is in dd/mm/yy format
     */
    public static boolean isValidDayMonthAnd2DigitYearFormat(String date) {
	return (date.matches("([0-9]{2})/([0-9]{2})/([0-9]{2})") || date.matches("([0-9]{1})/([0-9]{2})/([0-9]{2})"));

    }

    /** Converts string from dd/mm/yy to dd/mm/20yy format */
    public static String convertTo4DigitYearFormat(String date) {
	String dayAndMonth = date.substring(0, 6);
	String yy = date.substring(6);
	return dayAndMonth + "20" + yy;
    }

    /** Returns the date today as a Date object */
    public static Date todayAsDate() {
	nattyParser natty = new nattyParser();
	return new Date(natty.parseDate("today"));
    }

    /** Returns an empty DateTime object */
    public static DateTime getEmptyDateTime() {
	return new DateTime(new Date(""), new Time(""));
    }

}
