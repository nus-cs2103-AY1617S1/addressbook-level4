package tars.model.task;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import tars.commons.exceptions.IllegalValueException;

/**
 * Represents a Task's datetime in tars.
 */
public class DateTime {
    public static final String MESSAGE_NAME_CONSTRAINTS = "Task datetime should be spaces or alphanumeric characters";

    private LocalDateTime startDate;
    private LocalDateTime endDate;

    /**
     * Validates given task datetime.
     *
     * @throws DateTimeParseException
     *             if given datetime string is invalid.
     * @throws IllegalDateException
     *             end date occurring before start date.
     */
    public DateTime(String startDate, String endDate) throws DateTimeParseException, IllegalDateException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
        if (startDate != null) {
            this.startDate = LocalDateTime.parse(startDate, formatter);
        }

        this.endDate = LocalDateTime.parse(endDate, formatter);

        if (this.endDate.isBefore(this.startDate)) {
            throw new IllegalDateException("End date should be after start date.");
        }
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
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
