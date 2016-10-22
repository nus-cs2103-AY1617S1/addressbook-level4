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


    public static final String MESSAGE_ACTIVITYDATE_CONSTRAINTS = "Activity date should be in dd-mm-yyyy format";

    public static final String ACTIVITYDATE_VALIDATION_REGEX = "(0?[0-3][0-9]-[0-1][0-9]-[0-2][0-9][0-9][0-9])";

    public final String value;
    private String month;

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
        extractMonth(date);
        this.value = formatNiceDate(date);
    }

    //@@author: A0139277U
    private void extractMonth(String date){
    	String [] parts = date.split("-");
    	String month = parts[1];
    	month = new DateFormatSymbols().getMonths()[Integer.parseInt(month) - 1];
    	this.month = month;
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
   
    //@@author: A0139277U
    private static String formatNiceDate(String dateToFormat){
    	String [] parts = dateToFormat.split("-");
    	String day = parts[0];
    	String month = parts[1];
    	String year = parts[2];

    	Integer monthInt = Integer.parseInt(month);
    	month = new DateFormatSymbols().getMonths()[monthInt - 1];
    	
    	dateToFormat = day + " " + month + " " + year;
    	
    	return dateToFormat;
    	
    }
    
    public String getMonth(){
    	return this.month;
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
