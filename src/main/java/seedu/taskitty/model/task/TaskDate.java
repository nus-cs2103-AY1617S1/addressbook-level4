package seedu.taskitty.model.task;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import seedu.taskitty.commons.exceptions.IllegalValueException;
import seedu.taskitty.commons.util.DateUtil;

/**
 * Represents a Task's date in the task manager.
 * Guarantees: immutable; is valid as declared in {@link #isValidDate(String)}
 */
public class TaskDate {

    public static final String MESSAGE_DATE_CONSTRAINTS =
            "Task dates should be in the format dd/mm/yyyy or ddmmyyyy or dd monthname yyyy";
    public static final String MESSAGE_DATE_INVALID =
            "Task date provided is invalid!";
    public static final String MESSAGE_DATE_MISSING =
            "Task date must be provided";
    public static final String DATE_FORMAT_STRING = "dd/MM/yyyy";
    public static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern(DATE_FORMAT_STRING);
    
    //format: mm/dd/yyyy
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
        if (!isValidDate(date)) {
            throw new IllegalValueException(MESSAGE_DATE_CONSTRAINTS);
        }
        
        this.date = LocalDate.parse(date, DATE_FORMATTER);
    }

    /**
     * Returns true if a given string is a valid person name.
     */
    public static boolean isValidDate(String test) {
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
