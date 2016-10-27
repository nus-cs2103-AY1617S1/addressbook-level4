package jym.manager.model.task;


import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Locale;

import com.joestelmach.natty.DateGroup;
import com.joestelmach.natty.Parser;

import jym.manager.commons.exceptions.IllegalValueException;

/**
 * Represents a Task's deadline in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidEmail(String)}
 */
public class Deadline {

    public static final String MESSAGE_DEADLINE_CONSTRAINTS =
            "Task deadlines should be 2 alphanumeric/period strings separated by '@'";
    public static final String DEADLINE_VALIDATION_REGEX = "[\\w\\.]+@[\\w\\.]+";  // need to work on the deadline format

    private final String value;
    private LocalDateTime date;

    
    public Deadline(){
    	this.date = null;
    	this.value = "no deadline";
    }
    
    public Deadline(LocalDateTime dueDate){
    	assert dueDate != null;
    	this.date = dueDate;
    	value = dueDate.toString();
    	
    }
    
    /**
     * Validates given email.
     *
     * @throws IllegalValueException if given email address string is invalid.
     */
    public Deadline(String deadline) throws IllegalValueException {
        assert deadline != null;
        deadline = deadline.trim();
        if (!isValidDeadline(deadline)) {
            throw new IllegalValueException(MESSAGE_DEADLINE_CONSTRAINTS);
        }
        this.value = deadline;
        
    }
    public Deadline(Deadline other){
    	this.value = other.toString();
    	this.date = other.getDate();
    }
    /**
     * Returns if a given string is a valid person email.
     */
    public boolean isValidDeadline(String test) {
    	if(test == null) return false;
    	if(test.equals("no deadline")) return true;
    	
    	Parser p = new Parser();
    	List<DateGroup> dg = p.parse(test);
    	if(dg.isEmpty()) return false;
    	else {
    		this.date = LocalDateTime.ofInstant(dg.get(0).getDates().get(0).toInstant(), 
					ZoneId.systemDefault());
    		return true;
    	}
    	
	
    }

    @Override
    public String toString() {
    	if(this.date == null)
    		return value;
    	else
    		return this.date.format(DateTimeFormatter.ofPattern("dd-MMM-yyyy hh:mm a"));
    }
    
    public LocalDateTime getDate(){
    	return this.date;
    }
    
    public boolean hasDeadline(){
    	return this.date != null;
    }
    
    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Deadline // instanceof handles nulls
                && this.value.equals(((Deadline) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
