package teamfour.tasc.logic.commands;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Logger;
import java.lang.Exception;
import org.ocpsoft.prettytime.nlp.PrettyTimeParser;

import teamfour.tasc.commons.core.LogsCenter;
import teamfour.tasc.commons.exceptions.IllegalValueException;
import teamfour.tasc.logic.parser.KeywordParser;
import teamfour.tasc.model.task.Recurrence;

public class CommandHelper {
    private static final Logger logger = LogsCenter.getLogger(CommandHelper.class);

    public static String MESSAGE_REPEAT_PARAMETERS_INVALID = "Invalid repeat parameters";
    
    /**
     * Converts a String to Date if possible, otherwise returns null.
     * @param dateString the String containing the Date
     * @return the Date from String, or null if not possible
     */
    public static Date convertStringToDateIfPossible(String dateString) {
        if (dateString != null) {
            try {
                return CommandHelper.convertStringToDate(dateString);
            } catch (IllegalValueException e) {
                logger.warning("Invalid date string in method " + 
                            "convertStringToDateIfPossible: " + dateString);
                return null;
            }
        }
        return null;
    }
    
    /**
     * Precondition: date argument is not null.
     * Returns the Date set to the start of the date.
     * @param date the Date object
     * @return a Date object set to start of the date
     */
    public static Date getStartOfTheDate(Date date) {
        assert date != null;
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);
        return c.getTime();
    }
    
    /**
     * Precondition: date argument is not null.
     * Returns the Date set to the end of the date.
     * @param date the Date object
     * @return a Date object set to end of the date
     */
    public static Date getEndOfTheDate(Date date) {
        assert date != null;
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.set(Calendar.HOUR_OF_DAY, 23);
        c.set(Calendar.MINUTE, 59);
        c.set(Calendar.SECOND, 59);
        c.set(Calendar.MILLISECOND, 999);
        return c.getTime();
    }

    /**
     * Parses date(s) from an input String containing the dates
     * Dates parsed include time of day, day of week, day of month, month of year, and year
     * Time of day can be in numerical 12 hours or 24 hours or word form (7pm or 1900 hours or seven in the evening)
     * Day of week can be in short or full (fri or friday)
     * Day of month can be in numerical or english word form (13 or thirteenth)
     * Month of year can only be in word form, and can be in short(january or jan, but not 1)
     * Year must be in numerical form, and put after month and day
     * @param dateInString
     * @return List of dates parsed from dateInString
     */
    public static List<Date> convertStringToMultipleDates(String dateInString){
        if(dateInString.toLowerCase().contains("today")){

        }
        List<Date> dates = new PrettyTimeParser().parse(dateInString);
        return dates;
    }

    /**
     * Like convertStringToMultipleDates(String dateInString), but only returns one Date
     * Throws exception if multiple dates can be parsed from one dateInString
     * @param dateInString
     * @return Date parsed from dateInString
     * @throws Exception
     */
    public static Date convertStringToDate(String dateInString) throws IllegalValueException{
        //special case if date is "today", but no valid time given
        if(dateInString.toLowerCase().contains("today")){
            List<Date> dates = new PrettyTimeParser().parse(dateInString);
            if(dates.size() != 1){
                throw new IllegalValueException("Multiple dates found");
            }
            if( (new Date().getDate() == dates.get(0).getDate() )&& ((new Date().after(dates.get(0))) || (new Date().equals(dates.get(0))) ) ){
                dateInString = "today 11.59pm";
            }
        }
        //normal case
        List<Date> dates = new PrettyTimeParser().parse(dateInString);
        if(dates.size() != 1){
            throw new IllegalValueException("Multiple dates found");
        }
        return dates.get(0);
    }

    /**
     * Input parameter includes the pattern of recurrence, and frequency of recurrence
     * @param repeatParameter
     * @return Recurrence object from repeatParameter
     * @throws IllegalValueException
     */
    public static Recurrence getRecurrence(String repeatParameter) throws IllegalValueException{
        String parseThis = repeatParameter.toLowerCase();
        KeywordParser kp = new KeywordParser("daily","weekly","monthly","yearly");
        HashMap<String, String> parameters = kp.parseForOneKeyword(parseThis);
        if(parameters.containsKey("daily")){
            try {
                Recurrence recurrence = new Recurrence(Recurrence.Pattern.DAILY, Integer.parseInt(parameters.get("daily")));
                return recurrence;
            } catch (NumberFormatException | IllegalValueException e) {
                throw new IllegalValueException(MESSAGE_REPEAT_PARAMETERS_INVALID);
            }
        }
        else if(parameters.containsKey("weekly")){
            try {
                Recurrence recurrence = new Recurrence(Recurrence.Pattern.WEEKLY, Integer.parseInt(parameters.get("weekly")));
                return recurrence;
            } catch (NumberFormatException | IllegalValueException e) {
                throw new IllegalValueException(MESSAGE_REPEAT_PARAMETERS_INVALID);
            }
        }
        else if(parameters.containsKey("monthly")){
            try {
                Recurrence recurrence = new Recurrence(Recurrence.Pattern.MONTHLY, Integer.parseInt(parameters.get("monthly")));
                return recurrence;
            } catch (NumberFormatException | IllegalValueException e) {
                throw new IllegalValueException(MESSAGE_REPEAT_PARAMETERS_INVALID);
            }
        }
        else if(parameters.containsKey("yearly")){
            try {
                Recurrence recurrence = new Recurrence(Recurrence.Pattern.YEARLY, Integer.parseInt(parameters.get("yearly")));
                return recurrence;
            } catch (NumberFormatException | IllegalValueException e) {
                throw new IllegalValueException(MESSAGE_REPEAT_PARAMETERS_INVALID);
            }
        }
        else{
            return new Recurrence();
        }

    }
    
    /**
     * Convert the date object into a string that the pretty time parser
     * can actually understand.
     * 
     * @param date to convert to
     * @return string that can be parsed by pretty time library
     */
    public static String convertDateToPrettyTimeParserFriendlyString(Date date) {
        return String.format("%tb %td %tY %tT", date, date, date, date);
    }
}
