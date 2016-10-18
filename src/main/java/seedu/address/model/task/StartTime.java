package seedu.address.model.task;


import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.parser.DatePreParse;

import java.util.List;
import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import com.joestelmach.natty.*;


/**
 * Represents a Task's start time in the task manager.
 * Guarantees: immutable; is valid as declared in {@link #isValidStart(String)}
 */
public class StartTime {
    
    public static final String MESSAGE_START_TIME_CONSTRAINTS = "Invalid Start Time!";

    public Calendar startTime;

    /**
     * Validates given start.
     *
     * @throws IllegalValueException if given start timing string is invalid.
     */
    public StartTime(String input) throws IllegalValueException {
        input = input.trim();
        
        startTime = Calendar.getInstance();
    	String start = DatePreParse.preparse(input);
    	
    	if(!start.isEmpty() && !start.equals(new Date(0).toString())){
    		List<DateGroup> dates = new Parser().parse(start); // Using the Natty Parser()
    		if(dates.isEmpty()){
    			throw new IllegalValueException(MESSAGE_START_TIME_CONSTRAINTS);
    		}
    		else if(dates.get(0).getDates().isEmpty()){
    			throw new IllegalValueException(MESSAGE_START_TIME_CONSTRAINTS);
    		}
    		else{
    			startTime.setTime(dates.get(0).getDates().get(0));
    		}
    	}
    	
    	else{
    		startTime.setTime(new Date(0));
    	}
    }
    
    public String appearOnUIFormat() {
    	if(startTime.getTime().equals(new Date(0))) {
    		return "-";
    	}
    	else {
    		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yy h:mm a");
    		return dateFormat.format(startTime.getTime());
    	}
    }


    @Override
    public String toString() {
        if(startTime.getTime().equals(new Date(0)))
        	return (new Date(0)).toString();
        else
        	return startTime.getTime().toString();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (this.startTime != null && ((StartTime)other).startTime != null) && (other instanceof StartTime // instanceOf handles nulls
                && this.startTime.equals(((StartTime) other).startTime)); // state check
    }

    @Override
    public int hashCode() {
        return startTime.hashCode();
    }
}
