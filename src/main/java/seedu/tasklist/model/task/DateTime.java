package seedu.tasklist.model.task;

import java.util.Optional;

import seedu.tasklist.commons.exceptions.IllegalValueException;

/**
 * Represents a Task's date and time in the task list.
 */
public class DateTime {
    private static final String MESSAGE_DATETIME_CONSTRAINTS = "Invalid Date Time format\nDate format: DDMMYYYY\n24-hour Time format: HHMM";
    
    private static final int DATE_LENGTH = 8;
    private static final int TIME_LENGTH = 4;
    private static final int VALID_DATE_TIME_LENGTH = 0;    // Valid Date Time length is either 0, 4 or 8
    
    private Optional<Date> date;
    private Optional<Time> time;

    public DateTime() throws IllegalValueException {
        this.date = Optional.of(new Date(""));
        this.time = Optional.of(new Time(""));
    };
    
    public DateTime(String arguments) throws IllegalValueException {
        this();
        String[] args = arguments.trim().split(" ");
        for (String datetime : args) {
            if (datetime.length() == TIME_LENGTH) {
                this.time = Optional.of(new Time(datetime));
            } else if (datetime.length() == DATE_LENGTH) {
                this.date = Optional.of(new Date(datetime));
            } else if (datetime.length() != VALID_DATE_TIME_LENGTH) {
                throw new IllegalValueException(MESSAGE_DATETIME_CONSTRAINTS);
            }
        }
    }
    
    public Date getDate() {
        return date.get();
    }

    public Time getTime() {
        return time.get();
    }

    public void setDate(Date date) {
        this.date = Optional.of(date);
    }

    public void setTime(Time time) {
        this.time = Optional.of(time);
    }

    @Override
    public String toString() {
        String dateTime = "";
        try {
            dateTime = date.orElse(new Date("")) + " " + time.orElse(new Time(" "));
            return dateTime.trim();
        } catch (IllegalValueException e) {
            return dateTime;
        }
    }
}
