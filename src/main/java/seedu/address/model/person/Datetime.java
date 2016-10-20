package seedu.address.model.person;

import java.util.List;
import java.util.TimeZone;

import seedu.address.commons.exceptions.IllegalValueException;

/**
 * Represents a Task's Date in the to-do-list. Guarantees: immutable; is valid
 * as declared in {@link #isValidDate(String)}
 */
public class Datetime {

	public static final String MESSAGE_DATE_CONSTRAINTS = "Date should be in MM-DD-YYYY format";
	public static final String DATE_VALIDATION_REGEX = "(0?[1-9]|[12][0-9]|3[01])" + "-" + "(0?[1-9]|1[012])" + "-"
			+ "\\d{4}";

	public static final String MESSAGE_TIME_CONSTRAINTS = "Time should be in 24hr format. Eg. 2359";
	public static final String TIME_VALIDATION_REGEX = "([01]?[0-9]|2[0-3])[0-5][0-9]";

	private String dateString;
	private String timeString;

	private java.util.Date startDate;
	private java.util.Date endDate;

	private List<java.util.Date> dateList;

	public Datetime(String inputStr) throws IllegalValueException {
		// allow dateList to be null in Date constructor when user doesn't input
		// "date/"
		assert inputStr != null;

		com.joestelmach.natty.Parser natty = setupNatty();

		// user does not input 'date/' -> floating task
		if (inputStr == null) {
			populateFloatingTask();
		}
		// user inputs "date/" preceding empty <?date> group
		// converts DatedTask to UndatedTask
		else if (inputStr.equals("")) {
			populateFloatingTask();
		}
		// natty cannot parse the input and returns empty List<DateGroup>
		else if (natty.parse(inputStr).isEmpty()) {
			throw new IllegalValueException(seedu.address.model.person.Date.MESSAGE_DATE_CONSTRAINTS);
		}
		// let natty parse the input
		else {
			dateList = natty.parse(inputStr).get(0).getDates();
		}

		String[] dateStrings = new String[2];
		String[] timeStrings = new String[2];

		for (int i = 0; i < dateList.size(); i++) {
			java.util.Date date = dateList.get(i);
			dateStrings[i] = date.getDate() + "-" + (date.getMonth() + 1) + "-" + (date.getYear() + 1900);
			timeStrings[i] = String.format("%02d%02d", date.getHours(), date.getMinutes());
		}

		switch (dateList.size()) {
		// deadline
		case 1:
			populateDeadline(dateStrings, timeStrings);
			break;
		// event
		case 2:
			populateEvent(dateStrings, timeStrings);
			break;
		// 3 or more java.util.Date Objects
		default:
			throw new IllegalValueException(MESSAGE_DATE_CONSTRAINTS);
		}

	}

	private void populateEvent(String[] dateStrings, String[] timeStrings) {
		if (dateStrings[0].equals(dateStrings[1])) {
			dateString = dateStrings[0];
		} else {
			dateString = dateStrings[0] + " to " + dateStrings[1];
		}
		if (timeStrings[0].equals(timeStrings[1])) {
			timeString = dateStrings[0];
		} else {
			timeString = dateStrings[0] + " to " + dateStrings[1];
		}
	}

	private void populateDeadline(String[] dateStrings, String[] timeStrings) {
		this.dateString = dateStrings[0];
		this.timeString = timeStrings[0];
		startDate = dateList.get(0);
		endDate = null;
	}

	private void populateFloatingTask() {
		this.dateString = "";
		this.timeString = "";
		startDate = null;
		endDate = null;
	}

	private com.joestelmach.natty.Parser setupNatty() {
		TimeZone tz = TimeZone.getDefault();
		com.joestelmach.natty.Parser natty = new com.joestelmach.natty.Parser(tz);
		return natty;
	}

	/**
	 * Returns if a given string is a valid person email.
	 */
	public static boolean isValidDate(String test) {
		return test.equals("") || test.matches(DATE_VALIDATION_REGEX);
	}

	@Override
	public String toString() {
		return dateString;
	}

	@Override
	public boolean equals(Object other) {
		return other == this // short circuit if same object
				|| (other instanceof Date // instanceof handles nulls
						&& this.dateString.equals(((Datetime) other).dateString)
						&& this.timeString.equals(((Datetime) other).timeString)
						&& this.startDate.equals(((Datetime) other).startDate)
						&& this.endDate.equals(((Datetime) other).endDate));											
	}

	@Override
	public int hashCode() {
		return dateString.hashCode();
	}

	public String getDateString() {
		return dateString;
	}

	public String getTimeString() {
		return timeString;
	}

	public java.util.Date getStartDate() {
		return startDate;
	}

	public java.util.Date getEndDate() {
		return endDate;
	}
	
	
}
