package seedu.address.model.task;

import java.time.LocalDate;

import seedu.address.commons.exceptions.IllegalValueException;

/**
 * @@author A0138993L
 * Represents a Task date in Simply.
 * Guarantees: immutable; is valid as declared in {@link #isValidDate(String)}
 */
public class Date {

    public static final String MESSAGE_DATE_CONSTRAINTS = "Dates should be entered in the format DDMMYY, DD.MM.YY, DD/MM/YY, DD-MM-YY";
    public static final String DATE_VALIDATION_REGEX = "([3][01][1][012]\\d{2})|([3][01][0]\\d{3})|([012]\\d{1}[1][012]\\d{2})|"+ //6digits
    												   "([012]\\d{1}[0]\\d{3})|"+
    												   "([3][01]-[1][012]-\\d{2})|([3][01]-\\d{1}-\\d{2})|([12]\\d{1}-[1][012]-\\d{2})|"+ //2d-2d-2d
    												   "([12]\\d{1}-\\d{1}-\\d{2})|(\\d{1}-[1][012]-\\d{2})|(\\d{1}-\\d{1}-\\d{2})|"+
    												   "([3][01]\\.[1][012]\\.\\d{2})|([3][01]\\.\\d{1}\\.\\d{2})|([12]\\d{1}\\.[1][012]\\.\\d{2})|"+ //2d.2d.2d
    												   "([12]\\d{1}\\.\\d{1}\\.\\d{2})|(\\d{1}\\.[1][012]\\.\\d{2})|(\\d{1}\\.\\d{1}\\.\\d{2})|"+
    												   "([3][01]/[1][012]/\\d{2})|([3][01]/\\d{1}/\\d{2})|([12]\\d{1}/[1][012]/\\d{2})|"+ //2d/2d/2d
    												   "([12]\\d{1}/\\d{1}/\\d{2})|(\\d{1}/[1][012]/\\d{2})|(\\d{1}/\\d{1}/\\d{2})|"+
    												   "(no date)";
    public static final String MESSAGE_PAST_DATE = "Cannot enter a date that have already past!";
    
    public final String value;
    private int beforeCurrentDate;

    /**
     * Validates given date.
     * @@author A0138993L
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
        date = standardFormatDate(date);
        if (isAfterCurrentDate(date) ==1) {
        	beforeCurrentDate =1; //correct
        }
        else if (isAfterCurrentDate(date) ==2) {
        	beforeCurrentDate =2; //on the day itself
        }
        else
        	beforeCurrentDate =0; //past date
      	this.value = date;
    }
    //@@author A0138993L
	public int isAfterCurrentDate(String date) {
    	if (date.contains("-")) {
			String[] date_cat = date.split("-");
			String date_year = "20" + date_cat[2];
			LocalDate now = LocalDate.now();
			LocalDate test = LocalDate.of(Integer.parseInt(date_year), Integer.parseInt(date_cat[1]), Integer.parseInt(date_cat[0]));
			if (test.isAfter(now))
				return 1;
			else if (test.isEqual(now))
				return 2;
			else	
				return 0;
    	}
    	else//accounting for no date
    		return 1;
	}
	//@@author A0138993L
	private String standardFormatDate(String date) {
    	if (date.equals("default"))
    		return local_date();
    	else if (date.equals("no date"))
    		return date;
    	else if (date.contains("."))
			return date.replaceAll("\\.", "-");
		else if (date.contains("-"))
			return date;
		else if (date.contains("/"))
			return date.replaceAll("/",  "-");
		else {
			return date.substring(0, 2) + "-" + date.substring(2, 4) + "-" + date.substring(4, 6);
		}
	}
	//@@author A0138993L
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
    	if (test.matches(DATE_VALIDATION_REGEX) || test.equals("default"))
    		return true;
    	else
    		return false;
    }
    
    public int getBeforeCurrentDate() {
    	return beforeCurrentDate;
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
