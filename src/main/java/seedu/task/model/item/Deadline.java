package seedu.task.model.item;

import java.time.LocalDateTime;

import seedu.task.commons.exceptions.IllegalValueException;
import seedu.task.commons.util.StringUtil;


/**
 * Represents a Task's deadline in the task book.
 * Guarantees: immutable; is valid as declared in {@link #isValidDeadline(String)}
 * @author kian ming
 */
public class Deadline implements Comparable<Deadline> {

    public static final String MESSAGE_DEADLINE_CONSTRAINTS = 
    		"Task deadline can be in the following format: \n"
    		+ "DAY HH:MM[:SS], eg: Monday 12:00[:30]\n"
    		+ "M D Y, eg: Oct 12 2016\n"
    		+ "M/D/Y, eg: 01/30/16\n"
    		+ "RELATIVE_DAY TIME, tomorrow 4pm\n";
    

    private LocalDateTime deadLine; 

    /**
     * Validates given deadline.
     *
     * @throws IllegalValueException if given deadline string is invalid.
     */
    public Deadline(String deadlineArg) throws IllegalValueException {
        assert deadlineArg != null;
        deadlineArg = deadlineArg.trim();
   
        if (deadlineArg.isEmpty()) {
            throw new IllegalValueException(MESSAGE_DEADLINE_CONSTRAINTS);
        }   
        
        try {
        	this.deadLine = StringUtil.parseStringToTime(deadlineArg);
        } catch (IllegalValueException e) {
        	throw new IllegalValueException(MESSAGE_DEADLINE_CONSTRAINTS);
        }
    }


    @Override
    public String toString() {
        return this.deadLine.format(StringUtil.DATE_FORMATTER);
    }

    @Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((this.toString() == null) ? 0 : this.toString().hashCode());
		return result;
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		
		Deadline other = (Deadline) obj;
		if (deadLine == null) {
			if (other.deadLine != null)
				return false;
		} else if (!this.toString()
				.equals(other.toString())) /*Standardized String to compare for equality */
			return false;
		return true;
	}


	@Override
	public int compareTo(Deadline o) {
		return this.deadLine.compareTo(o.deadLine);
	}

    

}
