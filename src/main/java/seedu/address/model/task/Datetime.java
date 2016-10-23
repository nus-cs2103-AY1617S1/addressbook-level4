package seedu.address.model.task;

import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.joestelmach.natty.Parser;

import seedu.address.commons.exceptions.IllegalValueException;

/**
 * Represents a Task's Date and Time in the to-do-list. Guarantees: immutable;
 * 
 */
public class Datetime {

    public static final String MESSAGE_DATETIME_CONSTRAINTS = "Date can be DD-MM-YYYY and Time can be 24h format";

    public static final String MESSAGE_DATETIME_CONTAINS_DOTS = "Date should be in MM-DD-YYYY format and cannot contain '.' character";


    public static final String MESSAGE_DATE_CONSTRAINTS = "Date should be in DD-MM-YYYY format";
    public static final String DATE_INCORRECT_REGEX = ".*" + "(0?[1-9]|[12][0-9]|3[01])" + "\\." 
        		+ "(0?[1-9]|1[012])" + "\\." + "\\d{2}(\\{2}){0,1}" + ".*";
    public static final Pattern DATE_CORRECT_REGEX = Pattern.compile("(?<day>(0?[1-9]|[12][0-9]|3[01]))" + "-" 
    		+ "(?<month>(0?[1-9]|1[012]))" + "-" + "(?<year>\\d{2}(\\{2}){0,1})" + "(?<time>.*)");

    //public static final String MESSAGE_TIME_CONSTRAINTS = "Time should be in 24hr format. Eg. 2359";
    //    public static final String TIME_VALIDATION_REGEX = "([01]?[0-9]|2[0-3])[0-5][0-9]";

    private Date start;
    private Date end;

    public Datetime(String input) throws IllegalValueException {

        Parser natty = initNatty();
        List<Date> listOfDate;

        // 'date/' not found -> floating task
        if (input == null) {
            listOfDate = null;
        }
        // check input for '.' characters in date
        else if (input.matches(DATE_INCORRECT_REGEX)){
    		throw new IllegalValueException(MESSAGE_DATETIME_CONTAINS_DOTS);
        }
        // empty string preceding "date/" -> convert deadline or event to floating task
        else if (input.equals("")) {
            listOfDate = null;
        }
        // natty returns non-empty list if input is parse-able
        else if (!natty.parse(input).isEmpty()) {
        	// rearrange DD-MM-YY to parse-able MM-DD-YY 
        	final Matcher matcher = DATE_CORRECT_REGEX.matcher(input.trim());
            if (matcher.matches()){
        		input = matcher.group("month") + "-" + matcher.group("day") + "-" + matcher.group("year") + matcher.group("time");
        	}
            listOfDate = natty.parse(input).get(0).getDates();
        }
        // natty returns empty list if input is not parse-able
        else {
            throw new IllegalValueException(MESSAGE_DATETIME_CONSTRAINTS);
        }
        
        //floating task
        if (listOfDate == null) {
            start = null;
            end = null;
        }
        //deadline task
        else if (listOfDate.size() == 1){ 	
        	// date and time were specified 
        	if (listOfDate.get(0).getSeconds() == 0){
        		start = listOfDate.get(0);
        		end = null;
        	}
        	// only date was specified; default time will be set to 23:59
        	else{  		
        		Date newDate = listOfDate.get(0);
        		newDate.setHours(23);
        		newDate.setMinutes(59);
        		newDate.setSeconds(0);
        		start = newDate;
        		end = null;
        	}
        }
        //event task
        else if (listOfDate.size() == 2){
            if (listOfDate.get(0).before(listOfDate.get(1))) { // check that start date is before end date
                start = listOfDate.get(0);
                end = listOfDate.get(1);
            }
            else {
                throw new IllegalValueException(MESSAGE_DATETIME_CONSTRAINTS);
            }
        }
        //wrong number of date elements -> wrong inputs
        else {
            throw new IllegalValueException(MESSAGE_DATETIME_CONSTRAINTS);
        }
        
        System.out.println(toString());
    }

    /**
     * Initialises Natty with the current time zone.
     * @return Natty's date parser
     */
    private Parser initNatty() {
        TimeZone tz = TimeZone.getDefault();
        Parser natty = new Parser(tz);
        return natty;
    }

    /**
     * Returns if a given string is a valid task date.
     */
    /*    public static boolean isValidDate(String test) {
        return test.equals("") || test.matches(DATE_VALIDATION_REGEX);
    }*/

    @Override
    public String toString() {
        if (end != null){ //event
            return processDate(start) + " to " + processDate(end);
        }
        else if (start != null){ //deadline
            return processDate(start);
        }
        else { //floating task
            return ""; 
        }
    }

    /*
     * Returns DD-MMM-YYYY HH:MM
     */
    private String processDate(Date date){
        return getDate(date) + " " + getTime(date);
    }

    /**
     * @param Date date
     * @return String representation of Date in DD-MMM-YYYY
     */
    private String getDate(Date date){
        String[] split = date.toString().split(" ");
        String toReturn = split[2] + "-" + split[1]
                + "-" + split[5];
        return toReturn;
    }

    /**
     * @param date
     * @return String representation Date in HH:MM
     */
    private String getTime(Date date){
        String[] split = date.toString().split(" ");
        return split[3].substring(0, 5);
    }

    /**
     * 
     * @return event: DD-MMM-YYYY to DD-MMM-YYYY
     *      || deadline: DD-MMM-YYYY 
     *      || floating: ""
     */
    public String getDateString() {
        if (end != null){ //event
            //Starts and end on same day
            if (getDate(start).equals(getDate(end))){
                return getDate(start);
            }
            else {
                return getDate(start) + " to " + getDate(end);
            }
        }
        else if (start != null){ //deadline
            return getDate(start);
        }
        else { //floating task
            return ""; 
        }
    }

    /**
     * 
     * @return event: HH:MM to HH:MM
     *      || deadline: HH:MM 
     *      || floating: ""
     */
    public String getTimeString() {
        if (end != null){ //event
            return getTime(start) + " to " + getTime(end);
        }
        else if (start != null){ //deadline
            return getTime(start);
        }
        else { //floating task
            return ""; 
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Datetime // instanceof handles nulls
                        && this.toString().equals(((Datetime) other).toString()));											
    }

    @Override
    public int hashCode() {
        if (end != null){ //event
            return Objects.hash(start, end);
        }
        else if (start != null){ //deadline
            return Objects.hash(start);
        }
        else { //floating task
            return Objects.hash(""); 
        }
    }

    public Date getStart() {
        return start;
    }

    public Date getEnd() {
        return end;
    }


}
