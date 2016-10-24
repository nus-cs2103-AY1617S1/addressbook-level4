package seedu.malitio.model.task;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import seedu.malitio.commons.exceptions.IllegalValueException;
import seedu.malitio.logic.parser.DateParser;

/**
 * Represents a date and time of an event or deadline
 */
public class DateTime {
    public static final String MESSAGE_DATETIME_CONSTRAINTS = "Unrecognised date and time!";
    
    private Date date;
    
    private static DateFormat outputFormatter = new SimpleDateFormat("MM-dd-yyyy HH:mm");
  
    /**
     * Converts the string that contains date information into Date
     * 
     * @throws IllegalValueException if the format of date is unrecognised
     */
    public DateTime(String date) throws IllegalValueException {

       this.date = DateParser.parse(date);
       if (this.date == null) {
           throw new IllegalValueException(MESSAGE_DATETIME_CONSTRAINTS);
       }
    }
    
    public String toString() {
        String newDateString = outputFormatter.format(date);
        return newDateString;
    }

	public int compareTo(DateTime dateTime) {
		return date.compareTo(dateTime.getDate());
	}

	public Date getDate() {
		return date;
	}
}
