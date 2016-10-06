package seedu.address.commons.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import seedu.address.commons.exceptions.IllegalValueException;

public class DateFormatter {
    
    private static DateFormat dateFormatter = new SimpleDateFormat("ddMMyyyy");
    private static DateFormat timeFormatter = new SimpleDateFormat("hhmm");
    
    public static Date convertStringToDate(String val) throws IllegalValueException {
        try {
            return dateFormatter.parse(val);
        } catch (ParseException e) {
            throw new IllegalValueException("Incorrect date format");
        }
    }

    public static Date convertStringToTime(String val) throws IllegalValueException {
        try {
            return timeFormatter.parse(val);
        } catch (ParseException e) {
            throw new IllegalValueException("Incorrect time format");
        }
    }
    
    public static String convertDateToString(Date val) {
        return dateFormatter.format(val);
    }
    
    public static String convertTimeToString(Date val) {
        return timeFormatter.format(val);
    }
}
