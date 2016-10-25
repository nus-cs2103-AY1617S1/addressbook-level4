package seedu.tasklist.model.task;


import java.util.Calendar;

import seedu.tasklist.commons.exceptions.IllegalValueException;

/**
 * Represents a Person's phone number in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidEndTime(String)}
 */
public class EndTime extends Time{

    public static final String MESSAGE_END_TIME_CONSTRAINTS = "End time is invalid!";
    public static final int DEFAULT_HOUR_VAL = 23;
    public static final int DEFAULT_MINUTE_VAL = 59;

    /**
     * Validates given end time.
     *
     * @throws IllegalValueException if given end time is invalid.
     */
    public EndTime(String input) throws IllegalValueException {
    	super(input);
    }
    
    public EndTime(Long unixTime) {
    	super(unixTime);
    }
    
    @Override
	protected int getDefaultHourVal(){
    	return DEFAULT_HOUR_VAL;
    }

    @Override
	protected int getDefaultMinuteVal(){
    	return DEFAULT_MINUTE_VAL;
    }
    
    @Override
    protected String getIVEMessage(){
    	return MESSAGE_TIME_CONSTRAINTS;
    }
    
    public void setCalendar(Calendar cal){
    	time = (Calendar)cal.clone();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof EndTime // instanceof handles nulls
                && this.time.getTimeInMillis()==((EndTime) other).time.getTimeInMillis()); // state check
    }

    @Override
    public int hashCode() {
        return time.hashCode();
    }

}
