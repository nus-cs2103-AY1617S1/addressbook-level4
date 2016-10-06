package seedu.address.logic.commands;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.ocpsoft.prettytime.nlp.PrettyTimeParser;
//import org.ocpsoft.prettytime.PrettyTime;

import seedu.address.model.task.Recurrence;

public class CommandHelper {

    public static List<Date> convertStringToMultipleDates(String dateInString){
        List<Date> dates = new PrettyTimeParser().parse(dateInString);
        return dates;
    }

    public static Recurrence getRecurrence(String repeatParameter){
        Recurrence recurrence = new Recurrence();

        return recurrence;
    }

}
