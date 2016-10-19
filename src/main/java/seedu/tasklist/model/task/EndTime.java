package seedu.tasklist.model.task;


import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.joestelmach.natty.*;

import seedu.tasklist.commons.exceptions.IllegalValueException;
import seedu.tasklist.logic.parser.TimePreparser;

/**
 * Represents a Person's phone number in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidEndTime(String)}
 */
public class EndTime {

    public static final String MESSAGE_END_TIME_CONSTRAINTS =
            "End time should only be entered in 24 hrs format or 12 hrs format.";
    public static final String END_TIME_VALIDATION_REGEX = ".*";

    public final Calendar endTime;

    /**
     * Validates given end time.
     *
     * @throws IllegalValueException if given end time is invalid.
     */
    public EndTime(String input) throws IllegalValueException {
    	endTime = Calendar.getInstance();
    	if(input == null || input == ""){
    		endTime.setTime(new Date(0));
    	}
    	else{
    		String preparsedTime = TimePreparser.preparse(input);
    		List<DateGroup> dates = new Parser().parse(preparsedTime);
    		if(dates.isEmpty()){
    			throw new IllegalValueException("End time is invalid!");
    		}
    		else if(dates.get(0).getDates().isEmpty()){
    			throw new IllegalValueException("End time is invalid!");
    		}
    		else{
    			endTime.setTime(dates.get(0).getDates().get(0));
    		}
    	}
    	endTime.clear(Calendar.SECOND);
    	endTime.clear(Calendar.MILLISECOND);
    }
    
    public EndTime(Long unixTime) {
    	endTime = Calendar.getInstance();
    	endTime.setTimeInMillis(unixTime);
    }

    @Override
    public String toString() {
    	if(endTime.getTime().equals(new Date(0))){
    		return (new Date(0)).toString();
    	}
    	else{
    		return endTime.getTime().toString();
    	}
    }
    
    public String toCardString() {
    	if(endTime.getTime().equals(new Date(0))){
    		return "-";
    	}
    	else{
    		DateFormat df = new SimpleDateFormat("dd/MM/yyyy hh:mm a");
    		Date endTimeString = endTime.getTime();
    		String finalEndString = df.format(endTimeString );
    		
    		return finalEndString;
    	}
    }
    
    public boolean isMissing(){
        if(endTime.getTime().equals(new Date(0)))
        return true;
        else return false;
        } 

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof EndTime // instanceof handles nulls
                && this.endTime.getTimeInMillis()==((EndTime) other).endTime.getTimeInMillis()); // state check
    }

    @Override
    public int hashCode() {
        return endTime.hashCode();
    }

}
