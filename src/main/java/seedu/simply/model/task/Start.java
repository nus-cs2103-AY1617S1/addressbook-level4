package seedu.simply.model.task;


import java.time.LocalTime;

import seedu.simply.commons.exceptions.IllegalValueException;

/**
 * @@author A0138993L
 * Represents a task's start time in Simply.
 * Guarantees: immutable; is valid as declared in {@link #isValidStart(String)}
 */
public class Start implements Comparable<Start> {

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
    	this.value = calculateStartTimeValue(start);
    	
    }
    /**
     *@@author A0138993L
     * Calculates the start time of the task
     * @param start the start time from the user input in any format
     * @return standardized format of the start time
     */
	private String calculateStartTimeValue(String start) {
		if (start.equals("default")) {
    		String new_hr = startTimeHour();
    		String new_min = startTimeMin();
    		return new_hr +""+ new_min;
    	}
    	else if (start.equals("no start"))
    		return "no start";
    	else
    		return changeTo24HourFormat(start);
	}
	/**
	 * @@author A0138993L
	 * calculates the local time minutes
	 * @return the local minutes
	 */
	private String startTimeMin() {
		String new_min = new String(LocalTime.now().getMinute() + "");
		if (new_min.length() ==1 )
			new_min = "0" + new_min;
		return new_min;
	}
	/**
	 * @@author A0138993L
	 * calculates the local time hour
	 * @return the local hour
	 */
	private String startTimeHour() {
		String new_hr = new String(LocalTime.now().getHour() + "");
		if (new_hr.length() ==1)
			new_hr = "0" + new_hr;
		return new_hr;
	}
	
    //@@author A0138993L
	/**
	 * changes the user input to 24 hour format
	 * @param start
	 * @return 24 hr clock
	 */
    private String changeTo24HourFormat(String start) {
		if (Character.isDigit(start.charAt(start.length()-1)))
			return start;
		else if (start.length() == 3) {
			return format1DigitStartTime(start);
		}
		else if (start.length() == 4) {
			return format2DigitStartTime(start);
		}
		else {
			return formatGeneralStartTime(start);
		}
			
	}
    
    /**
     * @@author A0138993L
     * formats the general time format of hh.mm to 24 hour format
     * @param start
     * @return 24 hour clock format
     */
	private String formatGeneralStartTime(String start) {
		String[] time_cat = start.split("\\.");
		if (time_cat[0].length() ==1)
			time_cat[0] = "0" + time_cat[0];
		if (time_cat[1].substring(2).equalsIgnoreCase("pm")) 
			time_cat[0] = "" + (Integer.parseInt(time_cat[0]) + 12);
		return time_cat[0] + time_cat[1].substring(0, 2);
	}
    /**
     * @@author A0138993L
     * formats 2 digit start time to 24 hour clock
     * @param start
     * @return 24 hour clock format
     */
	private String format2DigitStartTime(String start) {
		if (start.substring(2).equalsIgnoreCase("pm"))
			return (Integer.parseInt(start.substring(0,2))+12) + "00";
		else
			return start.substring(0, 2) + "00";
	}
	/**
	 * @@author A0138993L
	 * formats 1 digit start time to 24 hour clock
	 * @param start
	 * @return 24 hour clock format
	 */
	private String format1DigitStartTime(String start) {
		if (start.substring(1).equalsIgnoreCase("pm"))
			return (Integer.parseInt(start.substring(0,1))+12) + "00";
		else
			return "0" + start.substring(0, 1) + "00";
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
    
  //@@author A0139430L
    @Override
    public int compareTo(Start o) {  
        if(this.value.compareTo("no start") == 0 & o.toString().compareTo("no start") == 0) 
            return 0;
        else if(this.value.compareTo("no start") == 0)
            return -1;
        else if(o.toString().compareTo("no start") == 0)
            return 1;
            
        return this.value.compareTo(o.toString());
    }

}
