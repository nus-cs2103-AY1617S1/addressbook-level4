package seedu.unburden.model.task;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.Calendar;

import seedu.unburden.commons.exceptions.IllegalValueException;

/**
 * Represents a Task's date in the task manager Guarantees: immutable; is valid
 * as declared in {@link #isValidDate(String)}
 * 
 * @@author A0143095H
 */

// @@author A0143095H
public class Date {

	public static final String MESSAGE_DATE_CONSTRAINTS = "Task date is incorrect. Ensure that it is a valid date in the format DD-MM-YYYY";
	public static final String DATE_VALIDATION_REGEX = "([0][1-9]|[1-2][0-9]|[3][0-1])[-]([0][1-9]|[1][0-2])[-]([2][0][1][6-9]|[2][1-9][0-9][0-9])$";

	
	private static final int[] numDays = {0, 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
	private final String fullDate;
	private int day;
	private int month;
	private int year;

	/**
	 * Validates given date.
	 *
	 * @throws IllegalValueException
	 *             if given date string is invalid.
	 */
	public Date(String date) throws IllegalValueException {
		assert date != null;
		if (!date.equals("       ")) {
			date = date.trim();
			int[] values = splitDate(date);
			this.day = values[0];
			this.month = values[1];
			this.year = values[2];
			if (!isValidDate(date, day, month, year)) {
				throw new IllegalValueException(MESSAGE_DATE_CONSTRAINTS);
			}
		}
		if (date.equals("       ")) {
			this.fullDate = "       ";
			this.day = 32;
			this.month = 13;
			this.year = 2015;
		} else {
			this.fullDate = date;
			int[] values = splitDate(date);
			this.day = values[0];
			this.month = values[1];
			this.year = values[2];
		}
	}
	
	private int[] splitDate(String date) {
		int[] newArgs = {0, 0, 0};
	
		String[] args = date.split("-");	
		newArgs[0] = Integer.parseInt(args[0]);
		newArgs[1] = Integer.parseInt(args[1]);
		newArgs[2] = Integer.parseInt(args[2]);
		
		return newArgs;
		
	}

	/**
	 * Returns true if a given string is a valid date.
	 */
	public static boolean isValidDate(String test, int day, int month, int year) {
		final Pattern pattern = Pattern.compile(DATE_VALIDATION_REGEX);
		final Matcher matcher = pattern.matcher(test.trim());
		return matcher.matches() && isValidYear(year) && isValidMonth(month) && isValidDay(day, month, year);
	}

	private static boolean isLeapYear(int year) {
		if ((year % 4 == 0) && (year % 100 == 0) & (year % 400 == 0)) {
			return true;
			
		}
		return false;
	}
	
	private static boolean isValidDay(int day, int month, int year) {
		if (isLeapYear(year) && month == 2) {
			if (day > numDays[month] + 1) {
				return false;
			}
		}
		
		else {
			if (day > numDays[month]) {
				return false;
			}
		}
		
		return true;
	}
	
	private static boolean isValidMonth(int month) {
		if (month > 12 || month == 0) {
			return false;
		}
		return true;
	}
	
	private static boolean isValidYear(int year) {
		Calendar calendar = Calendar.getInstance();
		int currentYear;
		calendar.setTime(calendar.getTime());
		currentYear = calendar.get(Calendar.YEAR);
		System.out.println(currentYear);
		
		if (year >= currentYear) {
			return true;
		}
		return false;
	}
	
	public java.util.Date toDate() throws ParseException {
		DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
		return df.parse(fullDate);
	}
	
	public String getFullDate() {
		return this.fullDate;
	}

	@Override
	public String toString() {
		return fullDate;
	}

	@Override
	public boolean equals(Object other) {
		return other == this // short circuit if same object
				|| (other instanceof Date // instanceof handles nulls
						&& this.fullDate.equals(((Date) other).fullDate)); // state
																			// check
	}

	@Override
	public int hashCode() {
		return fullDate.hashCode();
	}

}
