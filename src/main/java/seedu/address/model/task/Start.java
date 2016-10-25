package seedu.address.model.task;


import seedu.address.commons.exceptions.IllegalValueException;

import java.time.LocalTime;

/**
 * @@author A0138993L
 * Represents a task's start time in Simply.
 * Guarantees: immutable; is valid as declared in {@link #isValidStart(String)}
 */
public class Start {

    public static final String MESSAGE_START_CONSTRAINTS = "Task start time can be entered in 24hour or 12hour format.";
    public static final String START_VALIDATION_REGEX = "([01]\\d{1}[0-5]\\d{1})|" +
    													"([2][0-3][0-5]\\d{1})|" +
    													"([1-9](?:pm|am|PM|AM))|" + 
    													"(1[0-2](?:pm|am|PM|AM))|" +
    													"([1-9]\\.[0-5]{1}\\d{1}(?:pm|am|AM|PM))|" +
    													"(1[0-2]\\.[0-5]{1}\\d{1}(?:pm|am|AM|PM))|" +
    													"(no start)";

    public final String value;

    /**
     * Validates given start time.
     * @@author A0138993L
     * @throws IllegalValueException if given start time string is invalid.
     */
    public Start(String start) throws IllegalValueException {
    	//assert start != null;
    	if (start == null) 
    		start = "default"; 
    	start = start.trim();
    	if (!isValidStart(start)) {
    		throw new IllegalValueException(MESSAGE_START_CONSTRAINTS);
    	}
    	if (start.equals("default")) {
    		String new_min = new String(LocalTime.now().getMinute() + "");
    		String new_hr = new String(LocalTime.now().getHour() + "");
    		if (new_hr.length() ==1)
    			new_hr = "0" + new_hr;
    		if (new_min.length() ==1 )
    			new_min = "0" + new_min;
    		this.value = new_hr +""+ new_min;
    	}
    	else if (start.equals("no start"))
    		this.value = "no start";
    	else
    		this.value = changeTo24HourFormat(start);
    	
    }
    //@@author A0138993L
    private String changeTo24HourFormat(String start) {
		if (Character.isDigit(start.charAt(start.length()-1)))
			return start;
		else if (start.length() == 3) {
			if (start.substring(1).equalsIgnoreCase("pm"))
				return (Integer.parseInt(start.substring(0,1))+12) + "00";
			else
				return "0" + start.substring(0, 1) + "00";
		}
		else if (start.length() == 4) {
			if (start.substring(2).equalsIgnoreCase("pm"))
				return (Integer.parseInt(start.substring(0,2))+12) + "00";
			else
				return start.substring(0, 2) + "00";
		}
		else {
			String[] time_cat = start.split("\\.");
			if (time_cat[0].length() ==1)
				time_cat[0] = "0" + time_cat[0];
			if (time_cat[1].substring(2).equalsIgnoreCase("pm")) 
				time_cat[0] = "" + (Integer.parseInt(time_cat[0]) + 12);
			return time_cat[0] + time_cat[1].substring(0, 2);
		}
			
	}

	/**
     * Returns if a given string is a valid task start time.
     */
    public static boolean isValidStart(String test) {
    	if (test.matches(START_VALIDATION_REGEX) || test.equals("default"))
    		return true;
    	else
    		return false;
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Start // instanceof handles nulls
                && this.value.equals(((Start) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
