package seedu.taskman.model.event;

import com.google.common.base.Objects;

import seedu.taskman.commons.exceptions.IllegalValueException;
import seedu.taskman.model.Regex;

public class Schedule {
	
	public static final String MESSAGE_SCHEDULE_CONSTRAINTS =
            "Task schedule should only contain dates and times in the format: " +
                    Regex.DESCRIPTION_DATE_TIME_TYPIST_FRIENDLY + " (a \",\" or \"to\") "
                    + Regex.DESCRIPTION_DATE_TIME_TYPIST_FRIENDLY;
	public static final String SCHEDULE_VALIDATION_REGEX =
            "^" + Regex.DATE_TIME_TYPIST_FRIENDLY + "((,\\s?)|(\\s(to)\\s))" + Regex.DATE_TIME_TYPIST_FRIENDLY + "$";
    // todo: add regex for other time formats, eg: DDMMYYYY:TTTT
    // todo: add regex for START_DATETIME with DURATION

    // todo: save as unix time instead
    public final String start;
    public final String end;

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

    // todo: implement datetime comparison
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
