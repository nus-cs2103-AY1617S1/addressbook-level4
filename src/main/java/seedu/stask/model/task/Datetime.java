package seedu.stask.model.task;

import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.joestelmach.natty.Parser;

import seedu.stask.commons.exceptions.IllegalValueException;

//@@author A0143884W
/**
 * Represents a Task's Date and Time in the to-do-list. Guarantees: immutable;
 * 
 */
public class Datetime {

    public static final String MESSAGE_DATETIME_CONSTRAINTS = "Date can be DD-MMM-YYYY and Time can be 24h format";

    public static final String MESSAGE_DATETIME_CONTAINS_DOTS = "Date should be in DD-MM-YYYY format and cannot contain '.' character";

    public static final String MESSAGE_DATE_END_BEFORE_START = "Date should have its start time before its end time.";
    
    public static final String DATE_INCORRECT_REGEX = ".*" + "(0?[1-9]|[12][0-9]|3[01])" + "\\." 
            + "(0?[1-9]|1[012])" + "\\." + "\\d{2}(\\{2}){0,1}" + ".*";
    public static final Pattern DATE_CORRECT_REGEX = Pattern.compile("(?<day>(0?[1-9]|[12][0-9]|3[01]))" + "-" 
            + "(?<month>(0?[1-9]|1[012]))" + "-" + "(?<year>\\d{2}(\\{2}){0,1})" + "(?<time>.*)");

    private Date start;
    private Date end;

    public Datetime(String input) throws IllegalValueException {

        Parser natty = initNatty();
        
        List<Date> listOfDate = validateInput(input, natty);
     
        populateStartEndDates(listOfDate);
    }

    /**
     * Validate input date string using Natty parser
     * @param input String containing date and time 
     * @param natty Parser Object
     * @return List of Date Objects
     * @throws IllegalValueException
     */
	private List<Date> validateInput(String input, Parser natty) throws IllegalValueException {		
        if (input == null) { // 'date/' not found -> floating task
            return null;
        } else if (input.matches(DATE_INCORRECT_REGEX)){ // check input for '.' characters in date
            throw new IllegalValueException(MESSAGE_DATETIME_CONTAINS_DOTS);
        } else if (input.equals("")) { // empty string after "date/" -> convert deadline or event to floating task
            return null;
        } else if (!natty.parse(input).isEmpty()) { // natty returns non-empty list if input is parse-able
            // rearrange DD-MM-YY to parse-able MM-DD-YY 
            final Matcher matcher = DATE_CORRECT_REGEX.matcher(input.trim());
            if (matcher.matches()){
                input = matcher.group("month") + "-" + matcher.group("day") + "-" + matcher.group("year") + matcher.group("time");
            }
            return natty.parse(input).get(0).getDates();
        } else { // natty returns empty list if input is not parse-able
            throw new IllegalValueException(MESSAGE_DATETIME_CONSTRAINTS);
        }
	}
	
	/**
	 * Populate startDate and endDate depending on task type
	 * @param listOfDate List of Date Object after parsing by Natty 
	 * @throws IllegalValueException
	 */
	private void populateStartEndDates(List<Date> listOfDate) throws IllegalValueException {
		if (listOfDate == null) { // if task is floating task
            start = null;
            end = null;
        } else if (listOfDate.size() == 1){ // if task is deadline          
            if (listOfDate.get(0).getSeconds() == 0){ // date and time specified 
                start = listOfDate.get(0);
                end = null;
            } else{ // only date was specified; default time will be set to 23:59	
                Date newDate = setToDefaultTime(listOfDate);
                start = newDate;
                end = null;
            }
        } else if (listOfDate.size() == 2){ // if task is event
            if (listOfDate.get(0).before(listOfDate.get(1))) { // check that start date is before end date
                start = listOfDate.get(0);
                end = listOfDate.get(1);
            } else {
                throw new IllegalValueException(MESSAGE_DATE_END_BEFORE_START);
            }
        } else { //wrong number of date elements -> wrong inputs
            throw new IllegalValueException(MESSAGE_DATETIME_CONSTRAINTS);
        }
	}

	/**
	 * @param listOfDate
	 * @return Date Object with Time set to 2359
	 */
	private Date setToDefaultTime(List<Date> listOfDate) {
		Date newDate = listOfDate.get(0);
		newDate.setHours(23);
		newDate.setMinutes(59);
		newDate.setSeconds(0);
		return newDate;
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
     * 
     * @param date Date Object 
     * @return Returns String in DD-MMM-YYYY HH:MM Date and Time format
     */
    private String processDate(Date date){
        return getDate(date) + " " + getTime(date);
    }

    /**
     * @param date Date Object
     * @return String in DD-MMM-YYYY Date format
     */
    private String getDate(Date date){
        String[] split = date.toString().split(" ");
        String toReturn = split[2] + "-" + split[1]
                + "-" + split[5];
        return toReturn;
    }

    /**
     * @param date Date Object
     * @return String in HH:MM Time format
     */
    private String getTime(Date date){
        String[] split = date.toString().split(" ");
        return split[3].substring(0, 5);
    }

    /**
     * @return String of event date: DD-MMM-YYYY to DD-MMM-YYYY
     *      <br> String of deadline date: DD-MMM-YYYY 
     *      <br> String of floating date: ""
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
     * @return String of event time: HH:MM to HH:MM
     *      <br> String of deadline time: HH:MM 
     *      <br> String of floating time: ""
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
    
    /**
     * @return Date object containing start date of task
     * <br> Only applicable to deadlines and events, else null
     */
    public Date getStart() {
        return start;
    }

    /**
     * @return Date object containing end date of task
     * <br> Only applicable to events, else null
     */
    public Date getEnd() {
        return end;
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
}
