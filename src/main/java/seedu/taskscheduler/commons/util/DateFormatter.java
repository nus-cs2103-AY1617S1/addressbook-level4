package seedu.taskscheduler.commons.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.ocpsoft.prettytime.nlp.PrettyTimeParser;

import seedu.taskscheduler.commons.core.Messages;
import seedu.taskscheduler.commons.exceptions.IllegalValueException;

//@@author A0148145E

/**
 * Utility methods for Date and time formating.
 */

public class DateFormatter {

    private static DateFormat fullTimeFormatter = new SimpleDateFormat("HHmmss");
    
    private static DateFormat dateDisplayFormatter = new SimpleDateFormat("dd-MMM-yyyy, EEE");
    private static DateFormat timeDisplayFormatter = new SimpleDateFormat("hh:mm aa");
    
    public static Date convertStringToDate(String val) throws IllegalValueException {
        try {
            return new PrettyTimeParser().parse(val).get(0);
        } catch (IndexOutOfBoundsException iobe) {
            throw new IllegalValueException(String.format(Messages.MESSAGE_INVALID_DATE_FORMAT,val.trim()));
        }
    }
    
    public static String convertDateToDisplayString(Date val) {
        return dateDisplayFormatter.format(val);
    }
    
    public static String convertTimeToDisplayString(Date val) {
        return timeDisplayFormatter.format(val);
    }

    public static String convertDateToFullTimeString(Date val) {
        return fullTimeFormatter.format(val);
    }
}
