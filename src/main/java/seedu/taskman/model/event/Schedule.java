package seedu.taskman.model.event;

import com.google.common.base.Objects;

import seedu.taskman.commons.exceptions.IllegalValueException;

public class Schedule {
	
	public static final String MESSAGE_SCHEDULE_CONSTRAINTS = "Task schedule should only contain dates and times in the format: [this/next] tdy/tmr/mon/tue/wed/thu/fri/sat/sun HHMM to [this/next] tdy/tmr/mon/tue/wed/thu/fri/sat/sun HHMM.";
	public static final String DATE_TIME_VALIDATION_REGEX = "((tdy|tmr)|(((this)|(next))?\\s(mon|tue|wed|thu|fri|sat|sun)))\\s(((0|1)[0-9])|(2[0-3]))([0-5][0-9])";
	public static final String SCHEDULE_VALIDATION_REGEX = DATE_TIME_VALIDATION_REGEX + "\\s(to)\\s" + DATE_TIME_VALIDATION_REGEX;

    public final String start;
    public final String end;
    public final String value;
    
    public Schedule(String schedule) throws IllegalValueException {
    	if (!isValidSchedule(schedule)) {
            throw new IllegalValueException(MESSAGE_SCHEDULE_CONSTRAINTS);
        }
    	String[] split = schedule.split(" to ");
        if (isEarlier(split[0], split[1])) {
        	start = split[0];
        	end = split[1];
        } else {
        	start = split[1];
        	end = split[0];
        }
        this.value = schedule;
    }
    
    /**
     * Returns true if a given string is a valid schedule.
     */
    public static boolean isValidSchedule(String test) {
        // TODO: improve on validation, see above
        return test.matches(SCHEDULE_VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return start + " to " + end;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Schedule schedule = (Schedule) o;
        return Objects.equal(start == schedule.start, end == schedule.end);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(this.toString());
    }
    
    /**
     * Returns true if dateTime1 is a date/time earlier than dateTime2, else returns false.
     * 
     * @param dateTime1
     * @param dateTime2
     * @return
     */
    public static boolean isEarlier(String dateTime1, String dateTime2) {
    	return true;
    }
}
