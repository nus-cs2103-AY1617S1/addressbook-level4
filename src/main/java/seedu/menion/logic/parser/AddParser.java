package seedu.menion.logic.parser;

import com.joestelmach.natty.*;
import seedu.menion.commons.exceptions.IllegalValueException;
import seedu.menion.logic.commands.AddCommand;
import seedu.menion.model.activity.Activity;

import java.text.DateFormatSymbols;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
//@@author A0139277U
public class AddParser {

	public AddParser() {
	};

	private static final Pattern REGULAR_TASK_REGEX = Pattern
			.compile("(.+)[\\ ]*?by[\\ ]*?:[\\ ]*?(.+)[\\ ]*?n[\\ ]*?:[\\ ]*?(.+)");
	private static final Pattern EVENTS_FROM_TO_REGEX = Pattern
			.compile("(.+)[\\ ]*?from:[\\ ]*?(.+)[\\ ]*?to[\\ ]*?:[\\ ]*?(.+)[\\ ]*?n[\\ ]*?:[\\ ]*?(.+)");
	private static final Pattern EVENTS_TO_FROM_REGEX = Pattern
			.compile("(.+)[\\ ]*?to:[\\ ]*?(.+)[\\ ]*?from[\\ ]*?:[\\ ]*?(.+)[\\ ]*?n[\\ ]*?:[\\ ]*?(.+)");
	private static final Pattern FLOATING_TASK_REGEX = Pattern
			.compile("(.+)[\\ ]*?n[\\ ]*?:[\\ ]*?(.+)");
		
	
	private static final String REGULAR_TASK = "task";
	private static final String EVENTS = "event";
	private static final String FLOATING_TASK = "floating";
	
	
	private static Matcher matcher;
	private static ArrayList<String> parsedArguments;
	

	/**
	 * This method parses the input command and will check the type of add
	 * command.
	 * 
	 * @param args
	 * @return An array of parsed commands
	 */
	public static ArrayList<String> parseCommand (String args) throws IllegalValueException {

		parsedArguments = new ArrayList<String>();
		checkActivityType(args);

		return parsedArguments;
	}

	/**
	 * This method checks the type of activity based on its arguments and sets
	 * the arguments into an array list.
	 * 
	 * @param args
	 * @throws IllegalValueException 
	 */
	public static void checkActivityType(String args) throws IllegalValueException {

		if (isEvents(args)) {
			parsedArguments.add(Activity.EVENT_TYPE);
			inputEventArguments();
		}

		else if (isTask(args)) {
			parsedArguments.add(Activity.TASK_TYPE);
			inputTaskArguments();
		}

		else if (isFloatingTask(args)){
			parsedArguments.add(Activity.FLOATING_TASK_TYPE);
			inputFloatingTaskArguments();
		}
		else {
			throw new IllegalValueException(AddCommand.MESSAGE_USAGE);
		}

	}
	
	/**
	 * This method parses the date from the String argument into a Calendar object which
	 * can be used in other classes.
	 * @param args, dateTimeList
	 * @return an array list with the first index containing the date and second index containing the time
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
		dateTimeList.add(1, formatTime(calendar));
			
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
	 * Input the arguments into the parsedArguments ArrayList.
	 * list(1) = Floating Task Name
	 * list(2) = Floating Task Notes
	 */
	private static void inputFloatingTaskArguments(){

		parsedArguments.add(1, matcher.group(1).trim());
		parsedArguments.add(2, matcher.group(2).trim());
		
	}
	
	/**
	 * Input the arguments into the parsedArguments ArrayList. 
	 * list(1) = Task Name
	 * list(2) = Task Notes
	 * list(3) = Task Start Date
	 * list(4) = Task Start Time
	 */
	private static void inputTaskArguments() {

		parsedArguments.add(1, matcher.group(1).trim());
		parsedArguments.add(2, matcher.group(3).trim());
		ArrayList<String> dateTimeList = new ArrayList<String>();
		parseDate(matcher.group(2), dateTimeList);
		parsedArguments.add(3, dateTimeList.get(0));
		parsedArguments.add(4, dateTimeList.get(1));		
		
	}

	/**
	 * Input the arguments into the parsedArguments ArrayList.
	 * list(1) = Event Name
	 * list(2) = Event Notes
	 * list(3) = Event Start Date
	 * list(4) = Event Start Time
	 * list(5) = Event End Date
	 * list(6) = Event End Time
	 */
	private static void inputEventArguments() {

		if (matcher.pattern().equals(EVENTS_FROM_TO_REGEX)){

			parsedArguments.add(1, matcher.group(1).trim());
			parsedArguments.add(2, matcher.group(4).trim());
			ArrayList<String> dateTimeList = new ArrayList<String>();
			parseDate(matcher.group(2), dateTimeList);
			parsedArguments.add(3, dateTimeList.get(0));
			parsedArguments.add(4, dateTimeList.get(1));
			parseDate(matcher.group(3), dateTimeList);
			parsedArguments.add(5, dateTimeList.get(0));
			parsedArguments.add(6, dateTimeList.get(1));
			
		}
		
		else {
			
			parsedArguments.add(1, matcher.group(1).trim());
			parsedArguments.add(2, matcher.group(4).trim());
			ArrayList<String> dateTimeList = new ArrayList<String>();
			parseDate(matcher.group(3), dateTimeList);
			parsedArguments.add(3, dateTimeList.get(0));
			parsedArguments.add(4, dateTimeList.get(1));
			parseDate(matcher.group(2), dateTimeList);
			parsedArguments.add(5, dateTimeList.get(0));
			parsedArguments.add(6, dateTimeList.get(1));
			
		}
		

	}

	
	public static Boolean isFloatingTask(String args){
		matcher = FLOATING_TASK_REGEX.matcher(args);
		
		if (matcher.find()){
			return true;
		}
		
		return false;
		
	}
	
	/**
	 * This method checks if the input arguments satisfy the requirements to be
	 * a Task.
	 * 
	 * @return
	 */
	public static Boolean isTask(String args) {
		matcher = REGULAR_TASK_REGEX.matcher(args);

		if (matcher.find()) {
			return true;
		}

		return false;

	}

	/**
	 * This method checks if the input arguments satisfy the requirements to be
	 * a Event.
	 * 
	 * @return
	 */
	public static Boolean isEvents(String args) {
		matcher = EVENTS_FROM_TO_REGEX.matcher(args);

		if (matcher.find()) {
			return true;
		}
		else {
			matcher = EVENTS_TO_FROM_REGEX.matcher(args);
			if (matcher.find()){
				return true;
			}
		}

		return false;

	}

}
