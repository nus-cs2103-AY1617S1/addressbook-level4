package seedu.whatnow.model.task;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.ocpsoft.prettytime.PrettyTime;

import com.joestelmach.natty.Parser;

import seedu.whatnow.commons.exceptions.IllegalValueException;

public class TaskTime {
	//	public static final String TWENTY_FOUR_HOUR_FORMAT = "HHmm"; //E.g. 2359

	public static final String TWELVE_HOUR_WITH_MINUTES_COLON_REGEX = "([1-12]{1}+):([0-6]{1}[0-9]{1})[am|pm]";
	public static final String TWELVE_HOUR_WITH_MINUTES_COLON_FORMAT = "h:mm a"; //E.g. 1:50 pm
	public static final String TWELVE_HOUR_WITH_MINUTES_DOT_REGEX = "([1-12]{1}+).([0-6]{1}[0-9]{1})[am|pm]";
	public static final String TWELVE_HOUR_WITH_MINUTES_DOT_FORMAT = "h.mm a";	//E.g. 1.45 pm
	public static final String TWELVE_HOUR_WITHOUT_MINUTES_REGEX = "([1-12]{1}+)[am|pm]";
	public static final String TWELVE_HOUR_WITHOUT_MINUTES_FORMAT = "ha";	//E.g. 2pm

	public static final String DATE_NUM_SLASH_WITH_YEAR_FORMAT = "dd/MM/yyyy";
	public static final String DATE_NUM_SLASH_WITH_YEAR_VALIDATION_REGEX = "([0-9]{2}+)/([0-9]{2}+)/([0-9]{4})";

	public static final String DATE_NUM_SLASH_WITH_YEAR_SHORTENED_DAY_FORMAT = "d/MM/yyyy";
	public static final String DATE_NUM_SLASH_WITH_YEAR_SHORTENED_DAY_VALIDATION_REGEX = "([1-9]{1}+)/([0-9]{2}+)/([0-9]{4})";

	public static final String DATE_NUM_SLASH_WITH_YEAR_SHORTENED_MONTH_FORMAT = "dd/M/yyyy";
	public static final String DATE_NUM_SLASH_WITH_YEAR_SHORTENED_MONTH_VALIDATION_REGEX = "([1-9]{2}+)/([1-9]{1}+)/([0-9]{4})";

	public static final String DATE_NUM_SLASH_WITH_YEAR_SHORTENED_DAY_AND_MONTH_FORMAT = "d/M/yyyy";
	public static final String DATE_NUM_SLASH_WITH_YEAR_SHORTENED_DAY_AND_MONTH_VALIDATION_REGEX = "([1-9]{1}+)/([1-9]{1}+)/([0-9]{4})";

	public static final String DATE_NUM_SLASH_WITHOUT_YEAR_FORMAT = "dd/MM";
	public static final String DATE_NUM_SLASH_WITHOUT_YEAR_VALIDATION_REGEX = "([1-9]{2}+)/([0-9]{2}+))";

	public static final String DATE_NUM_SLASH_WITHOUT_YEAR_SHORTENED_DAY_FORMAT = "d/MM";
	public static final String DATE_NUM_SLASH_WITHOUT_YEAR_SHORTENED_DAY_VALIDATION_REGEX = "([1-9]{1}+)/([0-9]{2}+)";

	public static final String DATE_NUM_SLASH_WITHOUT_YEAR_SHORTENND_DAY_MONTH_FORMAT = "d/M";
	public static final String DATE_NUM_SLASH_WITHOUT_YEAR_SHORTENED_DAY_MONTH_REGEX = "([1-9]{1}+)/([1-9]{1}+)";

	public static final String DATE_AlPHA_WHITESPACE_WITH_YEAR_FORMAT = "dd MMMM yyyy ";
	public static final String DATE_ALPHA_WHITESPACE_WITHOUT_YEAR_FORMAT = "dd MMMM";	

	public static final String DATE_NUM_REGEX_WITH_YEAR= "(0?[1-9]|[12][0-9]|3[01])/(0?[1-9]|1[012])/((19|20)\\d\\d)";

	public static ArrayList<String> ListOfDateRegex;
	public static ArrayList<String> ListOfDateFormat;
	public static ArrayList<String>	ListOfTimeRegex;
	public static ArrayList<String>	ListOfTimeFormat;
	public final String INVALID_TIME_MESSAGE = "Entered an invalid time format";
	public final String INVALID_TIME_RANGE_MESSAGE = "Entered an invalid time range format";
	public final String INVALID_DATE_MESSAGE = "Entered an invalid date format";
	public final String INVALID_DATE_RANGE_MESSAGE = "Entered and invalid date range format";

	private String time;
	private String startTime;
	private String endTime;
	private String date;
	private String startDate;
	private String endDate;

