package seedu.tasklist.model.task;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

import seedu.tasklist.commons.exceptions.IllegalValueException;

/**
 * Represents a Task's date in the task list. Guarantees: immutable; is valid as
 * declared in {@link #isValidLocalDate(String)}
 */
public class Date {

    private static final String MESSAGE_DATE_CONSTRAINTS = "Invalid Date format\nDate format: DDMMYYYY";
    private static final String DATE_VALIDATION_REGEX = "^(?:[0-9 ]+|)$";

    private final LocalDate localDate;

    /**
     * Validates given date.
     *
     * @throws IllegalValueException
     *             if given date string is invalid.
     */
    public Date(String localDate) throws IllegalValueException {
        assert localDate != null;
        if (!isValidLocalDate(localDate)) {
            throw new IllegalValueException(MESSAGE_DATE_CONSTRAINTS);
        }

        if (!localDate.isEmpty() && localDate.length() == 8) {
            this.localDate = LocalDate.of(Integer.parseInt(localDate.substring(4, 8)), Integer.parseInt(localDate.substring(2, 4)),
                    Integer.parseInt(localDate.substring(0, 2)));
        } else {
            this.localDate = null;
        }
    }

    /**
     * Returns true if a given string is a valid date.
     */
    public static boolean isValidLocalDate(String test) {
        return test.matches(DATE_VALIDATION_REGEX);
    }
    
    public LocalDate getLocalDate() {
        return localDate;
    }

    @Override
    public String toString() {
        if (localDate != null) {
            DateTimeFormatter df = DateTimeFormatter.ofPattern("dd-MM-yyyy");
            return df.format(localDate);
        } else {
            return "";
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Date // instanceof handles nulls
                        && this.localDate.equals(((Date) other).localDate)); // state
                                                                   // check
    }

    @Override
    public int hashCode() {
        return localDate.hashCode();
    }
    
    //@@author A0153837X
    public String daysFromNow(){
    	// Calculate how many days in between
    	long days = ChronoUnit.DAYS.between(LocalDate.now(), localDate);
   		return Long.toString(days);
    }
    
}
