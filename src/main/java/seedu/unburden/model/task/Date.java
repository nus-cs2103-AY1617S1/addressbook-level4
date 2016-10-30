package seedu.unburden.model.task;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

	public final String fullDate;

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
			if (!isValidDate(date)) {
				throw new IllegalValueException(MESSAGE_DATE_CONSTRAINTS);
			}
		}
		if (date.equals("       ")) {
			this.fullDate = "       ";
		} else {
			this.fullDate = date;
		}
	}

	/**
	 * Returns true if a given string is a valid date.
	 */
	public static boolean isValidDate(String test) {
		final Pattern pattern = Pattern.compile(DATE_VALIDATION_REGEX);
		final Matcher matcher = pattern.matcher(test.trim());
		return matcher.matches();
	}

	public java.util.Date toDate() throws ParseException {
		DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
		return df.parse(fullDate);
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
