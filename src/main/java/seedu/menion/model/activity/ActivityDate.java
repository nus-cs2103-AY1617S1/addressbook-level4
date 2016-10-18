package seedu.menion.model.activity;

import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.text.DateFormatSymbols;

import seedu.menion.commons.exceptions.IllegalValueException;

/**
 * Represents a Activity's date date in the task manager.
 * Guarantees: immutable; is valid as declared in {@link #isValidDeadline(String)}
 */
public class ActivityDate {


    public static final String MESSAGE_ACTIVITYDATE_CONSTRAINTS = "Activity date should be in mm-dd-yy or mm-dd-yyyy format";

    public static final String ACTIVITYDATE_VALIDATION_REGEX = "(0?[0-3][0-9]-[0-1][0-9]-[0-2][0-9][0-9][0-9])";

    public final String value;

    /**
     * Validates given date.
     *
     * @throws IllegalValueException if given date string is invalid.
     */
    public ActivityDate(String date) throws IllegalValueException {
        assert date != null;
        date = date.trim();
        if (!isValidDate(date)) {
            throw new IllegalValueException(MESSAGE_ACTIVITYDATE_CONSTRAINTS);
        }
        this.value = date;
    }

    /**
     * Returns true if a given string is a valid task date.
     */
    public static boolean isValidDate(String test) {
        boolean result = false;
        
        if (test.matches(ACTIVITYDATE_VALIDATION_REGEX)) {
            result = true;
        }
        return result;
    }

    private static String formatNiceDate(String dateToFormat){
    	Pattern p = Pattern.compile("(.+)-(.+)-(.+)");
    	Matcher m = p.matcher(dateToFormat);
    	
    	String day = m.group(0);
    	String month = m.group(1);
    	String year = m.group(2);
    	
    	Integer monthInt = Integer.parseInt(month);
    	month = new DateFormatSymbols().getMonths()[monthInt - 1];
    	
    	dateToFormat = day + " " + month + " " + year;
    	
    	return dateToFormat;
    	
    }
    
    
    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ActivityDate // instanceof handles nulls
                && this.value.equals(((ActivityDate) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
