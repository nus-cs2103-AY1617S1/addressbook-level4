package seedu.task.model.item;

import seedu.task.commons.exceptions.IllegalValueException;
import seedu.task.commons.util.StringUtil;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Represents an event's duration in the task book. Guarantees: immutable; is
 * valid as declared in {@link #isValidDuration(String)}
 */
public class EventDuration implements Comparable<EventDuration> {

	public static final String MESSAGE_DURATION_CONSTRAINTS = "event duration should be seperated by >; \n"
			+ "start time should be no later than end time. \n "
			+ "eg: today 4pm > tomorrow 4pm";
	public static final Pattern DURATION_VALIDATION_REGEX = 
			Pattern.compile("^(?<startTime>[^\\>]+)"+"(?:\\>\\s(?<endTime>[^\\>]+))?");
	private static final int DATE_INDEX = 0;
	private static final String MESSAGE_DURATION_FORMAT = "%1$s > %2$s";
	private static final long DEFAULT_DURATION = 1;
	
	
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
		if(!matcher.matches()) throw new IllegalValueException(MESSAGE_DURATION_CONSTRAINTS);
		
		//store the start date and end date 
		Optional<String> startTimeArg = Optional.ofNullable(matcher.group("startTime"));
		Optional<String> endTimeArg = Optional.ofNullable(matcher.group("endTime"));
			
		try{
			parseStartAndEndTime(startTimeArg, endTimeArg);
		} catch (IllegalValueException e) {
			throw new IllegalValueException(MESSAGE_DURATION_CONSTRAINTS);
		}

	}
	
	private void parseStartAndEndTime(Optional<String> startTimeArg, Optional<String> endTimeArg) throws IllegalValueException {
		if(!startTimeArg.isPresent()) throw new IllegalValueException(MESSAGE_DURATION_CONSTRAINTS); /* start time must not be empty */
		
		setStartTime(StringUtil.parseStringToTime(startTimeArg.get()));
		assert getStartTime() != null;
		
		if(endTimeArg.isPresent()) {
			setEndTime(StringUtil.parseStringToTime(endTimeArg.get()));
		} else {
			setEndTime(getStartTime().plusHours(DEFAULT_DURATION)); /* if no end time, set default duration of event to 1 hr */
		}
		assert getEndTime() != null;
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
		return String.format(MESSAGE_DURATION_FORMAT, 
				getStartTime().format(StringUtil.DATE_FORMATTER), 
				getEndTime().format(StringUtil.DATE_FORMATTER));
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
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
		return this.toString().equals(other.toString());
	}


	@Override
	public int compareTo(EventDuration o) {
		if (this.startTime.compareTo(o.startTime) == 0) {
			return this.endTime.compareTo(o.endTime);
		} else {
			return this.startTime.compareTo(o.startTime);
		}
	}
}
