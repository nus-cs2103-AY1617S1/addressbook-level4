package seedu.unburden.model.task;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.unburden.commons.exceptions.IllegalValueException;

/**
 * Represents a Task's time in the Task Manager. Guarantees: immutable; is valid
 * as declared in {@link #isValidTime(String)}
 * 
 * @@author A0143095H
 */

// @@ Gauri Joshi A0143095H
public class Time implements Comparable<Time> {

	public static final String MESSAGE_TIME_CONSTRAINTS = "Task time should be in the format XXYY where X represents the number of hours and Y represents the number of minutes";
	public static final String TIME_VALIDATION_REGEX = "([0-1][0-9][0-5][0-9])|([2][0-3][0-5][0-9])$";

	// \\[0-9]{2}[0-9]{2}

	private final String fullTime;
	private int hours;
	private int minutes;
	

	/**
	 * Validates given time.
	 *
	 * @throws IllegalValueException
	 *             if given time string is invalid.
	 */
	public Time(String time) throws IllegalValueException {
		assert time != null;
		if (!time.equals("")) {
			time = time.trim();
			if (!isValidTime(time)) {
				throw new IllegalValueException(MESSAGE_TIME_CONSTRAINTS);
			}
		}
		if (time.equals("")) {
			this.fullTime = "";
			this.hours = 23;
			this.minutes = 59;
			
		} else {
			this.fullTime = time;
			this.hours = Integer.parseInt(time.substring(0, 2));
			this.minutes = Integer.parseInt(time.substring(2));
		}
	}

	/**
	 * Returns true if a given string is a valid time.
	 */
	public static boolean isValidTime(String test) {
		final Pattern pattern = Pattern.compile(TIME_VALIDATION_REGEX);
		final Matcher matcher = pattern.matcher(test);
		return matcher.matches();
	}
	
	public String getFullTime() {
		return this.fullTime;
	}

	public int getHours() {
		return this.hours;
	}
	
	public int getMinutes() {
		return this.minutes;
	}
	


	@Override
	public String toString() {
		return fullTime;
	}

	@Override
	public boolean equals(Object other) {
		return other == this // short circuit if same object
				|| (other instanceof Time // instanceof handles nulls
						&& this.fullTime.equals(((Time) other).fullTime)); // state
																			// check
	}

	
	@Override 	
	public int compareTo(Time time) {
		
		// entry check
		if (this.equals(time)) { //check if same time or both dummy values
			return 0;
		}
		else if (this.getFullTime() == "       ") { // check if this Time object contains the dummy value
			return 1;
		}
		else if (time.getFullTime() == "       ") { // check if the Time object compared to contains the dummy value
			return -1;
		}
		
		// comparing the values, hours and minutes.
		if (this.getHours() == time.getHours() && this.getMinutes() == time.getMinutes()) {
			return 0;
		}
		else if (this.getHours() == time.getHours()) {
			return this.getMinutes() - time.getMinutes();
		}
		else {
			return this.getHours() - time.getHours();
		}
	}
	
	@Override
	public int hashCode() {
		return fullTime.hashCode();
	}

}
