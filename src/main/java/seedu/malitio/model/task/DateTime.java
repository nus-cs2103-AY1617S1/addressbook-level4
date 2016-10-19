package seedu.malitio.model.task;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import seedu.malitio.commons.exceptions.IllegalValueException;

/**
 * Represents a date and time of an event or deadline
 */
public class DateTime {
    public static final String MESSAGE_DATETIME_CONSTRAINTS = "Invalid date and time format!";
    
    private Date date;
    
    private static DateFormat df = new SimpleDateFormat("ddMMyyyy HHmm");
    
    /**
     * Converts the string that contains date information into Date
     * 
     * @throws IllegalValueException if the format of date is incorrect
     */
    public DateTime(String date) throws IllegalValueException {
      if(!isValidDateTime(date)) {
          throw new IllegalValueException(MESSAGE_DATETIME_CONSTRAINTS);
      }
        try {
            this.date = df.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }    
       
    }
    
    /**
     * Checks the validity of the input string as date and time 
     */
    private static boolean isValidDateTime(String args) {
        df.setLenient(false);
        try {
            df.parse(args);
            return true;
        } catch (ParseException e) {            
            e.printStackTrace();
            }
        return false;
        }
    
    public String toString() {
        String newDateString = df.format(date);
        return newDateString;
    }

	public int compareTo(DateTime dateTime) {
		return date.compareTo(dateTime.getDate());
	}

	public Date getDate() {
		return date;
	}
}
