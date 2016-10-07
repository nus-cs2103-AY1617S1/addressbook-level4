package seedu.address.logic.commands;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.lang.Exception;
import org.ocpsoft.prettytime.nlp.PrettyTimeParser;
//import org.ocpsoft.prettytime.PrettyTime;

import seedu.address.model.task.Recurrence;

public class CommandHelper {

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
    //TODO specify exception
    public static Date convertStringToDate(String dateInString) throws Exception{
        List<Date> dates = new PrettyTimeParser().parse(dateInString);
        if(dates.size() != 1){
            throw new Exception();
        }
        return dates.get(0);
    }

    public static Recurrence getRecurrence(String repeatParameter){
        Recurrence recurrence = new Recurrence();

        return recurrence;
    }

}
