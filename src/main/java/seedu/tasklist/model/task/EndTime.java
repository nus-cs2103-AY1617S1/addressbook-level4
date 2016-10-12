package seedu.tasklist.model.task;


import java.util.Date;
import java.util.List;

import com.joestelmach.natty.*;

import seedu.tasklist.commons.exceptions.IllegalValueException;

/**
 * Represents a Person's phone number in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidEndTime(String)}
 */
public class EndTime {

    public static final String MESSAGE_END_TIME_CONSTRAINTS =
            "End time should only be entered in 24 hrs format or 12 hrs format.";
    public static final String END_TIME_VALIDATION_REGEX = ".*";

    public final Date endTime;

    /**
     * Validates given end time.
     *
     * @throws IllegalValueException if given end time is invalid.
     */
    public EndTime(String endTime) throws IllegalValueException {
    	if(endTime != "" && !endTime.equals(new Date(0).toString())){
    		List<DateGroup> dates = new Parser().parse(endTime);
    		if(dates.isEmpty()){
    			throw new IllegalValueException("Start time is invalid!");
    		}
    		else if(dates.get(0).getDates().isEmpty()){
    			throw new IllegalValueException("Start time is invalid!");
    		}
    		else{
    			this.endTime = dates.get(0).getDates().get(0);
    		}
    	}
    	else{
    		this.endTime = new Date(0);
    	}
    }

    @Override
    public String toString() {
    	if(endTime.equals(new Date(0))){
    		return (new Date(0)).toString();
    	}
    	else{
    		return endTime.toString();
    	}
    }
    
    public String toCardString() {
    	if(endTime.equals(new Date(0))){
    		return "-";
    	}
    	else{
    		return endTime.toString();
    	}
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof EndTime // instanceof handles nulls
                && this.endTime.equals(((EndTime) other).endTime)); // state check
    }

    @Override
    public int hashCode() {
        return endTime.hashCode();
    }

}
