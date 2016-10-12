package seedu.todo.model.task;


import java.time.LocalTime;

import seedu.todo.commons.exceptions.IllegalValueException;
import seedu.todo.commons.util.DateTimeUtil;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Represents a Task's from and till date.
 * Guarantees: mutable; is valid as declared in {@link #isValidPhone(String)}
 */
public class TaskDate {

    public static final String MESSAGE_DATETIME_CONSTRAINTS = "Tasks' dates and time need to follow predefined format.";
    
    
    private LocalDate date;
    private LocalTime time;
    
    /**
     * Validates given date and time string.
     *
     * @throws IllegalValueException if given date and time string is invalid.
     */
    public TaskDate(String dateTimeString) throws IllegalValueException {
        if (DateTimeUtil.isNotEmptyDateTimeString(dateTimeString)) {
        	this.date = null;
        	this.time = null;
        } else {
        	
            if (!isValidDate(DateTimeUtil.getDateString(dateTimeString))) {
                throw new IllegalValueException(MESSAGE_DATETIME_CONSTRAINTS);
            }
            this.date = DateTimeUtil.parseDateString(DateTimeUtil.getDateString(dateTimeString));
            
            if(!DateTimeUtil.getTimeString(dateTimeString).isEmpty()) {
            	if (!isValidTime(DateTimeUtil.getTimeString(dateTimeString))) {
            		throw new IllegalValueException(MESSAGE_DATETIME_CONSTRAINTS);
            	}
            	this.time = DateTimeUtil.parseTimeString(DateTimeUtil.getTimeString(dateTimeString));
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
    
    public LocalDate getDate() {
        return this.date;
    }
    
    public LocalTime getTime() {
        return this.time;
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
                || DateTimeUtil.combineLocalDateAndTime(this.date, this.time)
                    .equals(DateTimeUtil.combineLocalDateAndTime(((TaskDate) other).date, ((TaskDate) other).time)));
    }

    @Override
    public int hashCode() {
        return this.toString().hashCode();
    }

}
