package seedu.whatnow.model.task;

import seedu.whatnow.commons.exceptions.IllegalValueException;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

import java.text.ParseException;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
/**
 * This class checks for the validity of the user input date by checking with currentDate, and checking if the date range is valid
 * Throws its respective message if the input is invalid
 * @author A0139128A
 *
 */
//@author A0139128A
public class TaskDate {
	public static final String MESSAGE_NAME_CONSTRAINTS = "Task Date should be represented as one of the followings:"
			+ "dd/mm/yy\n" + "day month year\n" + "today\n" + "tomorrow\n";
	public static final String INVALID_TASK_DATE_RANGE_FORMAT = "The task date range is invalid";
	
	public static final String DATE_ALPHA_WITH_YEAR_VALIDATION_REGEX = "([0-9]{2}+[\\w\\.])+([0-9]{4})";	//To be updated
	public static final String DATE_ALPHA_WITHOUT_YEAR_VALIDATION_REGEX = "([0-9]{2}+[\\w\\.])";

	public static final String DATE_NUM_SLASH_WITH_YEAR_VALIDATION_REGEX = "([0-9]{2}+)/([0-9]{2}+)/([0-9]{4})"; //"\\d{2}/\\d{2}/\\d{4}"; //To be updated
	public static final String DATE_NUM_SLASH_WITHOUT_YEAR_VALIDATION_REGEX = "([0-9]{2})/([0-9]{2})";//"\\d{2}/\\d{2}";
	public static final String DATE_NUM_SLASH_WITH_YEAR_VALIDATION_SHORTENED_DAY_REGEX = "([0-9]{1}+)/([0-9]{2}+)/([0-9]{4})";
	public static final String DATE_NUM_SLASH_WITH_YEAR_VALIDATION_SHORTENED_MONTH_REGEX = "([0-9]{2}+)/([1-9]{1}+)/([0-9]{4})";
	public static final String DATE_NUM_SLASH_WITH_YEAR_VALIDATION_SHORTENED_DAY_AND_MONTH_REGEX = "([0-9]{1}+)/([0-9]{1}+)/([0-9]{4})";

	public static final String DATE_NUM_SLASH_WITH_YEAR_FORMAT = "dd/MM/yyyy";
	public static final String DATE_NUM_SLASH_WITHOUT_YEAR_FORMAT = "dd/MM";
	public static final String DATE_NUM_SLASH_WITH_YEAR_SHORTENED_DAY_FORMAT = "d/MM/yyyy";
	public static final String DATE_NUM_SLASH_WITH_YEAR_SHORTENED_MONTH_FORMAT = "dd/M/yyyy";
	public static final String DATE_NUM_SLASH_WITH_YEAR_SHORTENED_DAY_AND_MONTH_FORMAT = "d/M/yyyy";

	public static final String DATE_AlPHA_WHITESPACE_WITH_YEAR_FORMAT = "dd MMMM yyyy ";
	public static final String DATE_ALPHA_WHITESPACE_WITHOUT_YEAR_FORMAT = "dd MMMM";	

	public static final String DATE_NUM_SLASH_WITH_YEAR_VALIDATION_MODIFIED_REGEX =	"^\\d{1,2}\\/\\d{1,2}\\/\\d{4}$";

	public static ArrayList<String> ListOfDateRegex;
	public static ArrayList<String> ListOfDateFormat;

	private String fullDate;	
	private String startDate;
	private String endDate;
	
