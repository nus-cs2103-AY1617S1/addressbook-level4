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
public class StartTime {

    public static final String MESSAGE_START_TIME_CONSTRAINTS = "Start time is invalid!";
    public static final int DEFAULT_HOUR_VAL = 0;
    public static final int DEFAULT_MINUTE_VAL = 0;

    public final Calendar startTime; 

    /**
     * Validates given start time.
     *
     * @throws IllegalValueException if given start time is invalid.
     */
    public StartTime(String input) throws IllegalValueException {
    	startTime = Calendar.getInstance();
    	if(input == null || input==""){
    		startTime.setTime(new Date(0));
    	}
    	else{
    		String preparsedTime = TimePreparser.preparse(input);
    		List<DateGroup> dates = new Parser().parse(preparsedTime);
    		if(dates.isEmpty()){
    			throw new IllegalValueException("Start time is invalid!");
    		}
    		else if(dates.get(0).getDates().isEmpty()){
    			throw new IllegalValueException("Start time is invalid!");
    		}
    		else{
    			startTime.setTime(dates.get(0).getDates().get(0));
    			setDefaultTime(dates.get(0));
    		}
    	}

    	startTime.clear(Calendar.SECOND);
    	startTime.clear(Calendar.MILLISECOND);
    }
    
    public StartTime(Long unixTime) {
    	startTime = Calendar.getInstance();
    	startTime.setTimeInMillis(unixTime);
    }
    
    private void setDefaultTime(DateGroup dategroup){
    	if (dategroup.isTimeInferred()) {
			startTime.set(Calendar.HOUR_OF_DAY, DEFAULT_HOUR_VAL);
			startTime.set(Calendar.MINUTE, DEFAULT_MINUTE_VAL);
		}
    }

    @Override
    public String toString() {
    	if(startTime.getTime().equals(new Date(0))){
    		return (new Date(0)).toString();
    	}
    	else{
    		return startTime.getTime().toString();
    	}
    }
    
    public String toCardString() {
    	if(startTime.getTime().equals(new Date(0))){
    		return "-";
    	}
    	else{
    		DateFormat df = new SimpleDateFormat("dd/MM/yyyy hh:mm a");
    		Date startTimeString = startTime.getTime();
    		String finalStartString = df.format(startTimeString );
    		
    		return finalStartString;
    	}
    }

    public boolean isMissing(){
    	if(startTime.getTime().equals(new Date(0)))
        return true;
        else return false;
        } 
    
    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof StartTime // instanceof handles nulls
                && this.startTime.getTimeInMillis()==((StartTime) other).startTime.getTimeInMillis()); // state check
    }

    @Override
    public int hashCode() {
        return startTime.hashCode();
    }
    
    public Calendar getAsCalendar() {
        return startTime;
    }

    public int compareTo(StartTime startTime) {
        if ((this.getAsCalendar().getTimeInMillis()) > (startTime.getAsCalendar().getTimeInMillis()))
            return 1;
        else if ((this.getAsCalendar().getTimeInMillis()) < (startTime.getAsCalendar().getTimeInMillis()))
            return -1;
        else return 0;
    }

}