package seedu.task.model.item;

import seedu.task.commons.exceptions.IllegalValueException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.ocpsoft.prettytime.nlp.PrettyTimeParser;

/**
 * Represents an event's duration in the task book. Guarantees: immutable; is
 * valid as declared in {@link #isValidDuration(String)}
 */
public class EventDuration {

	public static final String MESSAGE_DURATION_CONSTRAINTS = "event duration should be seperated by >; \n"
			+ "start time should be no later than end time. \n "
			+ "eg: today 4pm > tomorrow 4pm";
	public static final Pattern DURATION_VALIDATION_REGEX = 
			Pattern.compile("^(?<startTime>[^\\>]+)"+"(?:\\>\\s(?<endTime>[^\\>]+))?");
	private static final int DATE_INDEX = 0;
	private static final String MESSAGE_DURATION_FORMAT = "%1$s > %2$s";
	
	private Duration duration;
	private LocalDateTime startTime;
	private LocalDateTime endTime;
	

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
			assert(matcher.group("startTime") != null);
			setStartTime(parseDate(parser, matcher.group("startTime")));
			
			if(matcher.group("endTime") != null) {
				setEndTime(parseDate(parser, matcher.group("endTime")));	
			} else {
				setEndTime(getStartTime()); /* if no endTime, set endTime equal to startTime */
			}
		} else {
			throw new IllegalValueException(MESSAGE_DURATION_CONSTRAINTS);
		}
		assert(getStartTime() != null);
		assert(getEndTime() != null);
		
		setDuration(Duration.between(getStartTime(), getEndTime()));
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
		
		if(dateArg == null) throw new IllegalValueException(MESSAGE_DURATION_CONSTRAINTS);
		
		List<Date> parsedResult = parser.parse(dateArg);
		
		//cannot parse
		if(parsedResult.isEmpty()) throw new IllegalValueException(MESSAGE_DURATION_CONSTRAINTS);
		
		return LocalDateTime.ofInstant(parsedResult.get(DATE_INDEX).toInstant(), ZoneId.systemDefault()); 
	}
	
	private Duration getDuration() {
		return duration;
	}

	private void setDuration(Duration duration) {
		this.duration = duration;
	}

	public LocalDateTime getStartTime() {
		return startTime;
	}

	private void setStartTime(LocalDateTime startTime) {
		this.startTime = startTime;
	}

	public LocalDateTime getEndTime() {
		return endTime;
	}

	private void setEndTime(LocalDateTime endTime) {
		this.endTime = endTime;
	}

	@Override
	public String toString() {
		return String.format(MESSAGE_DURATION_FORMAT, getStartTime().toString(), getEndTime().toString());
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((duration == null) ? 0 : duration.hashCode());
		result = prime * result + ((endTime == null) ? 0 : endTime.hashCode());
		result = prime * result + ((startTime == null) ? 0 : startTime.hashCode());
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
		if (endTime == null) {
			if (other.endTime != null)
				return false;
		} else if (!endTime.equals(other.endTime))
			return false;
		if (startTime == null) {
			if (other.startTime != null)
				return false;
		} else if (!startTime.equals(other.startTime))
			return false;
		return true;
	}
}
