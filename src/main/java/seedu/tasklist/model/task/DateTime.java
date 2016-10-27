package seedu.tasklist.model.task;

import java.time.LocalDate;
import java.time.LocalTime;
import seedu.tasklist.commons.exceptions.IllegalValueException;

/**
 * Represents a Task's date and time in the task list.
 * Guarantees: details are present and not null, field values are validated.
 */
public class DateTime implements DateTimeComparator {
    public static final String MESSAGE_DATETIME_CONSTRAINTS = "Invalid Date Time format\nDate format: DDMMYYYY\n24-hour Time format: HHMM";
    
    private static final int DATE_LENGTH = 8;
    private static final int TIME_LENGTH = 4;
    private static final int VALID_DATE_TIME_LENGTH = 0;    // Valid Date Time length is either 0, 4 or 8
    
    private Date date;
    private Time time;

    public DateTime() throws IllegalValueException {
        this.date = new Date("");
        this.time = new Time("");
    };
    
    public DateTime(String arguments) throws IllegalValueException {
        this();
        String[] args = arguments.trim().split(" ");
        for (String datetime : args) {
            if (datetime.length() == TIME_LENGTH) {
                this.time = new Time(datetime);
            } else if (datetime.length() == DATE_LENGTH) {
                this.date = new Date(datetime);
            } else if (datetime.length() != VALID_DATE_TIME_LENGTH) {
                throw new IllegalValueException(MESSAGE_DATETIME_CONSTRAINTS);
            }
        }
    }
    
    public Date getDate() {
        return date;
    }

    public Time getTime() {
        return time;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setTime(Time time) {
        this.time = time;
    }

    @Override
    public String toString() {
        String dateTime = date + " " + time;
        return dateTime.trim();
    }

    @Override
    public boolean isDateTimeAfter(DateTime dateTime) {
        return 
                // Compare Dates
                (!isDateEmpty() && !dateTime.isDateEmpty() 
                && getDate().getLocalDate().isAfter(dateTime.getDate().getLocalDate()))
                
                // Dates are equal, compare Time
                || (!isDateEmpty() && !isTimeEmpty() && !dateTime.isDateEmpty() && !dateTime.isTimeEmpty()
                        && getDate().getLocalDate().isEqual(dateTime.getDate().getLocalDate())
                        && (getTime().getLocalTime().equals(dateTime.getTime().getLocalTime())
                        || getTime().getLocalTime().isAfter(dateTime.getTime().getLocalTime())))
                
                // No dates, compare Time
                || (isDateEmpty() && !isTimeEmpty() && dateTime.isDateEmpty() && !dateTime.isTimeEmpty()
                        && getTime().getLocalTime().isAfter(dateTime.getTime().getLocalTime()));
    }

    @Override
    public boolean isDateTimeAfterCurrentDateTime() {
        return 
                // Compare Dates
                (!isDateEmpty() && getDate().getLocalDate().isBefore(LocalDate.now()))

                // Dates are equal, compare Time
                || (!isDateEmpty() && !isTimeEmpty() && getDate().getLocalDate().isEqual(LocalDate.now())
                        && getTime().getLocalTime().isBefore(LocalTime.now()))
                
                // No dates, compare Time
                || (!isTimeEmpty() && getTime().getLocalTime().isBefore(LocalTime.now()));
    }

    @Override
    public boolean isDateTimeEmpty() {
        return getDate().getLocalDate() == null && getTime().getLocalTime() == null;
    }

    @Override
    public boolean isDateEmpty() {
        return getDate().getLocalDate() == null;
    }

    @Override
    public boolean isTimeEmpty() {
        return getTime().getLocalTime() == null;
    }
    
    //@@author A0153837X
    public String timeLeft(){
    	String hour= null;
    	String day = null;
    	
    	// Unable to calculate time left if EndDateTime is not specified
    	if (this.isDateEmpty() == true){
    		return "Task has no date time specification!";
    	}
    	// Unable to calculate time left for overdued task
    	else if (this.isDateTimeAfterCurrentDateTime() == true){
    		return "Task is overdue!";
    	}
    	
    	day = this.getDate().daysFromNow() + " day(s)";
    	
    	// Different outputs when users have not specified time
    	if (this.isTimeEmpty() == true){
    		hour = ".";
    	}
    	else{
    		hour = ", " + this.getTime().hoursFromNow() + " hour(s) left.";
    	}
    	
    	String result = day + hour;
    	return result;
    }
}
