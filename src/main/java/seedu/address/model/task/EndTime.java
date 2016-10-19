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
<<<<<<< HEAD
 * Represents a task's address in the task manager.
 * Guarantees: immutable; is valid as declared in {@link #isValidEndTime(String)}
=======
 * Represents a Task's end time in the task manager.
 * Guarantees: immutable; is valid as declared in {@link #isValidStart(String)}
>>>>>>> 6979c6e09aa589b563edc008506e7fc11f772466
 */
public class EndTime {
    
    public static final String MESSAGE_END_TIME_CONSTRAINTS = "Invalid End Time!";

    public Calendar endTime;

    /**
     * Validates given end.
     *
     * @throws IllegalValueException if given end timing string is invalid.
     */
    public EndTime(String input) throws IllegalValueException {
        input = input.trim();
        
        endTime = Calendar.getInstance();
    	String end = DatePreParse.preparse(input);
    	
    	if(!end.isEmpty() && !end.equals(new Date(0).toString())){
    		List<DateGroup> dates = new Parser().parse(end);
    		if(dates.isEmpty()){
    			throw new IllegalValueException(MESSAGE_END_TIME_CONSTRAINTS);
    		}
    		else if(dates.get(0).getDates().isEmpty()){
    			throw new IllegalValueException(MESSAGE_END_TIME_CONSTRAINTS);
    		}
    		else{
    			endTime.setTime(dates.get(0).getDates().get(0));
    		}
    	}
    	
    	else{
    		endTime.setTime(new Date(0));
    	}
    }

    public String appearOnUIFormat() {
    	if(endTime.getTime().equals(new Date(0))) {
    		return "-";
    	}
    	else {
    		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yy h:mm a");
    		return dateFormat.format(endTime.getTime());
    	}
    }

    @Override
    public String toString() {
        if(endTime.getTime().equals(new Date(0)))
        	return (new Date(0)).toString();
        else
        	return endTime.getTime().toString();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (this.endTime != null && ((EndTime)other).endTime != null) && (other instanceof EndTime // instanceOf handles nulls
                && this.endTime.equals(((EndTime) other).endTime)); // state check
    }

    @Override
    public int hashCode() {
        return endTime.hashCode();
    }
}
