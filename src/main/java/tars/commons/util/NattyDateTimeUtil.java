package tars.commons.util;

import java.text.SimpleDateFormat;
import java.time.DateTimeException;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import com.joestelmach.natty.DateGroup;
import com.joestelmach.natty.Parser;

import tars.commons.core.Messages;

/**
 * Natty date time utility
 * 
 * @@author A0139924W
 */
public class NattyDateTimeUtil {
    private static final SimpleDateFormat CONVERT_NATTY_TIME_FORMAT = new SimpleDateFormat("dd/MM/yyyy HHmm");
    private static final String NATTY_TIME_PREFIX = "EXPLICIT_TIME";
    
    /**
     * Extracts the new task's dateTime from the string arguments using natty.
     * 
     * @return String[] with first index being the startDate time and second index being the end
     *         date time
     */
    public static String[] parseStringToDateTime(String dateTimeArg) {
        String endDateTime = "";
        String startDateTime = "";
        String formattedDateTimeArg = convertToUsDateFormat(dateTimeArg);

        Parser parser = new Parser(TimeZone.getDefault());
        List<DateGroup> groups = parser.parse(formattedDateTimeArg);

        if (isInvalidDateTimeArg(dateTimeArg, groups)) {
            throw new DateTimeException(Messages.MESSAGE_INVALID_DATE);
        }

        if (groups.size() > 0) {
            DateGroup group = groups.get(0);
            if (group.getDates().size() == 1 && group.getSyntaxTree().getChildCount() == 1) {
                return extractStartDate(group);
            }

            if (group.getDates().size() == 2 && group.getSyntaxTree().getChildCount() == 2) {
                return extractStartAndEndDate(group);
            }
        }

        return new String[] {startDateTime, endDateTime};
    }
    
    /**
     * Change the date format to US date format.
     * 
     * @@author A0139924W
     * @return formatted datetime in US format
     */
    private static String convertToUsDateFormat(String rawDateTime) {
        String formattedDateTime = rawDateTime.trim()
                                              .replaceAll("(\\b\\d{1,2})/(\\d{1,2})", "$2/$1")
                                              .replaceAll("(\\b\\d{1,2})-(\\d{1,2})", "$2-$1");
        return formattedDateTime;
    }
    
    /**
     * Change the date format to Asia date format.
     * 
     * @@author A0139924W
     * @return formatted datetime in Asia format
     */
    private static String convertToAsiaDateFormat(Date toBeFormattedDateTime) {
        return CONVERT_NATTY_TIME_FORMAT.format(toBeFormattedDateTime);
    }
    
    /**
     * Checks if the datetime is a invalid format.
     * 
     * @@author A0139924W
     * @return true if the given datetime is invalid
     */
    private static boolean isInvalidDateTimeArg(String dateTimeArg, List<DateGroup> groups) {
        return (dateTimeArg.trim().length() > 0 && groups.size() == 0);
    }
    
    /**
     * Extracts start date time from natty group
     * 
     * @@author A0139924W
     */
    private static String[] extractStartDate(DateGroup group) {
        String treeString = "";
        String endDateTime = "";
        Date date;

        treeString = group.getSyntaxTree().getChild(0).toStringTree();
        date = group.getDates().get(0);
        if (!isTimePresent(treeString)) {
            date = DateTimeUtil.setDateTime(date, 23, 59, 0);
        }

        endDateTime = convertToAsiaDateFormat(date);
        
        return new String[] {"", endDateTime};
    }

    /**
     * Extracts start and end date time from natty group
     * 
     * @@author A0139924W
     */
    private static String[] extractStartAndEndDate(DateGroup group) {
        String firstTreeString = "";
        String secondTreeString = "";
        String startDateTime = "";
        String endDateTime = "";
        Date firstDate;
        Date secondDate;

        firstTreeString = group.getSyntaxTree().getChild(0).toStringTree();
        secondTreeString = group.getSyntaxTree().getChild(1).toStringTree();
        firstDate = group.getDates().get(0);
        secondDate = group.getDates().get(1);

        if (!isTimePresent(firstTreeString)) {
            firstDate = DateTimeUtil.setDateTime(firstDate, 0, 0, 0);
        }

        if (!isTimePresent(secondTreeString)) {
            secondDate = DateTimeUtil.setDateTime(secondDate, 23, 59, 0);
        }

        startDateTime = CONVERT_NATTY_TIME_FORMAT.format(firstDate);
        endDateTime = CONVERT_NATTY_TIME_FORMAT.format(secondDate);

        return new String[] {startDateTime, endDateTime};
    }
    
    /**
     * Checks if time is present
     * 
     * @@author A0139924W
     */
    private static boolean isTimePresent(String treeString) {
        return treeString.contains(NATTY_TIME_PREFIX);
    }
}
