package seedu.address.commons.util;

import java.util.Date;

/**
 * Utility class to check if certain date formats are valid
 */
public class DateUtil {
	/**
     * Checks if a string follows a valid date format.
     * 
     * The following examples are all valid and similar dates: 
     * "Oct 31". "31 Oct".
     * "31 Oct 2016". "2016 Oct 31".
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
     * Retrieve the date from a string that represents some date.
     */
    public static Date getDate(String dateString) {
		return null;
	}
    
    /**
     * Retrieve the start date and end date from a string that says "from (start date) to/- (end date)".
     * Stores the dates in a Date[] with start date as the 1st value and end date as the 2nd value.
     */
	public static Date[] getStartAndEndDates(String dateString) {
		Date[] dates = new Date[2];
    	String[] splitByTo = dateString.split("to");
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
