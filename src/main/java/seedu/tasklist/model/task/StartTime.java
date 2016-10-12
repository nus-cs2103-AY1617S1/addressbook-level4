package seedu.tasklist.model.task;

import java.util.Date;
import java.util.List;

import seedu.tasklist.commons.exceptions.IllegalValueException;
import org.ocpsoft.prettytime.nlp.PrettyTimeParser;

/**
 * Represents the start time of a task.
 * Guarantees: immutable; is valid as declared in {@link #isValidStartTime(String)}
 */
public class StartTime {

    public static final String MESSAGE_START_TIME_CONSTRAINTS = "Start time is invalid!";

    public final Date startDate;

    /**
     * Validates given start time.
     *
     * @throws IllegalValueException if given start time is invalid.
     */
    public StartTime(String startTime) throws IllegalValueException {
    	if(startTime != null){
    		List<Date> dates = new PrettyTimeParser().parse(startTime);
    		if(dates.isEmpty()){
    			throw new IllegalValueException("Start time is invalid!");
    		}
    		else{
    			this.startDate = dates.get(0);
    		}
    	}
    	else{
    		this.startDate = null;
    	}
    }

    @Override
    public String toString() {
        return startDate.toString();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (this.startDate != null && ((StartTime) other).startDate != null ) && (other instanceof StartTime // instanceof handles nulls
                && this.startDate.equals(((StartTime) other).startDate)); // state check
    }

    @Override
    public int hashCode() {
        return startDate.hashCode();
    }

}
