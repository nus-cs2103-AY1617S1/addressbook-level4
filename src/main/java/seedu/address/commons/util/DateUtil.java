package seedu.address.commons.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.List;

import com.joestelmach.natty.*;

/**
 * Utility class to parse strings into Dates with checks on inputs and outputs
 */
//@@author A0139817U
public class DateUtil {
	// Format for displaying dates
	public static final SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
	public static final SimpleDateFormat dateFormatWithTime = new SimpleDateFormat("dd.MM.yyyy hh:mma");
	
	// Valid words that can be used to describe dates. Unsigned integers are valid as well.
	// Used to check if the user date inputs are valid.
	public static final HashSet<String> validWords;
	
	// Words used to describe a date relative to the current date.
	// Used to check if the user date inputs are relative.
	public static final HashSet<String> relativeWords;
	
	// Putting the list of valid and relative words into hashsets for faster access
	static { 
		validWords = new HashSet<String>();
		String[] validWordsArray = {
			"today", "tomorrow", "tmr", "next", "this", "following", "the", "day", "before", "after", "from",  			// Descriptors
			"jan", "january", "feb", "february", "mar", "march", "apr", "april", "may", "jun", "june", 					// Months
			"jul", "july", "aug", "august", "sep", "september", "oct", "october", "nov", "november", "dec", "december",
			"mon", "monday", "tue", "tues", "tuesday", "wed", "wednesday", "thu", "thurs", "thursday", "fri", "friday", // Days of the week 
			"sat", "saturday", "sun", "sunday", 										
			"1st", "2nd", "3rd", "4th", "5th", "6th", "7th", "8th", "9th", "10th", 										// Days of the month
			"11th", "12th", "13th", "14th", "15th", "16th", "17th", "18th", "19th", "20th",
			"21st", "22nd", "23rd", "24th", "25th", "26th", "27th", "28th", "29th", "30th", "31st",
			"pm", "am"  // Time
		};
		for (int i = 0; i < validWordsArray.length; i++) {
			validWords.add(validWordsArray[i]);
		}
		
		relativeWords = new HashSet<String>();
		String[] relativeWordsArray = { 
    			"today", "tdy", "tomorrow", "tmr", "mon", "monday", "tue", "tues", "tuesday", "wed", "wednesday", 
    			"thu", "thurs", "thursday", "fri", "friday", "sat", "saturday", "sun", "sunday"	
    	};
		for (int i = 0; i < relativeWordsArray.length; i++) {
			relativeWords.add(relativeWordsArray[i]);
		}
	}
	
	/**
     * Given a dateString, will return whether the words in the string are all valid words
     */
    public static boolean areValidWords(String dateString) {
    	// Add spaces between numbers and words before processing
    	dateString = StringUtil.addSpacesBetweenNumbersAndWords(dateString).trim().toLowerCase();
    	
    	String[] tokens = dateString.split(" ");
    	for (String token : tokens) {
    		// For it to be valid, token string must either be found in the validWords set 
    		// or it must be an unsigned integer
    		// or it must be a valid time format
    		if (!validWords.contains(token) && !StringUtil.isUnsignedInteger(token) && !isValidTimeFormat(token)) {
    			return false;
    		}
    	}
    	return true;
    }
    
    /**
     * Checks if a string follows a valid time format.
     * A valid time format is 
     * 
     * The follow examples are all valid time formats:
     * 1) Colon or full stop separating hours (0 <= hours < 24) and minutes (0 <= mins < 60). 
     * ("00:00" or "00.00". "23:59" or "23.59").
     * 2) Hours and minutes followed by morning or night descriptor.
     * ("12 am", "12:30am", "1.35 pm", "23.59pm")
     */
    public static boolean isValidTimeFormat(String timeString) {
    	// Time should not have more than 2 tokens
    	if (timeString.split(" ").length > 2) {
    		return false;
    	}
    	
    	// Remove all am and pm decorators
    	timeString = timeString.replaceAll("[a|p][m]", "").trim();
    	
    	String[] splitByColon = timeString.split(":");
    	String[] splitByDot = timeString.split("\\.");
    	
    	// After splitting by ":" or ".", the String array will be of length 2 if it is of format "HH:MM" or "HH.MM"
    	if (splitByColon.length == 2) {
    		// Check that HH and MM are within appropriate range
    		return isWithinHoursRange(splitByColon[0]) && isWithinMinutesRange(splitByColon[1]);
    		
    	} else if (splitByDot.length == 2) {
    		// Check that HH and MM are within appropriate range
    		return isWithinHoursRange(splitByDot[0]) && isWithinMinutesRange(splitByDot[1]);
    		
    	} else if (StringUtil.isUnsignedInteger(timeString)) {
    		// Time is of format "1am", "2pm", "11pm".
    		// Check that it lies between 1 (inclusive) and 12 (inclusive)
    		int val = Integer.parseInt(timeString);
    		return (val >= 1) && (val <= 12);
    		
    	} else {
    		return false;
    	}
    }
    
    /**
     * Checks if a String lies between 0 (inclusive) and 59 (inclusive)
     */
    public static boolean isWithinMinutesRange(String timeString) {
    	try {
    		int val = Integer.parseInt(timeString);
    		return (val >= 0) && (val <= 59);
    	} catch (NumberFormatException nfe) {
    		return false;
    	}
    }
    
    /**
     * Checks if a String lies between 0 (inclusive) and 23 (inclusive)
     */
    public static boolean isWithinHoursRange(String timeString) {
    	try {
    		int val = Integer.parseInt(timeString);
    		return (val >= 0) && (val <= 23);
    	} catch (NumberFormatException nfe) {
    		return false;
    	}
    }
    
