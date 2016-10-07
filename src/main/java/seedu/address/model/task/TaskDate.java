package seedu.address.model.task;


import java.time.LocalTime;
import java.time.LocalDate;
import java.time.LocalDateTime;
import seedu.address.commons.util.DateTimeUtil;
import seedu.address.commons.exceptions.IllegalValueException;

/**
 * Represents a Task's from and till date.
 * Guarantees: mutable; is valid as declared in {@link #isValidPhone(String)}
 */
public class TaskDate {

    public static final String MESSAGE_DATETIME_CONSTRAINTS = "Tasks' dates and time need to follow predefined format.";
    private static final int DATE_TIME_STRING_DATE_INDEX = 0;
    private static final int DATE_TIME_STRING_TIME_INDEX = 1;
    
    private LocalDate date;
    private LocalTime time;
    
    /**
     * Validates given date and time string.
     *
     * @throws IllegalValueException if given date and time string is invalid.
     */
    public TaskDate(String dateTimeString) throws IllegalValueException {
        if (dateTimeString == null) {
        	this.date = null;
        	this.time = null;
        } else {
        	dateTimeString = dateTimeString.trim();
        	String[] dateAndTime = dateTimeString.split("t");
        	
            if (!isValidDate(dateAndTime[DATE_TIME_STRING_DATE_INDEX])) {
                throw new IllegalValueException(MESSAGE_DATETIME_CONSTRAINTS);
            }
            this.date = DateTimeUtil.parseDateString(dateAndTime[DATE_TIME_STRING_DATE_INDEX]);
            
            if(dateAndTime.length > 1) {
            	if (!isValidTime(dateAndTime[DATE_TIME_STRING_TIME_INDEX])) {
            		throw new IllegalValueException(MESSAGE_DATETIME_CONSTRAINTS);
            	}
            	this.time = DateTimeUtil.parseTimeString(dateAndTime[DATE_TIME_STRING_TIME_INDEX]);
            } else {
            	this.time = null;
            }
        }
    }

    /**
     * Returns true if a given string is able to parse to a valid LocalDate
     */
    public static boolean isValidDate(String dateString) {
    	return DateTimeUtil.isValidDateString(dateString);
    }
    
    /**
     * Returns true if a given string is able to parse to a valid LocalTime
     */
    public static boolean isValidTime(String timeString) {
    	return DateTimeUtil.isValidTimeString(timeString);
    }

    @Override
    public String toString() {
        
    	String dateString, timeString;
    	if (date == null) {
    		dateString = "";
    	} else {
    		dateString = DateTimeUtil.prettyPrintDate(date);
    	}
    	
    	if (time == null) {
    		timeString = "";
    	} else {
    		timeString = DateTimeUtil.prettyPrintTime(time);
    	}
    	
    	return dateString + " " + timeString;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof TaskDate // instanceof handles nulls
                || this.toString().equals(other.toString()));
    }

    @Override
    public int hashCode() {
        return this.toString().hashCode();
    }

}
