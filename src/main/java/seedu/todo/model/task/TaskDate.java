package seedu.todo.model.task;

import java.time.LocalTime;

import seedu.todo.commons.exceptions.IllegalValueException;
import seedu.todo.commons.util.DateTimeUtil;

import java.time.LocalDate;
import java.time.LocalDateTime;
//@@author A0093896H
/**
 * Represents the information for Task's date and time .
 * 
 * Guarantees: valid as long as natty can parse it. The parsing is done in using DateTimeUtil.
 */
public class TaskDate {

    public static final String MESSAGE_DATETIME_CONSTRAINTS = "Do-Do Bird is unable to recognise "
            + "the date and time you entered.\n Please try again! You can refer to our help manual"
            + " for acceptable date and time formats.\n"
            + "Type 'help' to launch the help manual.";
    
    
    public static final String TASK_DATE_ON = "START";
    public static final String TASK_DATE_BY = "END";
    
    private LocalDate date;
    private LocalTime time;
    
    /**
     * Validates given date and time string.
     *
     * @throws IllegalValueException if given date and time string is invalid.
     */
    public TaskDate(String dateTimeString, String onOrBy) throws IllegalValueException {
        
        assert onOrBy != null;
        
        if (DateTimeUtil.isEmptyDateTimeString(dateTimeString)) {
            this.date = null;
            this.time = null;
        } else {
            LocalDateTime ldt = DateTimeUtil.parseDateTimeString(dateTimeString, onOrBy);
            if (ldt == null) {
                throw new IllegalValueException(MESSAGE_DATETIME_CONSTRAINTS);
            } else {
                this.date = ldt.toLocalDate();
                this.time = ldt.toLocalTime();
            }  
        }
    }

    public LocalDate getDate() {
        return this.date;
    }
    
    public LocalTime getTime() {
        return this.time;
    }
    
    public LocalDate setDate(LocalDate date) {
        return this.date = date;
    }
    
    public LocalTime setTime(LocalTime time) {
        return this.time = time;
    }
    
    //@@author A0121643R-unused    
    /**
     * earlier date is smaller so that it can be shown before task with later date
     */
	public int compareTo(TaskDate other) {
		if (this.getDate() == null && other.getDate() == null) {
			return 0;
		} else if (this.getDate() == null) {
			return 1;
		} else if (other.getDate() == null) {
			return -1;
		} else if (this.getDate().equals(other.getDate())) {
			return this.getTime().compareTo(other.getTime());			
		} else {
			return this.getDate().compareTo(other.getDate());
		}
	}
	//@@author

    @Override
    public String toString() {
        String dateString;
        String timeString;
        
        if (date == null) {
            dateString = "";
        } else {
            dateString = DateTimeUtil.prettyPrintDate(date);
        }
        
        if (time == null) {
            timeString = "";
        } else {
            timeString = DateTimeUtil.prettyPrintTime(time);
        }

        return dateString + " " + timeString;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof TaskDate // instanceof handles nulls
                && ((this.date == null && ((TaskDate) other).date == null)
                || ((this.date != null && ((TaskDate) other).date != null)
                && DateTimeUtil.combineLocalDateAndTime(this.date, this.time)
                    .equals(DateTimeUtil.combineLocalDateAndTime(((TaskDate) other).date, ((TaskDate) other).time)))));
    }

    @Override
    public int hashCode() {
        return this.toString().hashCode();
    }

}
