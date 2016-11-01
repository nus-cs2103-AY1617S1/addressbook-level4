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
    private static final SimpleDateFormat CONVERT_NATTY_TIME_FORMAT =
            new SimpleDateFormat("dd/MM/yyyy HHmm");
    private static final String DASH_DATE_FORMAT = "(\\b\\d{1,2})-(\\d{1,2})";
    private static final String SLASH_DATE_FORMAT = "(\\b\\d{1,2})/(\\d{1,2})";
    private static final String DASH_DATE_REPLACEMENT = "$2-$1";
    private static final String SLASH_DATE_REPLACEMENT = "$2/$1";

    private static final int EMPTY_GROUP_SIZE = 0;
    private static final int START_DATE_SIZE = 1;
    private static final int START_END_DATE_SIZE = 2;
    private static final int FIRST_GROUP = 0;
    private static final int SECOND_GROUP = 1;
    private static final int FIRST_CHILD = 0;
    private static final int SECOND_CHILD = 1;
    private static final String NATTY_TIME_PREFIX = "EXPLICIT_TIME";

    /**
     * Extracts the new task's dateTime from the string arguments using natty.
     * 
     * @return String[] with first index being the startDate time and second index being the end
     *         date time
     */
    public static String[] parseStringToDateTime(String dateTimeArg) {
        String endDateTime = StringUtil.EMPTY_STRING;
        String startDateTime = StringUtil.EMPTY_STRING;
        String formattedDateTimeArg = convertToUsDateFormat(dateTimeArg);

        Parser parser = new Parser(TimeZone.getDefault());
        List<DateGroup> groups = parser.parse(formattedDateTimeArg);

        if (isInvalidDateTimeArg(dateTimeArg, groups)) {
            throw new DateTimeException(Messages.MESSAGE_INVALID_DATE);
        }

        if (groups.size() > 0) {
            DateGroup group = groups.get(FIRST_GROUP);
            if (group.getDates().size() == START_DATE_SIZE) {
                return extractStartDate(group);
            }

            if (group.getDates().size() == START_END_DATE_SIZE) {
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
                .replaceAll(DASH_DATE_FORMAT, DASH_DATE_REPLACEMENT)
                .replaceAll(SLASH_DATE_FORMAT, SLASH_DATE_REPLACEMENT);
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
    private static boolean isInvalidDateTimeArg(String dateTimeArg,
            List<DateGroup> groups) {
        return (dateTimeArg.trim().length() > StringUtil.EMPTY_STRING_LENGTH
                && groups.size() == EMPTY_GROUP_SIZE);
    }

    /**
     * Extracts start date time from natty group
     * 
     * @@author A0139924W
     */
    private static String[] extractStartDate(DateGroup group) {
        String treeString = StringUtil.EMPTY_STRING;
        String endDateTime = StringUtil.EMPTY_STRING;
        Date date;

        treeString = group.getSyntaxTree().getChild(FIRST_CHILD).toStringTree();
        date = group.getDates().get(FIRST_GROUP);
        if (!isTimePresent(treeString)) {
            date = DateTimeUtil.setDateTime(date, 23, 59, 0);
        }

        endDateTime = convertToAsiaDateFormat(date);

        return new String[] {StringUtil.EMPTY_STRING, endDateTime};
    }

    /**
     * Extracts start and end date time from natty group
     * 
     * @@author A0139924W
     */
    private static String[] extractStartAndEndDate(DateGroup group) {
        String firstTreeString = StringUtil.EMPTY_STRING;
        String secondTreeString = StringUtil.EMPTY_STRING;
        String startDateTime = StringUtil.EMPTY_STRING;
        String endDateTime = StringUtil.EMPTY_STRING;
        Date firstDate;
        Date secondDate;

        firstTreeString =
                group.getSyntaxTree().getChild(FIRST_CHILD).toStringTree();
        secondTreeString =
                group.getSyntaxTree().getChild(SECOND_CHILD).toStringTree();
        firstDate = group.getDates().get(FIRST_GROUP);
        secondDate = group.getDates().get(SECOND_GROUP);

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
