package seedu.task.model.task;

import java.util.Date;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import seedu.task.commons.exceptions.IllegalValueException;
//@@author A0148083A
//Represents a Task's(event) start date in the task manager.
public class StartDate {

	public static final String MESSAGE_DATE_CONSTRAINTS = "Task's start date should be entered as DD-MM-YYYY hh:mm\n"
	        +"Example: add Homework d/Math homework sd/01-01-2011 00:00 dd/03-01-2011 23:59";
	public static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd-MM-yyyy HH:mm");

	public final Date startDate;

	public StartDate(String dateToValidate) throws IllegalValueException, ParseException {
		if (dateToValidate.equals("Not Set")) {
			this.startDate = null;
		} else if (!isValidDate(dateToValidate)) {
			throw new IllegalValueException(MESSAGE_DATE_CONSTRAINTS);
		} else {
			this.startDate = DATE_FORMAT.parse(dateToValidate);
		}
	}

	public StartDate(Date date) {
		startDate = date;
	}

	public static boolean isValidDate(String inDate) {
		DATE_FORMAT.setLenient(false);
		try {
			DATE_FORMAT.parse(inDate.trim());
		} catch (ParseException pe) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return startDate == null ? "Not Set" : DATE_FORMAT.format(startDate);
	}

	@Override
	public boolean equals(Object other) {
	      return other == this // short circuit if same object
	              || (other instanceof StartDate // instanceof handles nulls
	              && ((StartDate) other).startDate != null
	              && this.startDate.equals(((StartDate) other).startDate)); // state check
	}

	@Override
	public int hashCode() {
		return startDate.hashCode();
	}

}
//@@author