	public TaskTime(String time, String startTime, String endTime, String date, String startDate, String endDate)  throws IllegalValueException{
		ListOfDateRegex = new ArrayList<String>();
		ListOfDateRegex.add(DATE_NUM_SLASH_WITH_YEAR_VALIDATION_REGEX);	ListOfDateRegex.add(DATE_NUM_SLASH_WITH_YEAR_SHORTENED_DAY_VALIDATION_REGEX);	
		ListOfDateRegex.add(DATE_NUM_SLASH_WITH_YEAR_SHORTENED_MONTH_VALIDATION_REGEX);	ListOfDateRegex.add(DATE_NUM_SLASH_WITH_YEAR_SHORTENED_DAY_AND_MONTH_VALIDATION_REGEX);
		ListOfDateRegex.add(DATE_NUM_SLASH_WITHOUT_YEAR_VALIDATION_REGEX);	ListOfDateRegex.add(DATE_NUM_SLASH_WITHOUT_YEAR_SHORTENED_DAY_VALIDATION_REGEX);	
		ListOfDateRegex.add(DATE_NUM_SLASH_WITHOUT_YEAR_SHORTENED_DAY_MONTH_REGEX);	

		ListOfDateFormat = new ArrayList<String>();
		ListOfDateFormat.add(DATE_NUM_SLASH_WITH_YEAR_FORMAT);	ListOfDateFormat.add(DATE_NUM_SLASH_WITH_YEAR_SHORTENED_DAY_FORMAT);
		ListOfDateFormat.add(DATE_NUM_SLASH_WITH_YEAR_SHORTENED_MONTH_FORMAT);	ListOfDateFormat.add(DATE_NUM_SLASH_WITH_YEAR_SHORTENED_DAY_AND_MONTH_FORMAT);
		ListOfDateFormat.add(DATE_NUM_SLASH_WITHOUT_YEAR_FORMAT);	ListOfDateFormat.add(DATE_NUM_SLASH_WITHOUT_YEAR_SHORTENED_DAY_FORMAT);
		ListOfDateFormat.add(DATE_NUM_SLASH_WITHOUT_YEAR_SHORTENND_DAY_MONTH_FORMAT);

		ListOfTimeRegex = new ArrayList<String>();
		ListOfTimeRegex.add(TWELVE_HOUR_WITH_MINUTES_COLON_REGEX); ListOfTimeRegex.add(TWELVE_HOUR_WITH_MINUTES_DOT_REGEX);
		ListOfTimeRegex.add(TWELVE_HOUR_WITHOUT_MINUTES_REGEX);

		ListOfTimeFormat = new ArrayList<String>();
		ListOfTimeFormat.add(TWELVE_HOUR_WITH_MINUTES_COLON_FORMAT); ListOfTimeFormat.add(TWELVE_HOUR_WITH_MINUTES_DOT_FORMAT);
		ListOfTimeFormat.add(TWELVE_HOUR_WITHOUT_MINUTES_FORMAT);

		if(!isValidDate(date, 0)) {
			throw new IllegalValueException(INVALID_DATE_MESSAGE);
		}
		this.date = date;
		if(!isValidDateRange(startDate, endDate)) {
			throw new IllegalValueException(INVALID_DATE_RANGE_MESSAGE);
		}
		this.startDate = startDate;
		this.endDate = endDate;
		if(!isValidTime(time)) {
			throw new IllegalValueException(INVALID_TIME_MESSAGE);
		}
		this.time = time;
		if(!isValidTimeRange(startTime, endTime)) {
			throw new IllegalValueException(INVALID_TIME_RANGE_MESSAGE);
		}
		this.startTime = startTime;
		this.endTime = endTime;
	}
	/** returns time */
	public String getTime() {
		return this.time;
	}
	/** returns startTime */
	public String getStartTime() {
		return this.startTime;
	}
	/** returns endTime */
	public String getEndTime() {
		return this.endTime;
	}
	/** returns currentDate */
	public String getDate() {
		return this.date;
	}
	/** returns startDate */
	public String getStartDate() {
		return this.startDate;
	}
	/** returns endDate */
	public String getEndDate() {
		return this.endDate;
	}

