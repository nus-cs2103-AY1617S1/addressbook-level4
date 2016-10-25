package seedu.task.model.task;

import java.util.Calendar;
import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import seedu.task.commons.exceptions.IllegalValueException;

//Represents a Task's(event) start date in the task manager.
public class StartDate {

	public static final String MESSAGE_DATE_CONSTRAINTS = "Task's start date should be entered as DD-MM-YYYY";
	public static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd-MM-yyyy");

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
	              && this.startDate.equals(((StartDate) other).startDate)); // state check
	}

	@Override
	public int hashCode() {
		return startDate.hashCode();
	}

}
