package seedu.todolist.model.task;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import seedu.todolist.commons.exceptions.IllegalValueException;
import seedu.todolist.model.parser.DateParser;

//author A0138601M
/**
 * Represents a Task's date in the to do list.
 * Guarantees: immutable; is valid as declared in {@link #isValidDate(String)}
 */
public class TaskDate {
    
    public static final String MESSAGE_DATE_CONSTRAINTS = "Task date should be in the format 'dd/mm/yyyy' "
                                                        + "or 'dd monthname yyyy'.";
    public static final String MESSAGE_DATE_INVALID = "Task date provided is invalid!";
    
    //format: 'dd/mm/yyyy'
    public static final String DATE_VALIDATION_REGEX_1 = "((\\p{Digit}){1,2}/(\\p{Digit}){1,2}(/(\\p{Digit}){4})?)";
    //format: 'dd monthname yyyy'
    //full monthname (october) and abbreviated monthname (oct) are accepted.
    public static final String DATE_VALIDATION_REGEX_2 = "((\\p{Digit}){1,2}\\s?(\\p{Alpha}){3,9}\\s?((\\p{Digit}){4})?)";
    public static final String DATE_VALIDATION_REGEX_FORMAT = DATE_VALIDATION_REGEX_1 + "|" + DATE_VALIDATION_REGEX_2;
    
    public static final String DATE_DISPLAY_FORMAT = "dd MMM yyyy";
    
    public final LocalDate date;

    /**
     * Validates given date.
     *
     * @throws IllegalValueException if given date is invalid.
     */
    public TaskDate(String date) throws IllegalValueException {
        assert date != null;
        date = date.trim();
        if (!isValidDate(date)) {
            throw new IllegalValueException(MESSAGE_DATE_CONSTRAINTS);
        }
        
        try {
            this.date = DateParser.parseDate(date);
        } catch (DateTimeException dateTimeException) {
            throw new IllegalValueException(MESSAGE_DATE_INVALID);
        }
    }

    /**
     * Returns true if a given string is a valid task date.
     */
    private boolean isValidDate(String test) {
        if (test.matches(DATE_VALIDATION_REGEX_FORMAT)) {
            return true;
        }
        return false;
    }
    
    /**
     * Returns true if this date is earlier than given date.
     */
    public boolean isBefore(TaskDate other) {
        return this.date.isBefore(other.getDate());
    }

    public LocalDate getDate() {
        return this.date;
    }

    @Override
    public String toString() {
        if (this.date != null) {
            return date.format(DateTimeFormatter.ofPattern(DATE_DISPLAY_FORMAT));
        }
        return null;
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

}
