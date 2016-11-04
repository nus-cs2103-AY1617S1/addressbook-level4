package seedu.simply.model.task;

import java.time.LocalDate;

import seedu.simply.commons.exceptions.IllegalValueException;

/**
 * @@author A0138993L
 * Represents a Task date in Simply.
 * Guarantees: immutable; is valid as declared in {@link #isValidDate(String)}
 */
public class Date implements Comparable<Date> {

    public static final String MESSAGE_DATE_CONSTRAINTS = "Dates should be entered in the format DDMMYY, DD.MM.YY, DD/MM/YY, DD-MM-YY";
    public static final String DATE_VALIDATION_REGEX = "([3][01][1][012]\\d{2})|([3][01][0]\\d{3})|([012]\\d{1}[1][012]\\d{2})|" 
            + "([012]\\d{1}[0]\\d{3})|" //DDMMYY
            + "([3][01]-[1][012]-\\d{2})|([3][01]-[0]\\d{1}-\\d{2})|([12]\\d{1}-[1][012]-\\d{2})|" //DD-MM-YY
            + "([12]\\d{1}-[0]\\d{1}-\\d{2})|([0]\\d{1}-[1][012]-\\d{2})|([0]\\d{1}-[0]\\d{1}-\\d{2})|"
            + "([3][01]\\.[1][012]\\.\\d{2})|([3][01]\\.[0]\\d{1}\\.\\d{2})|([12]\\d{1}\\.[1][012]\\.\\d{2})|" //DD.MM.YY
            + "([12]\\d{1}\\.[0]\\d{1}\\.\\d{2})|([0]\\d{1}\\.[1][012]\\.\\d{2})|([0]\\d{1}\\.[0]\\d{1}\\.\\d{2})|"
            + "([3][01]/[1][012]/\\d{2})|([3][01]/[0]\\d{1}/\\d{2})|([12]\\d{1}/[1][012]/\\d{2})|" //DD/MM/YY
            + "([12]\\d{1}/[0]\\d{1}/\\d{2})|([0]\\d{1}/[1][012]/\\d{2})|([0]\\d{1}/[0]\\d{1}/\\d{2})|"
            + "(no date)";
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
        beforeCurrentDate = isAfterCurrentDate(date);
        this.value = date;
    }
    //@@author A0138993L
    /**
     * checks if the current date have been past
     * @param date
     * @return int value of 1 if date have past 2 if current date 0 if date is in the future
     */
	public int isAfterCurrentDate(String date) {
    	if (date.contains("-")) {
			String[] date_cat = date.split("-");
			String date_year = "20" + date_cat[2];
			LocalDate test = LocalDate.of(Integer.parseInt(date_year), Integer.parseInt(date_cat[1]), Integer.parseInt(date_cat[0]));
            LocalDate now = LocalDate.now();
			return currentDateStatus(now, test);
    	}
    	else//accounting for no date
    		return 1;
	}
	/**
	 * @@author A0138993L
	 * @param now  current local date
	 * @param test the date entered by the user
	 * @return the status of the date with 1 being past and 2 being equal and 0 being in the future
	 */
    private int currentDateStatus(LocalDate now, LocalDate test) {
        if (test.isAfter(now))
        	return 1;
        else if (test.isEqual(now))
        	return 2;
        else	
        	return 0;
    }
	//@@author A0138993L
	/**
	 * standardize the date format to DD-MM-YY
	 * @param date
	 * @return the date format of DD-MM-YY
	 */
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
    	LocalDate now = LocalDate.now();
    	String date = now.toString();
    	String[] date_cat = date.split("-");
    	String shortened_year = new String(date_cat[0].substring(2));
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
    
  //@@author A0139430L
    @Override
    public int compareTo(Date o) { 
        if(this.toString().compareTo("no date")==0 & o.toString().compareTo("no date")==0)
            return 0;
        else if(this.toString().compareTo("no date")==0 )
            return -1;
        else if(o.toString().compareTo("no date")==0 )
            return 1;
        
        String[] temp = this.value.split("-");
        String[] temp2 = o.toString().split("-");
        
        String date = temp[2].concat(temp[1]).concat(temp[0]);
        String date2 = temp2[2].concat(temp2[1]).concat(temp2[0]);
        
        return date.compareTo(date2);
    }

}
