package seedu.tasklist.model.task;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import seedu.tasklist.commons.exceptions.IllegalValueException;
import seedu.tasklist.logic.parser.TimePreparser;

import com.joestelmach.natty.*;

/**
 * Represents the start time of a task.
 * Guarantees: immutable; is valid as declared in {@link #isValidStartTime(String)}
 */
public class Time {

    public static final String MESSAGE_TIME_CONSTRAINTS = "Time is invalid!";

    public final Calendar time;

    /**
     * Validates given start time.
     *
     * @throws IllegalValueException if given start time is invalid.
     */
    public Time(String input) throws IllegalValueException {
    	time = Calendar.getInstance();
    	if(input == null || input==""){
    		time.setTime(new Date(0));
    	}
    	else{
    		String preparsedTime = TimePreparser.preparse(input);
    		List<DateGroup> dates = new Parser().parse(preparsedTime);
    		if(dates.isEmpty()){
    			throw new IllegalValueException(getIVEMessage());
    		}
    		else if(dates.get(0).getDates().isEmpty()){
    			throw new IllegalValueException(getIVEMessage());
    		}
    		else{
    			time.setTime(dates.get(0).getDates().get(0));
    			setDefaultTime(dates.get(0));
    		}
    	}
    	time.clear(Calendar.SECOND);
    	time.clear(Calendar.MILLISECOND);
    }
    
    public Time(Long unixTime) {
    	time = Calendar.getInstance();
    	time.setTimeInMillis(unixTime);
    }
    
    private void setDefaultTime(DateGroup dategroup){
    	if (dategroup.isTimeInferred()) {
			time.set(Calendar.HOUR_OF_DAY, getDefaultHourVal());
			time.set(Calendar.MINUTE, getDefaultMinuteVal());
		}
    }
    
    protected int getDefaultHourVal(){
    	return 0;
    }

    protected int getDefaultMinuteVal(){
    	return 0;
    }
    
    protected String getIVEMessage(){
    	return MESSAGE_TIME_CONSTRAINTS;
    }
    
    @Override
    public String toString() {
    	if(time.getTime().equals(new Date(0))){
    		return (new Date(0)).toString();
    	}
    	else{
    		return time.getTime().toString();
    	}
    }
    
    public String toCardString() {
    	if(time.getTime().equals(new Date(0))){
    		return "-";
    	}
    	else{
    		DateFormat df = new SimpleDateFormat("dd/MM/yyyy hh:mm a");
    		Date startTimeString = time.getTime();
    		String finalStartString = df.format(startTimeString );
    		
    		return finalStartString;
    	}
    }

    public boolean isMissing(){
    	if(time.getTime().equals(new Date(0)))
        return true;
        else return false;
        } 
    
    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Time
                && this.time.getTimeInMillis()==((Time) other).time.getTimeInMillis()); // state check
    }

    @Override
    public int hashCode() {
        return time.hashCode();
    }
    
    public Calendar getAsCalendar() {
        return time;
    }

    public int compareTo(Time startTime) {
        if ((this.getAsCalendar().getTimeInMillis()) > (startTime.getAsCalendar().getTimeInMillis()))
            return 1;
        else if ((this.getAsCalendar().getTimeInMillis()) < (startTime.getAsCalendar().getTimeInMillis()))
            return -1;
        else return 0;
    }

}