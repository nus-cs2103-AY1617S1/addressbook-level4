package seedu.address.logic.commands;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.lang.Exception;
import org.ocpsoft.prettytime.nlp.PrettyTimeParser;
//import org.ocpsoft.prettytime.PrettyTime;

import seedu.address.model.task.Recurrence;

public class CommandHelper {

    //TODO what if no valid dates found by parser
    public static List<Date> convertStringToMultipleDates(String dateInString){
        List<Date> dates = new PrettyTimeParser().parse(dateInString);
        return dates;
    }

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
