package seedu.taskitty.model.task;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import seedu.taskitty.commons.exceptions.IllegalValueException;

/**
 * Represents a Task's date in the task manager.
 * Guarantees: immutable; is valid as declared in {@link #isValidDateFormat(String)}
 */
public class TaskDate {

    public static final String MESSAGE_DATE_CONSTRAINTS =
            "Task dates should be in the format dd/mm/yyyy";
    public static final String MESSAGE_DATE_INVALID =
            "Task date provided is invalid!";
    public static final String MESSAGE_DATE_MISSING =
            "Task date must be provided";
    public static final String DATE_FORMAT_STRING = "dd/MM/yyyy";
    public static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern(DATE_FORMAT_STRING);
    
    //format: dd/mm/yyyy
    private static final String DATE_VALIDATION_REGEX = "[\\p{Digit}]{1,2}/[\\p{Digit}]{1,2}/[\\p{Digit}]{4}";

    public final LocalDate date;

    /**
     * Validates given name.
     *
     * @throws IllegalValueException if given name string is invalid.
     */
    public TaskDate(String date) throws IllegalValueException {
        if (date == null) {
            throw new IllegalValueException(MESSAGE_DATE_MISSING);
        }
        
        date = date.trim();
      //This is not an assert because user can change the database and input wrong formats
        if (!isValidDateFormat(date)) {
            throw new IllegalValueException(MESSAGE_DATE_CONSTRAINTS);
        }
        
        try {
            this.date = LocalDate.parse(date, DATE_FORMATTER);
        } catch (DateTimeParseException dtpe){
            throw new IllegalValueException(MESSAGE_DATE_INVALID);
        }
    }

    /**
     * Returns true if a given string is a valid person name.
     */
    public static boolean isValidDateFormat(String test) {
        return test.matches(DATE_VALIDATION_REGEX);
    }


    @Override
    public String toString() {
        return date.format(DATE_FORMATTER);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof TaskDate // instanceof handles nulls
                && this.date.equals(((TaskDate) other).date)); // state check
    }

    @Override
    public int hashCode() {
        return date.hashCode();
    }
    
    public LocalDate getDate() {
    	return date;
    }

}
