package seedu.task.model.task;

import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import seedu.task.commons.exceptions.IllegalValueException;
//@@author A0148083A
//Represents a Task's(event) start date in the task manager.
public class DueDate {

  
    public static final String MESSAGE_DATE_CONSTRAINTS = "Task's due date should be entered as DD-MM-YYYY hh:mm\n"
            + "EXAMPLE: add Homework d/Math homework dd/02-01-2011 23:59";
    public static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd-MM-yyyy HH:mm");
    
    public static final SimpleDateFormat DATE_FORMAT_WITHOUT_TIME = new SimpleDateFormat("dd-MM-yyyy");
  
    public final Date dueDate;

    public DueDate(String dateToValidate) throws IllegalValueException, ParseException {
        if (dateToValidate.equals("Not Set")) {
            this.dueDate = null;
        }
        else if (!isValidDateTime(dateToValidate) && !isValidDate(dateToValidate)) {
            throw new IllegalValueException(MESSAGE_DATE_CONSTRAINTS);
        }
        else {
            if (!isValidDateTime(dateToValidate)) {
                this.dueDate = DATE_FORMAT.parse(dateToValidate + " 23:59");
            }
            else {
                this.dueDate = DATE_FORMAT.parse(dateToValidate);
            }
        }
    }
  
	public DueDate(Date date) {
		dueDate = date;
	}

	public static boolean isValidDateTime(String inDate) {
		DATE_FORMAT.setLenient(false);
		try {
			DATE_FORMAT.parse(inDate.trim());
		} catch (ParseException pe) {
			return false;
		}
		return true;
	}
	
	public static boolean isValidDate(String inDate){
	    DATE_FORMAT_WITHOUT_TIME.setLenient(false);
	    try{
	        DATE_FORMAT_WITHOUT_TIME.parse(inDate.trim());
	    }
	    catch (ParseException pe){
	        return false;
	    }
	    return true;
	}

	@Override
	public String toString() {
		return dueDate == null ? "Not Set" : DATE_FORMAT.format(dueDate);
	}

	@Override
	public boolean equals(Object other) {
		if (other == null)
			return false;
		return other == this // short circuit if same object
				|| (other instanceof DueDate // instanceof handles nulls
			    && ((DueDate) other).dueDate != null
			    && this.dueDate.equals(((DueDate) other).dueDate)); // state
																			// check
	}

	@Override
	public int hashCode() {
		return dueDate.hashCode();
	}

}
//@@author A0148083A
