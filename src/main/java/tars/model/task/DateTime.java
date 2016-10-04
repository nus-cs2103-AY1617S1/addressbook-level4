package tars.model.task;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * Represents a Task's datetime in tars.
 */
public class DateTime {
    public static final String MESSAGE_NAME_CONSTRAINTS = "Task datetime should be spaces or alphanumeric characters";

    private LocalDateTime dateTime;

    /**
     * Validates given task datetime.
     *
     * @throws DateTimeParseException if given datetime string is invalid.
     */
    public DateTime(String dateTime) throws DateTimeParseException{
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
        this.dateTime = LocalDateTime.parse(dateTime, formatter);
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }
    
}
