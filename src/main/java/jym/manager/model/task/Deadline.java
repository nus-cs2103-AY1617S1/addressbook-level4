package jym.manager.model.task;


import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Locale;

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
		 LocalDateTime ldt = null;
	     test.replaceAll("\\n", "");
	     if(test.contains("T")){
	    	 try{
	 			ldt = LocalDateTime.parse(test);
	 		} catch(DateTimeParseException dtpe){
	 			dtpe.printStackTrace();
	 		}
	     } else {
	     	try{
		     	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm").withLocale(Locale.ENGLISH);
		     	ldt = LocalDateTime.parse(test, formatter);
	    	}catch(DateTimeParseException dtpe){
	    		dtpe.printStackTrace();
	    	}
	     }
    	
		if(ldt != null){
			this.date = ldt;
		}
	     return (ldt != null);
    }

    @Override
    public String toString() {
    	if(this.date == null)
    		return value;
    	else
    		return this.date.toString();
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
