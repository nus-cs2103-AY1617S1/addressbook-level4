package seedu.taskmanager.model.item;


import java.time.LocalDateTime;

import seedu.taskmanager.commons.exceptions.IllegalValueException;

/**
 * Represents a Item's date in the task manager.
 * Can be either startDate or endDate
 */
public class ItemDate {
    
    public static final String DATE_FORMAT = "yyyy-MM-dd";
    public static final String ALTERNATE_DATE_FORMAT = "MM-dd";
    public static final String MESSAGE_DATE_CONSTRAINTS = "Date input must be in YYYY-MM-DD or MM-DD";
    public static final String DATE_VALIDATION_REGEX = "\\d{4}-(0[1-9]|1[012])-(0[1-9]|[12][0-9]|3[01])";
    
    //@@author A0140060A
    public static final String EMPTY_DATE = "";


    public final String value;
    
    /**
     * Convenience constructor for empty ItemDate
     */
    public ItemDate() {
        value = EMPTY_DATE;
    }
    
    //@@author 
    
    /**
     * Validates given date.
     *
     * @throws IllegalValueException if given date string is invalid.
     */
    public ItemDate(String date) throws IllegalValueException {
        assert date != null;
        date = date.trim();
        if (date != null && !date.isEmpty()) {
            String parts[] = date.split("-");
            // If only MM-dd
            if (parts.length < 3) {
                // Try adding year in front
                LocalDateTime ldt = LocalDateTime.now();
                date = ldt.getYear() + "-" + date;
            }
        }
        if (!isValidDate(date)) {
            throw new IllegalValueException(MESSAGE_DATE_CONSTRAINTS);
        }
        this.value = date;
    }

    /**
     * Returns if a given string is a valid date.
     */
    public static boolean isValidDate(String test) {
    	if (test.equals(EMPTY_DATE)) {
    		return true;
    	}
        return test.matches(DATE_VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ItemDate // instanceof handles nulls
                && this.value.equals(((ItemDate) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
