package seedu.taskscheduler.commons.util;

import java.text.DateFormat;
import java.text.ParseException;
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

    private static DateFormat dateFormatter = new SimpleDateFormat("ddMMyy");
    private static DateFormat timeFormatter = new SimpleDateFormat("HHmm");
    private static DateFormat fullTimeFormatter = new SimpleDateFormat("HHmmss");
    
    private static DateFormat dateDisplayFormatter = new SimpleDateFormat("dd-MMM-yyyy");
    private static DateFormat timeDisplayFormatter = new SimpleDateFormat("hh:mm aa");
    
    public static Date convertStringToDate(String val) throws IllegalValueException {
        try {
            return new PrettyTimeParser().parse(val).get(0);
        } catch (IndexOutOfBoundsException iobe) {
            throw new IllegalValueException(String.format(Messages.MESSAGE_INVALID_DATE_FORMAT,val));
        }
//        try {
//            return dateFormatter.parse(val);
//        } catch (ParseException e) {
//            throw new IllegalValueException(Messages.MESSAGE_INVALID_DATE_FORMAT);
//        }
    }

    public static Date convertStringToTime(String val) throws IllegalValueException {
        try {
            return timeFormatter.parse(val);
        } catch (ParseException e) {
            throw new IllegalValueException(Messages.MESSAGE_INVALID_TIME_FORMAT);
        }
    }

    public static boolean isValidDateString(String val) {
        try {
            dateFormatter.parse(val);
        } catch (ParseException e) {
            return false;
        }
        return true;
    } 
    
    public static boolean isValidTimeString(String val) {
        try {
            timeFormatter.parse(val);
        } catch (ParseException e) {
            return false;
        }
        return true;
    }
//    
//    public static String convertDateToString(Date val) {
//        return dateFormatter.format(val);
//    }
//    
//    public static String convertTimeToString(Date val) {
//        return timeFormatter.format(val);
//    }
    
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
