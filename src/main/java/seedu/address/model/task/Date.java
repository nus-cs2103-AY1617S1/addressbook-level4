package seedu.address.model.task;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.address.commons.exceptions.IllegalValueException;

/**
 * Represents a Task's date in the to-do list.
 * Guarantees: immutable; is valid as declared in {@link #isValidDate(String)}
 */
public class Date {

    public static final String MESSAGE_DATE_CONSTRAINTS = "Task date should be in the format dd/mm/yy";
    public static final String DATE_VALIDATION_REGEX = "(0?[1-9]|[12][\\d]|3[01])/(0?[1-9]|1[012])/(\\d\\d)";

    public final String value;

    /**
     * Validates given date number.
     *
     * @throws IllegalValueException if given date string is invalid.
     */
    public Date(String date) throws IllegalValueException {
        assert date != null;
        date = date.trim();
        if (!isValidDate(date)) {
            throw new IllegalValueException(MESSAGE_DATE_CONSTRAINTS);
        }
        this.value = date;
    }

    /**
     * Returns true if a given string is a valid task date number.
     */
    public static boolean isValidDate(String test) {
    	Matcher matcher;
    	Pattern pattern;
    	
    	pattern = Pattern.compile(DATE_VALIDATION_REGEX);
    	matcher = pattern.matcher(test);
    	
    	if(matcher.matches()){
    		matcher.reset();
    		if(matcher.find()){
    			int day = Integer.parseInt(matcher.group(1));
    			int month = Integer.parseInt(matcher.group(2));
    			int year = Integer.parseInt(matcher.group(3));
    			
    			switch (month) {
                case 1:
                case 3:
                case 5:
                case 7:
                case 8:
                case 10:
                case 12: return day < 32;
                case 4:
                case 6:
                case 9:
                case 11: return day < 31;
                case 2: 
                    if (year%4 == 0) {
                        //its a leap year
                        return day < 30;
                    } else {
                        return day < 29;
                    }
                default:
                    break;
                }
    		}
    	}
    	return false;
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Date // instanceof handles nulls
                && this.value.equals(((Date) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
