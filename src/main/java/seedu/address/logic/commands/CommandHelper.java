package seedu.address.logic.commands;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Logger;
import java.lang.Exception;
import org.ocpsoft.prettytime.nlp.PrettyTimeParser;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.parser.KeywordParser;
//import org.ocpsoft.prettytime.PrettyTime;

import seedu.address.model.task.Recurrence;

public class CommandHelper {

    public static String MESSAGE_REPEAT_PARAMETERS_INVALID = "Invalid repeat parameters";

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

}
