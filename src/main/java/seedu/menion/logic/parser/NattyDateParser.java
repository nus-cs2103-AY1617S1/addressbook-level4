package seedu.menion.logic.parser;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.joestelmach.natty.DateGroup;
import com.joestelmach.natty.Parser;

import seedu.menion.model.activity.ActivityTime;
//@@author A0139277U
/**
 * This class is a Parser utility class. It's main purpose is to use the Natty Library to parse
 * dates back to the caller.
 *
 */
public class NattyDateParser {

	public NattyDateParser(){};
	
	/**
	 * This method parses the date from the String argument into a Calendar object which
	 * can be used in other classes.
	 * @param args, dateTimeList
	 * @return an array list with the first index containing the date 18-08-2016 1800 and second index containing the time
	 */
	public static void parseDate (String args, ArrayList<String> dateTimeList) {
		
		Parser parser = new Parser();
		List<DateGroup>groups = parser.parse(args);	
		DateGroup group = groups.get(0);
		List<Date> dates = group.getDates();
		Date date = dates.get(0);
	
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		
		dateTimeList.add(0, formatDate(calendar));
		if (group.isTimeInferred()){
			dateTimeList.add(1, ActivityTime.INFERRED_TIME);
		}
		else {
			dateTimeList.add(1, formatTime(calendar));
		}
			
	}
	
	/**
	 * This method formats the date into a presentable String format.
	 * @param Calendar object cal.
	 * @return a format string in the form of dd-mm-yyyy
	 */
	private static String formatDate (Calendar cal){
		
		int day = cal.get(Calendar.DAY_OF_MONTH);
		int month = cal.get(Calendar.MONTH);
		int year = cal.get(Calendar.YEAR);
		
		// Converts month into XX format
		String monthString;
		if (month + 1 < 10){
			monthString = "0" + Integer.toString(month + 1);
		}
		else {
			monthString = Integer.toString(month + 1);
		}
		
		// Converts day into XX format
		String dayString;
		if (day< 10){
			dayString = "0" + Integer.toString(day);
			
		}
		else {
			dayString = Integer.toString(day);
		}
		
		return (dayString + "-" + monthString + "-" + Integer.toString(year));
	}
		
	/**
	 * This method formats the time into a presentable String format.
	 * @param Calendar object cal.
	 * @return a formatted string in the form of hhmm.
	 */
	private static String formatTime(Calendar cal){
		
		int hour = cal.get(Calendar.HOUR_OF_DAY);
		int min = cal.get(Calendar.MINUTE);
		
		String hourString;
		if (hour < 10){
			hourString =  "0" + Integer.toString(hour);
		}
		else {
			hourString = Integer.toString(hour);
		}
		
		String minString;
		if (min < 10){
			minString = "0" + Integer.toString(min);
		}
		else {
			minString = Integer.toString(min);
		}
		
		return hourString + minString;
		
	}
}
