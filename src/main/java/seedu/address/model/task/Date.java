package seedu.address.model.task;

import java.time.LocalDate;

import seedu.address.commons.exceptions.IllegalValueException;

/**
 * Represents a Task date in Simply.
 * Guarantees: immutable; is valid as declared in {@link #isValidDate(String)}
 */
public class Date {

    public static final String MESSAGE_DATE_CONSTRAINTS = "Dates should only contain 6 numbers";
    public static final String DATE_VALIDATION_REGEX = "([3][01][1][012]\\d{2})|([3][01][0]\\d{3})|([012]\\d{1}[1][012]\\d{2})|"+ //6digits
    												   "([012]\\d{1}[0]\\d{3})|"+
    												   "([3][01]-[1][012]-\\d{2})|([3][01]-\\d{1}-\\d{2})|([12]\\d{1}-[1][012]-\\d{2})|"+ //2d-2d-2d
    												   "([12]\\d{1}-\\d{1}-\\d{2})|(\\d{1}-[1][012]-\\d{2})|(\\d{1}-\\d{1}-\\d{2})|"+
    												   "([3][01]\\.[1][012]\\.\\d{2})|([3][01]\\.\\d{1}\\.\\d{2})|([12]\\d{1}\\.[1][012]\\.\\d{2})|"+ //2d.2d.2d
    												   "([12]\\d{1}\\.\\d{1}\\.\\d{2})|(\\d{1}\\.[1][012]\\.\\d{2})|(\\d{1}\\.\\d{1}\\.\\d{2})|"+
    												   "([3][01]/[1][012]/\\d{2})|([3][01]/\\d{1}/\\d{2})|([12]\\d{1}/[1][012]/\\d{2})|"+ //2d/2d/2d
    												   "([12]\\d{1}/\\d{1}/\\d{2})|(\\d{1}/[1][012]/\\d{2})|(\\d{1}/\\d{1}/\\d{2})|"+
    												   "(no date)";

    public final String value;

    /**
     * Validates given date.
     *
     * @throws IllegalValueException if given date string is invalid.
     */
    public Date(String date) throws IllegalValueException {
        //assert date != null;
        if (date == null)
    		date = "default";
        date = date.trim();
        if (!isValidDate(date)) {
            throw new IllegalValueException(MESSAGE_DATE_CONSTRAINTS);
        }
        if (date == "default") {
        	//System.out.println("test");
        	this.value = local_date();
        }
        else
        	this.value = date;
    }

    public String local_date(){
    	/*LocalDate current_date = LocalDate.now();
    	System.out.println(current_date);
    	System.out.println(current_date.getDayOfMonth() + "/" + current_date.getMonthValue() + "/" + current_date.getYear());*/
    	LocalDate now = LocalDate.now();
    	String date = now.toString();
    	String[] date_cat = date.split("-");
    	String shortened_year = new String(date_cat[0].substring(2));
    	//System.out.println(date_cat[2] + date_cat[1] + shortened_year);
    	return date_cat[2] + "-" + date_cat[1] + "-" + shortened_year;
    }
    /**
     * Returns true if a given string is a valid task date.
     */
    public static boolean isValidDate(String test) {
    	if (test.matches(DATE_VALIDATION_REGEX) || test == "default")
    		return true;
    	else
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
