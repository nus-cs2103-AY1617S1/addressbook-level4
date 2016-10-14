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
    public static final String DATE_FORMAT = "dd/MM/yyyy";
    
    //format: dd/mm/yyyy TODO currently not working for input
    private static final String DATE_VALIDATION_REGEX_FORMAT_1 =
            "[\\p{Digit}]{1,2}/[\\p{Digit}]{1,2}/[\\p{Digit}]{4}";
    //format: ddmmyyyy
    private static final String DATE_VALIDATION_REGEX_FORMAT_2 =
            "[\\p{Digit}]{8}";
    //format: dd monthname yyyy
    //Shortest allowed monthname is 3 characters, longest monthname is September (9 characters)
    private static final String DATE_VALIDATION_REGEX_FORMAT_3 =
            "[\\p{Digit}]{1,2}[ ]?[\\p{Alpha}]{3,9}[ ]?[\\p{Digit}]{4}";
    public static final String DATE_VALIDATION_REGEX_FORMAT = 
            DATE_VALIDATION_REGEX_FORMAT_1 + "|"
            + DATE_VALIDATION_REGEX_FORMAT_2 + "|"
            + DATE_VALIDATION_REGEX_FORMAT_3;

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
        
        try {
            this.date = DateUtil.parseDate(date);
        } catch (DateTimeException dateTimeException) {
            throw new IllegalValueException(MESSAGE_DATE_INVALID);
        }
    }

    /**
     * Returns true if a given string is a valid person name.
     */
    public static boolean isValidDate(String test) {
        return test.matches(DATE_VALIDATION_REGEX_FORMAT);
    }


    @Override
    public String toString() {
        return date.format(DateTimeFormatter.ofPattern(DATE_FORMAT));
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
