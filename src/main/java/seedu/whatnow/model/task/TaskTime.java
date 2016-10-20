package seedu.whatnow.model.task;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.ocpsoft.prettytime.PrettyTime;

import com.joestelmach.natty.Parser;

import seedu.whatnow.commons.exceptions.IllegalValueException;

public class TaskTime {
	//	public static final String TWENTY_FOUR_HOUR_FORMAT = "HHmm"; //E.g. 2359
	public static final String TWELVE_HOUR_WITH_MINUTES_COLON_FORMAT = "h:mm a"; //E.g. 1:50 pm
	public static final String TWELVE_HOUR_WITH_MINUTES_DOT_FORMAT = "h.mm a";	//E.g. 1.45 pm
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

	public final String INVALID_TIME_MESSAGE = "Entered an invalid time format";
	public final String INVALID_TIME_RANGE_MESSAGE = "Entered an invalid time range format";
	public final String INVALID_DATE_MESSAGE = "Entered an invalid date format";
	public final String INVALID_DATE_RANGE_MESSAGE = "Entered and invalid date range format";

	private final String time;
	private final String startTime;
	private final String endTime;
	private final String date;
	private final String startDate;
	private final String endDate;

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

		if(!isValidTime(time)) {
			throw new IllegalValueException(INVALID_TIME_MESSAGE);
		}
		this.time = time;
		if(!isValidTimeRange(startTime, endTime)) {
			throw new IllegalValueException(INVALID_TIME_RANGE_MESSAGE);
		}
		this.startTime = startTime;
		this.endTime = endTime;
		if(!isValidDate(date)) {
			throw new IllegalValueException(INVALID_DATE_MESSAGE);
		}
		this.date = date;
		if(!isValidDateRange(startDate, endDate)) {
			throw new IllegalValueException(INVALID_DATE_RANGE_MESSAGE);
		}
		this.startDate = startDate;
		this.endDate = endDate;
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

			return false;
		}
	}
	/**
	 * Checks if a particular time range is valid i.e. startTime is before endTime
	 * @return true if valid, else return false
	 */
	public boolean isValidTimeRange(String beforeTime, String afterTime) {
		return false;
	}

	/**
	 * Checks if a particular date is valid i.e. not before currentDate and is a valid sequence 
	 * @return true if valid, else return false
	 */
	public boolean isValidDate(String reqDate) {
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
			/*
			DateFormat df1 = new SimpleDateFormat(DATE_NUM_SLASH_WITH_YEAR_FORMAT);	//e.g. 19/10/2016
			DateFormat df2 = new SimpleDateFormat(DATE_NUM_SLASH_WITH_YEAR_SHORTENED_DAY_FORMAT); //e.g. 1/10/2017
			DateFormat df3 = new SimpleDateFormat(DATE_NUM_SLASH_WITH_YEAR_SHORTENED_MONTH_FORMAT); //e.g. 01/1/2017
			DateFormat df4 = new SimpleDateFormat(DATE_NUM_SLASH_WITH_YEAR_SHORTENED_DAY_AND_MONTH_FORMAT);	//e.g. 2/2/2017
			DateFormat df5 = new SimpleDateFormat(DATE_NUM_SLASH_WITHOUT_YEAR_FORMAT);	//e.g. 10/12
			DateFormat df6 = new SimpleDateFormat(DATE_NUM_SLASH_WITHOUT_YEAR_SHORTENED_DAY_FORMAT); // e.g. 1/12
			DateFormat df7 = new SimpleDateFormat(DATE_NUM_SLASH_WITHOUT_YEAR_SHORTEND_DAY_MONTH_FORMAT); //e.g. 1/1
			 */
			for(int i = 0 ; i < ListOfDateFormat.size() && i < ListOfDateRegex.size(); i ++) {
				if(reqDate.matches(ListOfDateRegex.get(0))) {
					return isValidNumDate(reqDate, ListOfDateFormat.get(i));
				}
			}
			return false;
		}
	}
	
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
		
	}
	/**
	 * Checks if a particular Date range is valid i.e. startDate is before endDate
	 * @return true if range is valid, false if range is invalid
	 */
	public boolean isValidDateRange(String beforeDate, String afterDate) {
		return false;
	}
}