	/**
	 * Checks if a particular time is valid i.e. time is not before current time and is of valid sequence
	 * @return true if valid, else return false
	 */
	public boolean isValidTime(String reqTime) {
		//i.e. not a deadline but a schedule
		if(reqTime == null) {
			return true;
		}else {
			for(int j=0 ; j < ListOfTimeRegex.size() ; j ++) {
				if(reqTime.matches(ListOfTimeRegex.get(j))) {
					return isValidTimeSeq(reqTime, ListOfTimeFormat.get(j));
				}
			}
			return false;
		}
	}
	/**
	 * Checks if a particularTime is indeed valid and is not before current Time and is of valid sequence
	 * 
	 */
	public boolean isValidTimeSeq(String reqTime, String format) {
		//First checks if it is indeed valid:
		Date tempTime = null;
		try {
			DateFormat df = new SimpleDateFormat(format);
			df.setLenient(false);

			tempTime = df.parse(reqTime);
		} catch(ParseException ex) {
			ex.printStackTrace();
			return false;
		}
		//Second check on whether this time is before the current Time
		
		//This check is for e.g. input add "Sth" at 5 pm
		//Gets today's currentTime, if its after then i will get tomorrow's currentTime instead
		if(startDate == null && endDate == null && date == null) {
			
		}
		//checks for todayDate gets current time and compare with input time, returns false if invalid
		else if(date.equals("today")) {
			
		}
		//Performs a normal check
		else if(date.equals("tomorrow")) {
			
		}
		//E.g. add "Sth" on 22/10/2016 to 23/10/2016 (date has been checked if its valid) at 8pm
		else {
			
		}
		return false;
	}
	/**
	 * Checks if a particular time range is valid i.e. startTime is before endTime
	 * @return true if valid, else return false
	 */
	public boolean isValidTimeRange(String beforeTime, String afterTime) {
		//First check for whether the given two times are valid
		
		//Second check for whether the dates are present or not i.e.
		//Add "sth" 8pm to 10pm, we get today, if it has passed, we get tomorrow's date instead
		
		//Third check for whether the beforeTime is indeed before the afterTime
		return false;
	}

	/**
	 * Checks if a particular date is valid i.e. not before currentDate and is a valid sequence 
	 * num is to be used to indicate what date im referring to i.e. 0 for variable date, 1 for variable startDate, 2 for variable endDate
	 * @return true if valid, else return false
	 */
	public boolean isValidDate(String reqDate, int num) {
		if(reqDate == null) {
			return true;
		}
		else if(reqDate.toLowerCase().equals("today")) {
			return true;
		}
		else if(reqDate.toLowerCase().equals("tomorrow")) {
			return true;
		}
		else {
			for(int i = 0 ; i < ListOfDateFormat.size() && i < ListOfDateRegex.size(); i ++) {
				if(reqDate.matches(ListOfDateRegex.get(i))) {
					return isValidNumDate(reqDate, ListOfDateFormat.get(i));
				}
			}
			return false;
		}
	}
	/**
	 * 	Checks if a particular date is of a validFormat and is not before today's date
	 */
	public boolean isValidNumDate(String reqDate, String format) {

		//First check: whether if this date is of a valid format
		Date tempDate = null;
		try {
			DateFormat df = new SimpleDateFormat(format);
			df.setLenient(false);

			tempDate = df.parse(reqDate);
		} catch(ParseException ex) {
			ex.printStackTrace();
			return false;
		}

		//Second check : whether if this date is of the past
		Calendar c = new GregorianCalendar();
		c.set(Calendar.HOUR_OF_DAY, 23);
		c.set(Calendar.MINUTE, 59);
		c.set(Calendar.SECOND, 59);
		Date currDate = c.getTime();
		if(currDate.compareTo(tempDate) > 0) {
			return false;
		}

		return true;
		//The following formats the date to become a dd/mm/yyyy format

		/*
		//E.g. 1/12/2017
		if(format.equals(DATE_NUM_SLASH_WITH_YEAR_SHORTENED_DAY_FORMAT)) {

		}
		//E.g. 11/1/2017
		else if(format.equals(DATE_NUM_SLASH_WITH_YEAR_SHORTENED_MONTH_FORMAT )) {

		}
		//E.g. 1/1/2017
		else if(format.equals(DATE_NUM_SLASH_WITH_YEAR_SHORTENED_DAY_AND_MONTH_FORMAT)) {

		}
		//E.g. 12/12
		else if(format.equals(DATE_NUM_SLASH_WITHOUT_YEAR_FORMAT)) {
			Calendar now = Calendar.getInstance();
			int yearInt = now.get(Calendar.YEAR);
			String year = String.valueOf(yearInt);
			reqDate.concat(year);
		}
		//E.g. 1/12
		else if(format.equals(DATE_NUM_SLASH_WITHOUT_YEAR_SHORTENED_DAY_FORMAT)) {

		}
		//E.g. 1/1
		else if(format.equals(DATE_NUM_SLASH_WITHOUT_YEAR_SHORTENND_DAY_MONTH_FORMAT)) {

		}*/
	}
	/**
	 * Checks if a particular Date range is valid i.e. startDate is before endDate
	 * @return true if range is valid, false if range is invalid
	 */
	//Assume both beforeDate and afterDate is dd/mm/yyyy
	public boolean isValidDateRange(String beforeDate, String afterDate) {
		//Checks if the 2 dates are valid


		//Checks if beforeDate is earlier than afterDate
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		Date startDate = null;
		try {
			startDate = sdf.parse(beforeDate);
		} catch (ParseException e) {

		}
		Date endDate = null;
		try {
			endDate = sdf.parse(afterDate);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return startDate.before(endDate);
	}
}