	public TaskDate(String taskDate) throws IllegalValueException, java.text.ParseException{
		ListOfDateRegex = new ArrayList<String>();
		ListOfDateFormat = new ArrayList<String>();
		ListOfDateRegex.add(DATE_NUM_SLASH_WITH_YEAR_VALIDATION_REGEX);
		ListOfDateRegex.add(DATE_NUM_SLASH_WITH_YEAR_VALIDATION_SHORTENED_DAY_REGEX);
		ListOfDateRegex.add(DATE_NUM_SLASH_WITH_YEAR_VALIDATION_SHORTENED_MONTH_REGEX);
		ListOfDateRegex.add(DATE_NUM_SLASH_WITH_YEAR_VALIDATION_SHORTENED_DAY_AND_MONTH_REGEX);
		ListOfDateRegex.add(DATE_NUM_SLASH_WITHOUT_YEAR_VALIDATION_REGEX);
		ListOfDateRegex.add(DATE_ALPHA_WITH_YEAR_VALIDATION_REGEX);
		ListOfDateRegex.add(DATE_ALPHA_WITHOUT_YEAR_VALIDATION_REGEX);
		ListOfDateFormat.add(DATE_NUM_SLASH_WITH_YEAR_FORMAT);
		ListOfDateFormat.add(DATE_NUM_SLASH_WITH_YEAR_SHORTENED_DAY_FORMAT);
		ListOfDateFormat.add(DATE_NUM_SLASH_WITH_YEAR_SHORTENED_MONTH_FORMAT);
		ListOfDateFormat.add(DATE_NUM_SLASH_WITH_YEAR_SHORTENED_DAY_AND_MONTH_FORMAT);
		ListOfDateFormat.add(DATE_NUM_SLASH_WITHOUT_YEAR_FORMAT);
		ListOfDateFormat.add(DATE_AlPHA_WHITESPACE_WITH_YEAR_FORMAT);
		ListOfDateFormat.add(DATE_ALPHA_WHITESPACE_WITHOUT_YEAR_FORMAT);
		if (taskDate != null) {
			taskDate = taskDate.trim();
			if(!isValidDate(taskDate)) {
				throw new IllegalValueException(MESSAGE_NAME_CONSTRAINTS);
			}
			//Formats the date to be today's date
			if(taskDate.equals("today")) {
				DateFormat dateFormat = new SimpleDateFormat(DATE_NUM_SLASH_WITH_YEAR_FORMAT);
				Calendar cal = Calendar.getInstance();
				taskDate = dateFormat.format(cal.getTime());
				fullDate = taskDate;
			}
			//Formats the date to be tomorrow's date
			else if(taskDate.equals("tomorrow")) {
				DateFormat dateFormat2 = new SimpleDateFormat(DATE_NUM_SLASH_WITH_YEAR_FORMAT);
				Calendar cal2 = Calendar.getInstance();
				cal2.add(Calendar.DATE, 1);
				taskDate = dateFormat2.format(cal2.getTime());
				fullDate= taskDate;
			}
		}
	}
	/*
	 * Validates given date
	 *
	 * @throw IllegalValueException if given date is invalid
	 */
	public TaskDate(String taskDate, String startDate, String endDate) throws IllegalValueException, java.text.ParseException {
		ListOfDateRegex = new ArrayList<String>();
		ListOfDateFormat = new ArrayList<String>();
		ListOfDateRegex.add(DATE_NUM_SLASH_WITH_YEAR_VALIDATION_REGEX);
		ListOfDateRegex.add(DATE_NUM_SLASH_WITH_YEAR_VALIDATION_SHORTENED_DAY_REGEX);
		ListOfDateRegex.add(DATE_NUM_SLASH_WITH_YEAR_VALIDATION_SHORTENED_MONTH_REGEX);
		ListOfDateRegex.add(DATE_NUM_SLASH_WITH_YEAR_VALIDATION_SHORTENED_DAY_AND_MONTH_REGEX);
		ListOfDateRegex.add(DATE_NUM_SLASH_WITHOUT_YEAR_VALIDATION_REGEX);
		ListOfDateRegex.add(DATE_ALPHA_WITH_YEAR_VALIDATION_REGEX);
		ListOfDateRegex.add(DATE_ALPHA_WITHOUT_YEAR_VALIDATION_REGEX);
		ListOfDateFormat.add(DATE_NUM_SLASH_WITH_YEAR_FORMAT);
		ListOfDateFormat.add(DATE_NUM_SLASH_WITH_YEAR_SHORTENED_DAY_FORMAT);
		ListOfDateFormat.add(DATE_NUM_SLASH_WITH_YEAR_SHORTENED_MONTH_FORMAT);
		ListOfDateFormat.add(DATE_NUM_SLASH_WITH_YEAR_SHORTENED_DAY_AND_MONTH_FORMAT);
		ListOfDateFormat.add(DATE_NUM_SLASH_WITHOUT_YEAR_FORMAT);
		ListOfDateFormat.add(DATE_AlPHA_WHITESPACE_WITH_YEAR_FORMAT);
		ListOfDateFormat.add(DATE_ALPHA_WHITESPACE_WITHOUT_YEAR_FORMAT);
		
		System.out.println("Entered here");
		
		if(taskDate == null && startDate != null &&  endDate != null) {
			if(!isValidDateRange(startDate, endDate)) {
				throw new IllegalValueException(INVALID_TASK_DATE_RANGE_FORMAT);
			}
		}
		else {
			if(!isValidDate(taskDate)) {
				throw new IllegalValueException(MESSAGE_NAME_CONSTRAINTS);
			}
			//Formats the date to be today's date
			if(taskDate.equals("today")) {
				DateFormat dateFormat = new SimpleDateFormat(DATE_NUM_SLASH_WITH_YEAR_FORMAT);
				Calendar cal = Calendar.getInstance();
				taskDate = dateFormat.format(cal.getTime());
				fullDate = taskDate;
			}
			//Formats the date to be tomorrow's date
			else if(taskDate.equals("tomorrow")) {
				DateFormat dateFormat2 = new SimpleDateFormat(DATE_NUM_SLASH_WITH_YEAR_FORMAT);
				Calendar cal2 = Calendar.getInstance();
				cal2.add(Calendar.DATE, 1);
				taskDate = dateFormat2.format(cal2.getTime());
				fullDate = taskDate;
			}
		}
	}

