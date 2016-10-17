package seedu.address.model.person;


import java.util.List;

import seedu.address.commons.exceptions.IllegalValueException;

/**
 * Represents a Task's Time in the to-do-list.
 * Guarantees: immutable; is valid as declared in {@link #isValidTime(String)}
 */
public class Time {
    
    public static final String MESSAGE_TIME_CONSTRAINTS = 
            "Time should be in 24hr format. Eg. 2359";
    public static final String TIME_VALIDATION_REGEX = "([01]?[0-9]|2[0-3])[0-5][0-9]";

    public final String value;
    public final java.util.Date startTime;
    public final java.util.Date endTime;

    
    public Time(String time) throws IllegalValueException {
        if (time == null){
            this.value = "";
            startTime = null;
            endTime = null;
            return;
        }
        if (!isValidTime(time)) {
            throw new IllegalValueException(MESSAGE_TIME_CONSTRAINTS);
        }
        this.value = time;
        startTime = null;
        endTime = null;
    }
    
    /**
     * Validates given time.
     *
     * @throws IllegalValueException if given time string is invalid.
     */
    public Time(List<java.util.Date> dateList) throws IllegalValueException {
    	String [] timeStrings = new String [2];
    	for (int i = 0; i < dateList.size(); i++){
        	java.util.Date date = dateList.get(i);
        	timeStrings[i] = date.getDate() + "." + (date.getMonth() + 1) + "." + (date.getYear() + 1900);
        	
        	if (!isValidTime(timeStrings[i])) {
                throw new IllegalValueException(MESSAGE_TIME_CONSTRAINTS);
            }
        }
        
        if (dateList.size() == 1){
        	this.value = timeStrings[0];
        	startTime = dateList.get(0);
        	endTime = null;
        }
        else if (dateList.size() == 2){
        	if (timeStrings[0].equals(timeStrings[1])){
        		this.value = timeStrings[0];
        	}
        	else {
        		this.value = timeStrings[0] + " to " + timeStrings[1];
        	}
        	startTime = dateList.get(0);
        	endTime = dateList.get(1);
        }
        else {
        	this.value = "";
        	startTime = null;
        	endTime = null;
        }
        
    }
    
    
    /**
     * Returns true if a given string is a valid time
     */
    public static boolean isValidTime(String test) {
        return test.equals("") || test.matches(TIME_VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Time // instanceof handles nulls
                && this.value.equals(((Time) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}