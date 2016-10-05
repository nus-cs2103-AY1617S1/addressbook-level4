package tars.model.task;

import java.time.DateTimeException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.ResolverStyle;

import tars.commons.exceptions.IllegalValueException;

/**
 * Represents a Task's datetime in tars.
 */
public class DateTime {
    public static final String MESSAGE_DATETIME_CONSTRAINTS = "Task datetime should be spaces or alphanumeric characters";

    private LocalDateTime startDate;
    private LocalDateTime endDate;

    public String startDateString;
    public String endDateString;
    
    /**
     * Default constructor
     */
    public DateTime() {}

    /**
     * Validates given task dateTime.
     *
     * @throws DateTimeException if given dateTime string is invalid.
     * @throws IllegalDateException end date occurring before start date.
     */
    public DateTime(String startDate, String endDate)
            throws DateTimeException, IllegalDateException {
        DateTimeFormatter formatter = DateTimeFormatter
                .ofPattern("d/M/uuuu HHmm")
                .withResolverStyle(ResolverStyle.STRICT);
        this.endDate = LocalDateTime.parse(endDate, formatter);
        this.endDateString = endDate;

        if (startDate != null) {
            this.startDate = LocalDateTime.parse(startDate, formatter);
            this.startDateString = startDate;
            if (this.endDate.isBefore(this.startDate) || this.endDate.isEqual(this.startDate)) {
                throw new IllegalDateException("End dateTime should be after start dateTime.");
            }
        }
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }

    @Override
    public String toString() {
        if (this.startDate == null) {
            return endDateString;
        } else {
            return startDateString + " to " + endDateString;
        }
    }

    /**
     * Signals an error caused by end date occurring before start date
     */
    public class IllegalDateException extends IllegalValueException {
        public IllegalDateException(String message) {
            super(message);
        }
    }
    
}
