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

    public final Calendar endtime;

    /**
     * Validates given end time.
     *
     * @throws IllegalValueException if given end time is invalid.
     */
    public EndTime(String input) throws IllegalValueException {
    	endtime = Calendar.getInstance();
    //	String endTime = input.trim();
    	String endTime = TimePreparser.preparse(input);
    	if(!endTime.isEmpty() && !endTime.equals(new Date(0).toString())){
    		List<DateGroup> dates = new Parser().parse(endTime);
    		if(dates.isEmpty()){
    			throw new IllegalValueException("End time is invalid!");
    		}
    		else if(dates.get(0).getDates().isEmpty()){
    			throw new IllegalValueException("End time is invalid!");
    		}
    		else{
    			endtime.setTime(dates.get(0).getDates().get(0));
    		}
    	}
    	else{
    	      endtime.setTime(new Date(0));
    	}
    }

    @Override
    public String toString() {
    	if(endtime.getTime().equals(new Date(0))){
    		return (new Date(0)).toString();
    	}
    	else{
    		return endtime.getTime().toString();
    	}
    }
    
    public String toCardString() {
    	if(endtime.getTime().equals(new Date(0))){
    		return "-";
    	}
    	else{
    		DateFormat df = new SimpleDateFormat("dd/MM/yyyy hh:mm a");
    		Date endTimeString = endtime.getTime();
    		String finalEndString = df.format(endTimeString );
    		
    		return finalEndString;
    	}
    }
    
    public boolean isMissing(){
        if(endtime.getTime().equals(new Date(0)))
        return true;
        else return false;
        } 

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof EndTime // instanceof handles nulls
                && this.endtime.equals(((EndTime) other).endtime)); // state check
    }

    @Override
    public int hashCode() {
        return endtime.hashCode();
    }

}
