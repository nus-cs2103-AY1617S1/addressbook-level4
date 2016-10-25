package seedu.tasklist.model.task;
import seedu.tasklist.commons.exceptions.IllegalValueException;

/**
 * Represents the start time of a task.
 * Guarantees: immutable; is valid as declared in {@link #isValidStartTime(String)}
 */
public class StartTime extends Time{

    public static final String MESSAGE_START_TIME_CONSTRAINTS = "Start time is invalid!";
    public static int DEFAULT_HOUR_VAL = 0;
    public static int DEFAULT_MINUTE_VAL = 0;

    /**
     * Validates given start time.
     *
     * @throws IllegalValueException if given start time is invalid.
     */
    public StartTime(String input) throws IllegalValueException {
    	super(input);
    }
    
    public StartTime(Long unixTime) {
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
    
    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof StartTime // instanceof handles nulls
                && this.time.getTimeInMillis()==((StartTime) other).time.getTimeInMillis()); // state check
    }

    @Override
    public int hashCode() {
        return time.hashCode();
    }

}