	/**
	 * 
	 * @param test is a given user date input
	 * @return the validity of the user date input by passing it to methods of different regex
	 * @throws java.text.ParseException
	 */
	public boolean isValidDate(String reqDate) throws java.text.ParseException {	
		if(reqDate.equals("today") || reqDate.equals("tomorrow")) {
			return true;
		}
		else {
			for(int i = 0 ; i < ListOfDateFormat.size() && i < ListOfDateRegex.size(); i++) {
				if(reqDate.matches(ListOfDateRegex.get(i))) {
					return isValidNumDate(reqDate, ListOfDateFormat.get(i));
				}
			}
			return false;
		}
	}
	/**
	 * This function finds the respective regex that matches the user input and sends to
	 * isValidDateRangeValidator to check if the two dates are really valid
	 * @param startDate is the user input startingDate
	 * @param endDate is the user input endingDate
	 * @return true is valid date range, else false
	 * @throws java.text.ParseException
	 */
	public boolean isValidDateRange(String startDate, String endDate) throws java.text.ParseException {
		for(int i=0 ; i < ListOfDateFormat.size() && i < ListOfDateRegex.size(); i++ ) {
			if((startDate.matches(ListOfDateRegex.get(i)) && (endDate.matches(ListOfDateRegex.get(i))))) {
				return isValidDateRangeValidator(startDate, endDate, ListOfDateFormat.get(i));
			}
		}
		return false;
	}
	public boolean isValidDateRangeValidator(String beforeDate, String afterDate, String format) {
		if(beforeDate == null && afterDate == null) {
			return true;
		}
		boolean validDateRange = false;
		boolean sameDate = false;
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		Date beginDate = null;
		Date finishDate = null;
		try {
			beginDate = sdf.parse(beforeDate);
			finishDate = sdf.parse(afterDate);
			if(beginDate.before(finishDate)) {
				validDateRange = true;
			}
			if(beginDate.equals(finishDate)) {
				sameDate = true;
			}
		} catch (ParseException e) {
			return false;
		}
		Calendar b = new GregorianCalendar();
		b.setTime(beginDate);
		b.set(Calendar.HOUR_OF_DAY, 23);
		b.set(Calendar.MINUTE, 59);
		b.set(Calendar.SECOND, 59);
		beginDate = b.getTime();
		
		Calendar a = new GregorianCalendar();
		a.setTime(beginDate);
		a.set(Calendar.HOUR_OF_DAY, 23);
		a.set(Calendar.MINUTE, 59);
		a.set(Calendar.SECOND, 59);
		finishDate = a.getTime();
		
		//Following checks if the user input date is invalid i.e before today's date
		Calendar c = new GregorianCalendar();
		c.set(Calendar.HOUR_OF_DAY, 00);
		c.set(Calendar.MINUTE, 00);
		c.set(Calendar.SECOND, 00);
		Date currDate = c.getTime();
		
		if(currDate.compareTo(beginDate) > 0 || currDate.compareTo(finishDate) > 0) {
			return false;
		}
		else if(!validDateRange && !sameDate) {
			return false;
		}
		else {
			startDate = beforeDate;
			endDate = afterDate;
			return true;
		}
	}
	/**
	 * 
	 * @param test is the user date input
	 * @param format is the type of format the user has chosen to input
	 * @return the validity of format of the user date input and validity i.e. existence of the date input
	 * @throws java.text.ParseException
	 */
	public boolean isValidAlphaDate(String test, String format) throws java.text.ParseException {
		Date tempDate = null;

		//Following will check if the user input date is valid in terms of numerical value i.e. 32nd november
		try {
			DateFormat dateFormat = new SimpleDateFormat(format);
			tempDate = dateFormat.parse(test);
			if(!test.equals(dateFormat.format(format))) {
				tempDate = null;
			}
		} catch(ParseException ex) {
			ex.printStackTrace();
			return false;
		}
		//Following checks if the user input date is invalid i.e before today's date
		Calendar c = new GregorianCalendar();
		c.set(Calendar.HOUR_OF_DAY, 23);
		c.set(Calendar.MINUTE, 59);
		c.set(Calendar.SECOND, 59);
		Date currDate = c.getTime();
		if(currDate.compareTo(tempDate) > 0) {
			return false;
		}
		//Following ensures that the date format keyed in the user will be converted to DATE_NUM_SLASH_WITH_YEAR_FORMAT
		if(format.equals(DATE_AlPHA_WHITESPACE_WITH_YEAR_FORMAT)) {
			String tempToGetMonth;
			String[] splitted = test.split("\\s+");
			tempToGetMonth = splitted[0];
			Date dateToGetMonth = new SimpleDateFormat("MMMMM", Locale.ENGLISH).parse(tempToGetMonth);
			Calendar cal = Calendar.getInstance();
			cal.setTime(dateToGetMonth);
			int month = cal.get(Calendar.MONTH);
			String requiredMonth = String.valueOf(month);
			String requiredDate = splitted[0];
			requiredDate.concat(requiredMonth);
			requiredDate.concat(splitted[2]);
			fullDate = requiredDate;
			return true;
		}
		else if(format.equals(DATE_ALPHA_WHITESPACE_WITHOUT_YEAR_FORMAT)) {
			String tempToGetMonth;
			String[] splitted = test.split("\\s+");
			tempToGetMonth = splitted[0];
			Date dateToGetMonth = new SimpleDateFormat("MMMMM", Locale.ENGLISH).parse(tempToGetMonth);
			Calendar cal = Calendar.getInstance();
			cal.setTime(dateToGetMonth);
			int month = cal.get(Calendar.MONTH);
			String requiredMonth = String.valueOf(month);
			int yearInt = cal.get(Calendar.YEAR);
			String year = String.valueOf(yearInt);
			String requiredDate = splitted[0];
			requiredDate.concat(year);
			return true;
		}
		else
			return false;
	}
	/**
	 * @param test is the date input by the user
	 * @param format is the type of format that the date input will be tested against with
	 * @return the validity of the user input
	 * @throws java.text.ParseException
	 */
	public boolean isValidNumDate(String test, String format) throws java.text.ParseException {
		Date inputDate = null;
		try {
			DateFormat df = new SimpleDateFormat(format);
			df.setLenient(false);

			inputDate = df.parse(test);
		} catch(ParseException ex) {
			ex.printStackTrace();
			return false;
		}
		
		Calendar d = new GregorianCalendar();
		d.setTime(inputDate);
		d.set(Calendar.HOUR_OF_DAY, 23);
		d.set(Calendar.MINUTE, 59);
		d.set(Calendar.SECOND, 59);
		inputDate = d.getTime();
		//Following checks if the user input date is invalid i.e before today's date
		Calendar c = new GregorianCalendar();
		c.set(Calendar.HOUR_OF_DAY, 00);
		c.set(Calendar.MINUTE, 00);
		c.set(Calendar.SECOND, 00);
		Date currDate = c.getTime();
		
		if(currDate.compareTo(inputDate) > 0) {
			return false;
		}
		//The following will ensure the date format to be DATE_NUM_SLASH_WITH_YEAR_FORMAT
		if(format.equals(DATE_NUM_SLASH_WITHOUT_YEAR_FORMAT)) {
			Calendar now = Calendar.getInstance();
			int yearInt = now.get(Calendar.YEAR);
			String year = String.valueOf(yearInt);
			test.concat(year);
			fullDate = test;
			return true;
		}
		else if(format.equals(DATE_NUM_SLASH_WITH_YEAR_SHORTENED_DAY_FORMAT)) {
			fullDate = "0"+test;
			return true;
		}
		else if(format.equals(DATE_NUM_SLASH_WITH_YEAR_SHORTENED_MONTH_FORMAT)) {
			String toReplaceFullDate = test;
			String[] split = toReplaceFullDate.split("/");
			fullDate = split[0] + "/0" + split[1] +"/" +split[2];
			return true;
		}
		else if(format.equals(DATE_NUM_SLASH_WITH_YEAR_SHORTENED_DAY_AND_MONTH_FORMAT)) {
			String toReplaceFullDate = test;
			String[] split = toReplaceFullDate.split("/");
			fullDate = "0"+ split[0] + "/0" + split[1] + "/" + split[2];
			return true;
		}
		else {
			fullDate = test;
			return true;
		}
	}
	@Override
	public String toString() {
		if(fullDate == null) {
			return startDate + " "  + endDate;
		}
		else {
			return fullDate;
		}
	}
	@Override
	public boolean equals(Object other) {
		return other == this // short circuit if same object
				|| (other instanceof TaskDate // instanceof handles nulls
						&& this.fullDate.equals(((TaskDate) other).fullDate)); // state check
	}
	/** Returns the fullDate */
	public String getDate() {
		return fullDate;
	}
	/** Returns the startDate */
	public String getStartDate() {
		return this.startDate;
	}
	/** Returns the endDate */
	public String getEndDate() {
		return this.endDate;
	}
}