package seedu.address.model.task;


import java.time.LocalTime;

import seedu.address.commons.exceptions.IllegalValueException;

/**
 * Represents a task's end time in Simply
 * Guarantees: immutable; is valid as declared in {@link #isValidEnd(String)}
 */
public class End {
    
    public static final String MESSAGE_END_CONSTRAINTS = "Task end time can be entered in 24hour or 12hour format.";
    public static final String END_VALIDATION_REGEX = "([01]\\d{1}[0-5]\\d{1})|" +
    												  "([2][0-3][0-5]\\d{1})|" +
    												  "([1-9](?:pm|am|PM|AM))|" + 
    												  "(1[0-2](?:pm|am|PM|AM))|" +
    												  "([1-9]\\.[0-5]{1}\\d{1}(?:pm|am))|" +
    												  "(1[0-2]\\.[0-5]{1}\\d{1}(?:pm|am))|" +
    												  "(no end)";
    public static final String DEFAULT_END_TIME = "2359";
    public final String value;
    private int pastEndTime =0;

    /**
     * Validates given end time.
     *
     * @throws IllegalValueException if given address string is invalid.
     */
    public End(String end) throws IllegalValueException {
        //assert end != null;
    	if (end == null) 
    		end = "default";
        if (!isValidEnd(end)) {
            throw new IllegalValueException(MESSAGE_END_CONSTRAINTS);
        }
        if (end.equals("default"))
        	this.value = DEFAULT_END_TIME;
        else if (end.equals("no end"))
        	this.value = "no end";
        else {
        	this.value = changeTo24HourFormat(end);
        	if (isPastEndTime(value)) {
             	pastEndTime =1;
             }
        }
    }
    
    private boolean isPastEndTime(String end) {
    	String localTime = new String("");
    	String new_min = new String(LocalTime.now().getMinute() + "");
		String new_hr = new String(LocalTime.now().getHour() + "");
		if (new_hr.length() ==1)
			new_hr = "0" + new_hr;
		if (new_min.length() ==1 )
			new_min = "0" + new_min;
		localTime = new_hr +""+ new_min;
		if (Integer.parseInt(end) - Integer.parseInt(localTime) < 0){
			System.out.println("end:" + Integer.parseInt(end) + " local:" + Integer.parseInt(localTime));
			return true;
		}
		else
			return false;
	}

	private String changeTo24HourFormat(String end) {
		if (Character.isDigit(end.charAt(end.length()-1)))
			return end;
		else if (end.length() == 3) {
			if (end.substring(1).equalsIgnoreCase("pm"))
				return (Integer.parseInt(end.substring(0,1))+12) + "00";
			else
				return "0" + end.substring(0, 1) + "00";
		}
		else if (end.length() == 4) {
			if (end.substring(2).equalsIgnoreCase("pm"))
				return (Integer.parseInt(end.substring(0,2))+12) + "00";
			else
				return end.substring(0, 2) + "00";
		}
		else {
			String[] time_cat = end.split("\\.");
			if (time_cat[0].length() ==1)
				time_cat[0] = "0" + time_cat[0];
			if (time_cat[1].substring(2).equalsIgnoreCase("pm")) 
				time_cat[0] = "" + (Integer.parseInt(time_cat[0]) + 12);
			return time_cat[0] + time_cat[1].substring(0, 2);
		}
			
	}

	public int getPastEndTime() {
		return pastEndTime;
	}

    /**
     * Returns true if a given string is a valid task end time.
     */
    public static boolean isValidEnd(String test) {
    	if (test.matches(END_VALIDATION_REGEX) || test.equals("default"))
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
                || (other instanceof End // instanceof handles nulls
                && this.value.equals(((End) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}