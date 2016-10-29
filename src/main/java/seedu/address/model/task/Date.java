package seedu.address.model.task;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

import com.joestelmach.natty.DateGroup;

//@@author A0146123R
/**
 * Represents a Task's date
 * It can be deadline for tasks or event date for events.
 */
public interface Date{
    
    public static final String DATE_VALIDATION_REGEX = "^[0-3]?[0-9].[0-1]?[0-9].([0-9]{4})(-[0-2]?[0-9]?)?";
    // EXAMPLE = "15.10.2016-14"
    public static final String DATE_CORRECTION_REGEX = "^[0-9].[0-9].([0-9]{4})(-[0-2]?[0-9]?)?";
    // EXAMPLE = "5.10.2016-14"
    
    public static final DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");

    String getValue();
    
    String toString();
  

    int hashCode();
    
    default boolean isEmptyDate() {
        return getValue().equals("");
    }
    
    /**
     * Returns true if a given string need correction.
     */
    static boolean needCorrection(String test) {
        return test.matches(DATE_CORRECTION_REGEX);
    }
    
    /**
     * Add leading zero to make sure the date strictly follow DD.MM.YYYY
     */
    static String addLeadingZero(String test) {
        String[] parts = test.split("\\.");
        assert parts.length == 3;
        if (parts[0].length() < 2) {
            parts[0] = "0" + parts[0];
        }
        if (parts[1].length() < 2) {
            parts[1] = "0" + parts[1];
        }
        String newDate = parts[0] + "." + parts[1] + "." + parts[2];
        return newDate;
    }
    
    //@@author A0142325R
    static String parseDate(String date) {
        DateGroup dateGroup = new com.joestelmach.natty.Parser().parse(date).get(0);
        List<java.util.Date> parsedDate = dateGroup.getDates();
        if (dateGroup.isTimeInferred()) {
            return dateFormat.format(parsedDate .get(0)).toString();
        } else {
            return dateFormat.format(parsedDate.get(0)).toString() + "-" + parsedDate.get(0).toString().substring(11, 13);
        }
    }
    
}