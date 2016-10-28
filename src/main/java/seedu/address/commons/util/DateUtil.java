package seedu.address.commons.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

import com.joestelmach.natty.*;

/**
 * Utility class to check if certain date formats are valid
 */
//@@author A0139817U
public class DateUtil {
	// Format for displaying dates
	public static final SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
	
	// Valid words that can be used to describe dates. Unsigned integers are valid as well.
	public static final HashSet<String> validWords;
	
	// Putting the list of valid words into the validWords hashset
	static { 
		validWords = new HashSet<String>();
		String[] validWordsArray = {
			"today", "tdy", "tomorrow", "tmr", "next", "this", "following",  // Descriptors
			"jan", "january", "feb", "february", "mar", "march", "apr", "april", "may", "jun", "june", // Months
			"jul", "july", "aug", "august", "sep", "september", "oct", "october", "nov", "november", "dec", "december",
			"mon", "monday", "tue", "tues", "tuesday", "wed", "wednesday", "thu", "thurs", "thursday", "fri", "friday", // Days of the week
			"st", "nd", "rd", "th", "pm", "am"  // Decorators
		};
		for (int i = 0; i < validWordsArray.length; i++) {
			validWords.add(validWordsArray[i]);
		}
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
     * Retrieve the date from a string that represents some date using Natty library.
     */
    public static Date getDate(String dateString) {
    	// Add spaces between numbers and words in order for Natty to process it correctly
    	dateString = StringUtil.addSpacesBetweenNumbersAndWords(dateString).trim().toLowerCase();
    	String[] tokens = dateString.split(" ");
    	
    	// There should not be more than 3 tokens since there is only <day> <month> <year>
    	if (tokens.length > 3) {
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
    	
    	// Date should be provided by user and not inferred.
    	if (firstDateGroup.isDateInferred()) {
    		return null;
    	}
    	
    	List<Date> dates = firstDateGroup.getDates();

    	// String is valid date format only if there is only 1 date value within
    	if (dates.size() != 1) {
    		return null;
    	}
    	return validateDateIsSensible(dates.get(0), dateString);
	}
    
    /**
     * Given a dateString, will return whether the words in the string are all valid words
     */
    public static boolean areValidWords(String dateString) {
    	// Add spaces between numbers and words before processing
    	dateString = StringUtil.addSpacesBetweenNumbersAndWords(dateString).trim().toLowerCase();
    	
    	String[] tokens = dateString.split(" ");
    	for (String token : tokens) {
    		if (!validWords.contains(token)) {
    			return false;
    		}
    	}
    	return true;
    }
    
    /**
     * After receiving a Date from Natty, do a sanity check to ensure that the Date given by Natty is sensible
     * and not overly flexible by taking the <day> value of the Date and making sure that it exists within the
     * tokens of a date string.
     * 
     * If the date is sensible, return the original date.
     * Else, return null.
     * 
     * Caution: This is just a heuristic to check if Natty processed properly
     */
    public static Date validateDateIsSensible(Date date, String dateString) {
    	String[] tokens = dateString.split(" ");
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		String day = Integer.toString(cal.get(Calendar.DAY_OF_MONTH));
		
    	for (String token : tokens) {
    		// Removes any 1st/2nd/3rd etc from the date input
    		if (token.replaceAll("st|nd|rd|th", "").equals(day)) {
    			return date;
    		}
    	}
    	return null;
    }
    
    /**
     * Retrieve the start date and end date from a string that says "from (start date) to/- (end date)".
     * Stores the dates in a Date[] with start date as the 1st value and end date as the 2nd value.
     */
	public static Date[] getStartAndEndDates(String dateString) {
		Date[] dates = new Date[2];
    	String[] splitByTo = dateString.split("(?![c])to(?![b])"); // Make sure "to" is not part of October
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
    	return dates;
	}
}
