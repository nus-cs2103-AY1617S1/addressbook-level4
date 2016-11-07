package seedu.tasklist.model.task;


import java.util.Calendar;

import seedu.tasklist.commons.exceptions.IllegalValueException;

//@@author A0146107M
/**
* Represents the start time of a task.
* 
*/
public class EndTime extends Time{

    public static final String MESSAGE_END_TIME_CONSTRAINTS = "End time is invalid!";
    public static final int DEFAULT_HOUR_VAL = 23;
    public static final int DEFAULT_MINUTE_VAL = 59;

    /**
     * Constructor using natural language input
     *
     * @param input Input string to parse
     * @throws IllegalValueException if given start time is invalid.
     */
    public EndTime(String input) throws IllegalValueException {
    	super(input);
    }
    
    /**
     * Constructor using unix time millis
     *
     * @param unixTimeMillis Input long representing time in millis since epoch time
     */
    public EndTime(Long unixTimeMillis) {
    	super(unixTimeMillis);
    }
    
    /**
     * Constructor using a Calendar instance
     *
     * @param cal Calendar to get time info from
     */ 
    public EndTime(Calendar cal) {
    	super(cal);
    }
    
    /**
     * Get hour default
     *
     * @return the default hour value
     */
    @Override
	protected int getDefaultHourVal(){
    	return DEFAULT_HOUR_VAL;
    }

    /**
     * Get minute default
     *
     * @return the default minute value
     */
    @Override
	protected int getDefaultMinuteVal(){
    	return DEFAULT_MINUTE_VAL;
    }
    
    /**
     * Get Illegal Value Exception message
     *
     * @return The message associated with an Illegal Value Exception
     */
    @Override
    protected String getIVEMessage(){
    	return MESSAGE_END_TIME_CONSTRAINTS;
    }

    /**
     * Checks if another object is equal to this EndTime instance
     *
     * @param other Another object to compare to
     * @return true if both are EndTime objects and the time represented by both objects are equal
     */
    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof EndTime // instanceof handles nulls
                && this.time.getTimeInMillis()==((EndTime) other).time.getTimeInMillis()); // state check
    }

    /**
     * Get a hash representation of this object
     *
     * @return The hash representation of this object
     */
    @Override
    public int hashCode() {
        return super.hashCode();
    }

}
