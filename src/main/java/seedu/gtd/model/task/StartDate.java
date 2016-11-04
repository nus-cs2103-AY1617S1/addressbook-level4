package seedu.gtd.model.task;

import seedu.gtd.commons.exceptions.IllegalValueException;

/**
 * Represents a Task's start date in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidDueDate(String)}
 */
public class StartDate {

    public static final String MESSAGE_STARTDATE_CONSTRAINTS = "Task startdate is formatted like the following: Wed Nov 02 15:39:55 UTC 2016 \n" 
            + " Accepted formal dates: 1978-01-28, 1984/04/02, 1/02/1980, 2/28/79 \n"
            + " Relaxed dates: The 31st of April in the year 2008, Fri, 21 Nov 1997, Jan 21, '97, Sun, Nov 21, jan 1st, february twenty-eighth \n"
            + " Relative dates: next thursday, last wednesday, today, tomorrow, yesterday, next week, next month, next year, 3 days from now, three weeks ago \n"
            + " Prefixes: day after, the day before, the monday after, the monday before, 2 fridays before, 4 tuesdays after \n"
            + " Time: 0600h, 06:00 hours, 6pm, 5:30 a.m., 5, 12:59, 23:59, 8p, noon, afternoon, midnight \n" 
            + " Relative times: 10 seconds ago, in 5 minutes, 4 minutes from now.";

    public final String value;

    /**
     * Validates given start date.
     *
     * @throws IllegalValueException if given start date string is invalid.
     */
    public StartDate(String startdate) throws IllegalValueException {
        assert startdate != null;
        try{
	        if (isInvalidStartDate(startdate)) {
	            throw new IllegalValueException(MESSAGE_STARTDATE_CONSTRAINTS);
	        }
        }catch(NullPointerException e){
        	startdate = new String("none");
        }
    	this.value = startdate;
    }

    /**
     * Returns true if a given string is a valid task due date number.
     */
    public static boolean isInvalidStartDate(String test) throws NullPointerException {
   		return test.isEmpty();
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof StartDate // instanceof handles nulls
                && this.value.equals(((StartDate) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
