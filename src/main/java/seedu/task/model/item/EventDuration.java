package seedu.task.model.item;

import seedu.task.commons.exceptions.IllegalValueException;
import seedu.task.commons.util.StringUtil;
import java.time.LocalDateTime;

/**
 * Represents an event's duration in the task book. 
 * Guarantees: immutable; 
 * is valid as declared in {@link #isValidDuration(String)}
 */
public class EventDuration implements Comparable<EventDuration> {

	public static final String MESSAGE_DURATION_CONSTRAINTS = "Start time should be no later than end time. \n "
			+ "No abbreviation is allowed for relative, ie: tmrw. \n"
			+ "But Fri, Mon, etc is okay.\n"
			+ "Possible event duration could be:"
			+ "today 4pm /to tomorrow 4pm";
	
	private static final String MESSAGE_DURATION_FORMAT = "From: %1$s to %2$s";
	private static final long DEFAULT_DURATION = 1;
	
	
	private LocalDateTime startTime;
	private LocalDateTime endTime;
	

	/**
	 * Creates a given duration.
	 *
	 * @throws IllegalValueException
	 *   		if given duration string is invalid.
	 */
	public EventDuration(String startTimeArg, String endTimeArg) throws IllegalValueException {
		assert startTimeArg != null;
		assert endTimeArg != null;
		parseDuration(startTimeArg, endTimeArg);
	}

	
	private void parseDuration(String startTimeArg, String endTimeArg) throws IllegalValueException {
		//if start time empty, set end time first, and start time will be {@code DEFAULT_DURATION} before.  
		if(startTimeArg.isEmpty()) {
			setEndTime(StringUtil.parseStringToTime(endTimeArg));
			setStartTime(getEndTime().minusHours(DEFAULT_DURATION));
			return;
		} 
		
		// if end time empty, set start time first, and end time will be {@code DEFAULT_DURATION} later. 
		if(endTimeArg.isEmpty()) {
			setStartTime(StringUtil.parseStringToTime(startTimeArg));
			setEndTime(getStartTime().plusHours(DEFAULT_DURATION));
			return;
		}
		
		setStartTime(StringUtil.parseStringToTime(startTimeArg));
		setEndTime(StringUtil.parseStringToTime(endTimeArg));
		
		if(!isValidDuration()) {
			throw new IllegalValueException(MESSAGE_DURATION_CONSTRAINTS);
		}
	}

	/**
	 * start time must be before end time.
	 * @return if duration valid
	 */
	private boolean isValidDuration() {
		return getStartTime().isBefore(getEndTime());
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
	
	/**
	 * format start time for UI 
	 * @return
	 */
	public String getStartTimeAsText() {
		return getStartTime().format(StringUtil.DATE_FORMATTER);
	}
	
	/**
	 * format end time for UI.
	 * @return
	 */
	public String getEndTimeAsText() {
		return getEndTime().format(StringUtil.DATE_FORMATTER);
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
