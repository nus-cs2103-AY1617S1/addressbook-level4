package seedu.tasklist.model.task;

import java.util.Date;
import java.util.List;

import seedu.tasklist.commons.exceptions.IllegalValueException;
import seedu.tasklist.logic.commands.TimePreparser;

import com.joestelmach.natty.*;

/**
 * Represents the start time of a task.
 * Guarantees: immutable; is valid as declared in {@link #isValidStartTime(String)}
 */
public class StartTime {

    public static final String MESSAGE_START_TIME_CONSTRAINTS = "Start time is invalid!";

    public final Date startTime;

    /**
     * Validates given start time.
     *
     * @throws IllegalValueException if given start time is invalid.
     */
    public StartTime(String input) throws IllegalValueException {
    	String startTime = TimePreparser.preparse(input);
    	if(!startTime.isEmpty() && !startTime.equals(new Date(0).toString())){
    		List<DateGroup> dates = new Parser().parse(startTime);
    		if(dates.isEmpty()){
    			throw new IllegalValueException("Start time is invalid!");
    		}
    		else if(dates.get(0).getDates().isEmpty()){
    			throw new IllegalValueException("Start time is invalid!");
    		}
    		else{
    			this.startTime = dates.get(0).getDates().get(0);
    		}
    	}
    	else{
    		this.startTime = new Date(0);
    	}
    }

    @Override
    public String toString() {
    	if(startTime.equals(new Date(0))){
    		return (new Date(0)).toString();
    	}
    	else{
    		return startTime.toString();
    	}
    }
    
    public String toCardString() {
    	if(startTime.equals(new Date(0))){
    		return "-";
    	}
    	else{
    		return startTime.toString();
    	}
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (this.startTime != null && ((StartTime) other).startTime != null ) && (other instanceof StartTime // instanceof handles nulls
                && this.startTime.equals(((StartTime) other).startTime)); // state check
    }

    @Override
    public int hashCode() {
        return startTime.hashCode();
    }

}
