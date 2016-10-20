package seedu.address.model.task;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.deadline.Deadline;

/**
 * Represents a task's starting time in the task manager.
 * Guarantees: immutable; is valid as declared in 
 */
public class Startline  {
	
	public static final String MESSAGE_ADDRESS_CONSTRAINTS = "Task start must be in ddmmyy or dd-MM-yy format.";
	public static final String STARTLINE_VALIDATION_REGEX = "\\d+";
    public static final String STARTLINE_DASH_VALIDATION_REGEX = "[\\d]+-[\\d]+-[\\d]+";
	
	public final String value;
	public final Date date;
	public final Calendar calendar;
	
	/**
     * Validates given start time.
     *
     * @throws IllegalValueException if given address string is invalid.
     */
	public Startline(String startline) throws IllegalValueException {
		assert startline != null;
//		if(!isValidStartline(startline)){
//			throw new IllegalValueException(MESSAGE_ADDRESS_CONSTRAINTS);
//		}
		String [] date_time = startline.trim().split("\\s+");
		this.value = mutateToDash(date_time[0])  + " " + date_time[1];
		this.date = mutateToDate(this.value);
		Calendar cal = Calendar.getInstance();
		cal.setTime(this.date);
		this.calendar = cal;
	}
	
//	private boolean isValidStartline(String test){
//		return (test.matches(STARTLINE_VALIDATION_REGEX) || test.matches(STARTLINE_DASH_VALIDATION_REGEX));
//	}
	
	@Override
    public String toString() {
        return value;
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
	
	private Date mutateToDate(String startline) throws IllegalValueException{
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yy");
		try{
			return sdf.parse(startline);
		}
		catch (ParseException pe){
			throw new IllegalValueException(pe.getMessage());
		}
	}
	
	private String mutateToDash(String startline) throws IllegalValueException {
    	
    	Date date = null;
    	DateFormat input = new SimpleDateFormat("ddMMyy");
    	DateFormat output = new SimpleDateFormat("dd-MM-yy");
    	SimpleDateFormat saved = new SimpleDateFormat("dd-MM-yy");
    	
    	if(startline.length() == 8){
	    	try{
		    	date = saved.parse(startline);
		    	if(startline.equals(saved.format(date))){
		    		return startline;
		    	}
	    	}
	    	catch (ParseException e1){
	    		throw new IllegalValueException(MESSAGE_ADDRESS_CONSTRAINTS + " " +  startline + "lol");
	    	}
    	}
    	else if(startline.length() == 6){
	    	try{
		    	String result = output.format(input.parse(startline));
		    	return result;
	    	}
	    	catch (ParseException e){
	    		throw new IllegalValueException(MESSAGE_ADDRESS_CONSTRAINTS + " " +  startline + "lol2");
	    	}
    	}
    	else{
    		throw new IllegalValueException(MESSAGE_ADDRESS_CONSTRAINTS + " " +  startline + "lol3");
    	}
    	return startline;
    }
}