    /**
     * Given 2 dates, retrieve the date from the first date and the time from the second date to
     * form a new date
     */
    public static Date retrieveDateTime(Date first, Date second) {
    	Calendar start = Calendar.getInstance();
		start.setTime(first);
		
		Calendar end = Calendar.getInstance();
		end.setTime(second);
		end.set(start.get(Calendar.YEAR), start.get(Calendar.MONTH), start.get(Calendar.DAY_OF_MONTH));
		
		return end.getTime();
    }
	
	/**
     * Checks if a string follows a valid date format.
     * 
     * The following examples are all valid and similar dates: 
     * "Oct 31". "31 Oct".
     * "31 Oct 2016". "Oct 31 2016".
     */
    public static boolean isValidDateFormat(String dateString) {
    	Date date = getDate(dateString);
    	// date has a value (not null) if dateString is a valid format
    	return (date != null);
    }
    
    /**
     * Checks if a string follows a valid format (using "to" or "-") to show start date and end date.
     * 
     * The following examples are all valid and similar illustrations: 
     * "Sep 31 - Oct 31". "31 Sep 2016 - 31 Oct 2016". 
     * "Sep 31 to Oct 31". "31 Sep 2016 to 31 Oct 2016".
     */
    public static boolean isValidStartDateToEndDateFormat(String dateString) {
    	Date[] dates = getStartAndEndDates(dateString);
    	// dates is null if dateString is not a valid format
    	if (dates == null) {
    		return false;
    	}
    	Date startDate = dates[0];
    	Date endDate = dates[1];
    	
    	// Format is valid only if end date is after the start date
    	return endDate.after(startDate);
    }
    
    /**
     * After receiving a Date from Natty, do a sanity check to ensure that the Date given by Natty is sensible
     * and not overly flexible by taking the <day> value of the Date and making sure that it exists within the
     * tokens of a date string.
     * 
     * However, if the dateString given to Natty contains relative dates (E.g. "next monday", "tomorrow", "the
     * day after tomorrow") instead of absolute dates (E.g. "31 Oct", "1 August 2017"), we will not need to 
     * perform such checks. 
     * 
     * Caution: This is just a heuristic to check if Natty processed properly
     */
    public static boolean isDateSensible(Date date, String dateString) {
    	// If it is a valid time format, the date will be sensible
    	if (isValidTimeFormat(dateString)) {
    		return true;
    	}
    	
    	String[] tokens = dateString.split(" ");
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		String day = Integer.toString(cal.get(Calendar.DAY_OF_MONTH));
		
    	for (String token : tokens) {
    		// Check if any token is a relative date word
    		if (relativeWords.contains(token)) {
    			return true;
    		}
    		
    		// Removes any 1st/2nd/3rd etc from the date input
    		if (token.replaceAll("st|nd|rd|th", "").equals(day)) {
    			return true;
    		}
    	}
    	return false;
    }

    /**
     * Retrieve the date from a string that represents some date using Natty library.
     */
    public static Date getDate(String dateString) {
    	// Add spaces between numbers and words in order for Natty to process it correctly
    	dateString = StringUtil.addSpacesBetweenNumbersAndWords(dateString).trim().toLowerCase();
    	
    	// Reject if there are any invalid words
    	if (!areValidWords(dateString)) {
    		return null;
    	}
    	
    	// Using the Natty library to parse the dateString
    	Parser parser = new Parser();
    	List<DateGroup> groups = parser.parse(dateString);

    	// String is valid date format only if there is only 1 DateGroup within
    	if (groups.size() != 1) {
    		return null;
    	}
    	
    	DateGroup firstDateGroup = groups.get(0);
    	List<Date> dates = firstDateGroup.getDates();

    	// String is valid date format only if there is only 1 date value within
    	if (dates.size() != 1) {
    		return null;
    	}
    	
    	// Validate that date is sensible before returning
    	if (isDateSensible(dates.get(0), dateString)) {
    		return dates.get(0);
    	} else {
    		return null;
    	}
	}
    
    /**
     * Retrieve the start date and end date from a string that says "from (start date) to/- (end date)".
     * Stores the dates in a Date[] with start date as the 1st value and end date as the 2nd value.
     */
	public static Date[] getStartAndEndDates(String dateString) {
		Date[] dates = new Date[2];
    	String[] splitByTo = dateString.split("(?![cC])to(?!([bB]|(day )))"); // Make sure "to" is not part of "ocTOber" or "TOday"
    	String[] splitByDash = dateString.split("-");
    	
    	// After splitting by "to" or "-", the String array must be of length 2 (hold start date and end date)
    	if (splitByTo.length == 2) {
    		dates[0] = getDate(splitByTo[0]);
    		dates[1] = getDate(splitByTo[1]);
    	} else if (splitByDash.length == 2) {
    		dates[0] = getDate(splitByDash[0]);
    		dates[1] = getDate(splitByDash[1]);
    	} else {
    		return null;
    	}
    	
    	// If either dates are null, it means that dateString is not a valid format, return null
		if (dates[0] == null || dates[1] == null) {
			return null;
		}
		
		/* User may not provide the date for the end date (E.g. "from 1 Nov 5pm - 6pm" instead of "from 1 Nov 5pm - 1 Nov 6pm") 
		 * and this will cause an error because the date is taken as the current date (since "6pm" is the input)
    	 * 
    	 * Manually set the date of the end date to that of the start date
    	 */
    	if (splitByTo.length == 2 && isValidTimeFormat(splitByTo[1])) {
    		dates[1] = retrieveDateTime(dates[0], dates[1]);
    	} else if (splitByDash.length == 2 && isValidTimeFormat(splitByDash[1])) {
    		dates[1] = retrieveDateTime(dates[0], dates[1]);
    	}
		
    	return dates;
	}
}
