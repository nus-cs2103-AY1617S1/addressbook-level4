package seedu.task.model.item;

import seedu.task.commons.exceptions.IllegalValueException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.ocpsoft.prettytime.nlp.PrettyTimeParser;

/**
 * Represents an event's duration in the task book. Guarantees: immutable; is
 * valid as declared in {@link #isValidDuration(String)}
 */
public class EventDuration {

	public static final String MESSAGE_DURATION_CONSTRAINTS = "event duration should be in a date format of DD-MM-YY DD-MM-YY";
	public static final Pattern DURATION_VALIDATION_REGEX = 
			Pattern.compile("^(?<startDate>[^\\>]+)"+"(?:\\>\\s(?<endDate>[^\\>]+))?");
	private static final String MESSAGE_INVALID_DATE = "wrong date";
	private static final int DATE_INDEX = 0;
	private static final String MESSAGE_DURATION_FORMAT = "%1$s > %2$s";
	
	private Duration duration;
	private LocalDateTime startDate;
	private LocalDateTime endDate;

	/**
	 * Validates given duration.
	 *
	 * @throws IllegalValueException
	 *             if given duration string is invalid.
	 */
	public EventDuration(String durationArg) throws IllegalValueException {
		assert durationArg != null;
		parseDuration(durationArg);
	}

	private void parseDuration(String durationArg) throws IllegalValueException {
		final Matcher matcher = DURATION_VALIDATION_REGEX.matcher(durationArg);
		final PrettyTimeParser parser = new PrettyTimeParser();
		
		if(matcher.matches()) {
			//store the start date and end date 
			assert(matcher.group("startDate") != null);
			setStartDate(parseDate(parser, matcher.group("startDate")));
			
			if(matcher.group("endDate") != null) {
				setEndDate(parseDate(parser, matcher.group("endDate")));	
			} else {
				setEndDate(getStartDate()); /* if no endDate, set endDate equal to startDate */
			}
		} else {
			throw new IllegalValueException(MESSAGE_INVALID_DATE + durationArg);
		}
		assert(getStartDate() != null);
		assert(getEndDate() != null);
		
		setDuration(Duration.between(getStartDate(), getEndDate()));
	}
	
	/**
	 * Parse a String argument into date format. 
	 * @param parser
	 * @param dateArg
	 * @return date in LocalDateTime format
	 * @throws IllegalValueException
	 */
	private LocalDateTime parseDate(PrettyTimeParser parser, String dateArg) throws IllegalValueException {
		//invalid start date
		
		System.out.println("Date is\n"+ dateArg);
		
		if(dateArg == null) throw new IllegalValueException(MESSAGE_INVALID_DATE);
		
		Date parsedResult = parser.parse(dateArg).get(DATE_INDEX);
		
		//cannot parse
		if(parsedResult == null) throw new IllegalValueException(MESSAGE_INVALID_DATE);
		
		return LocalDateTime.ofInstant(parsedResult.toInstant(), ZoneId.systemDefault()); 
	}
	
	private Duration getDuration() {
		return duration;
	}

	private void setDuration(Duration duration) {
		this.duration = duration;
	}

	private LocalDateTime getStartDate() {
		return startDate;
	}

	private void setStartDate(LocalDateTime startDate) {
		this.startDate = startDate;
	}

	private LocalDateTime getEndDate() {
		return endDate;
	}

	private void setEndDate(LocalDateTime endDate) {
		this.endDate = endDate;
	}

	@Override
	public String toString() {
		return String.format(MESSAGE_DURATION_FORMAT, getStartDate().toString(), getEndDate().toString());
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((duration == null) ? 0 : duration.hashCode());
		result = prime * result + ((endDate == null) ? 0 : endDate.hashCode());
		result = prime * result + ((startDate == null) ? 0 : startDate.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		EventDuration other = (EventDuration) obj;
		if (duration == null) {
			if (other.duration != null)
				return false;
		} else if (!duration.equals(other.duration))
			return false;
		if (endDate == null) {
			if (other.endDate != null)
				return false;
		} else if (!endDate.equals(other.endDate))
			return false;
		if (startDate == null) {
			if (other.startDate != null)
				return false;
		} else if (!startDate.equals(other.startDate))
			return false;
		return true;
	}
}
