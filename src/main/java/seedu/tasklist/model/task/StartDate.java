package seedu.tasklist.model.task;

import seedu.tasklist.commons.exceptions.IllegalValueException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
/**
 * Represents a Task's start date in the task list.
 * Guarantees: immutable; is valid as declared in {@link #isValidStartDate(String)}
 */
public class StartDate {

    public static final String MESSAGE_STARTDATE_CONSTRAINTS = "StartDate should be numeric only";
    public static final String DATE_VALIDATION_REGEX = "^(?:\\d+|)$";
    //public static final String DATE_VALIDATION_REGEX = "";
    public LocalDate startDate;
    //public LocalDateTime startDateTime;
    /**
     * Validates given start date.
     *
     * @throws IllegalValueException if given startDate string is invalid.
     */
    public StartDate(String startDate) throws IllegalValueException {
        String month;
        String day;
        String year;
//    	String hour;
//    	String minute;
//    	String[] dateTime;
    	
    	assert startDate != null;
        startDate = startDate.trim();
        if (!isValidStartDate(startDate)) {
            throw new IllegalValueException(MESSAGE_STARTDATE_CONSTRAINTS);
        } 
        
        if(!startDate.isEmpty()){
//           	month = "" + startDate.charAt(0) + startDate.charAt(1);
//           	day = "" + startDate.charAt(2) + startDate.charAt(3);
//           	year = "" + startDate.charAt(4) + startDate.charAt(5) + startDate.charAt(6) + startDate.charAt(7);
//            
//            this.startDate = LocalDate.of(Integer.parseInt(year), Integer.parseInt(month), Integer.parseInt(day));
        	this.startDate = LocalDate.of(Integer.parseInt(startDate.substring(4, 7)), Integer.parseInt(startDate.substring(0, 1))
            		, Integer.parseInt(startDate.substring(2, 3)));
            
        }
            
            else{
            	//this.startDate = LocalDate.MAX;
            }
        
//        if(!startDate.equals("")){
//        	dateTime = startDate.split(" ");
//        	month = "" + dateTime[0].charAt(0) + dateTime[0].charAt(1);
//        	day = "" + dateTime[0].charAt(2) + dateTime[0].charAt(3);
//        	year = "" + dateTime[0].charAt(4) + dateTime[0].charAt(5) + dateTime[0].charAt(6) + dateTime[0].charAt(7);
//        	hour = "" + dateTime[1].charAt(0) + dateTime[1].charAt(1);
//        	minute = "" + dateTime[1].charAt(2) + dateTime[1].charAt(3);
//        	
//        	//this.startDate = LocalDate.of(Integer.parseInt(year), Integer.parseInt(month), Integer.parseInt(day));
//        	startDateTime = LocalDateTime.of(Integer.parseInt(year), Integer.parseInt(month), Integer.parseInt(day),
//        			Integer.parseInt(hour), Integer.parseInt(minute));
//        } else{
//        	//this.startDate = LocalDate.now();
//        	startDateTime = LocalDateTime.now();
//        }
    }

    /**
     * Returns true if a given string is a valid start date.
     */
    public static boolean isValidStartDate(String test) {
        return test.matches(DATE_VALIDATION_REGEX);
    }

    @Override
    public String toString() {

    	DateTimeFormatter df = DateTimeFormatter.ofPattern("LLddyyyy");
    	return df.format(startDate);
        //return startDateTime.toString();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof StartDate // instanceof handles nulls
                && this.startDate.equals(((StartDate) other).startDate)); // state check
    }

    @Override
    public int hashCode() {
        return startDate.hashCode();
    }

}
