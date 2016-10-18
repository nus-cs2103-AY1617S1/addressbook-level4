package tars.commons.util;

import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import com.joestelmach.natty.DateGroup;
import com.joestelmach.natty.Parser;

import tars.model.task.DateTime;

/**
 * Date Time Utility package
 * 
 * @@author A0139924W
 */
public class DateTimeUtil {
    private static final SimpleDateFormat CONVERT_NATTY_TIME_FORMAT = new SimpleDateFormat("dd/MM/yyyy HHmm");
    
    /**
     * Extracts the new task's dateTime from the string arguments using natty.
     * 
     * @return String[] with first index being the startDate time and second index being the end
     *         date time
     */
    public static String[] getDateTimeFromArgs(String dateArgs) {
        List<Date> dateList = new ArrayList<Date>();
        Parser parser = new Parser(TimeZone.getDefault());
        
        // swap the date format as natty read dates in US format
        List<DateGroup> groups = parser.parse(dateArgs
                .trim()
                .replaceAll("(\\b\\d{1,2})/(\\d{1,2})", "$2/$1")
                .replaceAll("(\\b\\d{1,2})-(\\d{1,2})", "$2-$1")
        );
        
        for (DateGroup group : groups) {
            dateList.addAll(group.getDates());
        }
        
        // invalid date format, will pass the datetime to handle it
        if (dateArgs.trim().length() > 0 && dateList.size() == 0) {
            return new String[] { "", dateArgs };
        }
        
        if(dateList.size() == 1) {
            String endDateTime = CONVERT_NATTY_TIME_FORMAT.format(dateList.get(0));
            return new String[] { "", endDateTime };
        } else if(dateList.size() == 2) {
            String startDateTime = CONVERT_NATTY_TIME_FORMAT.format(dateList.get(0));
            String endDateTime = CONVERT_NATTY_TIME_FORMAT.format(dateList.get(1));
            return new String[] { startDateTime, endDateTime };
        }
        
        return new String[] { "", "" };
    }
    
    /**
     * Checks if given endDateTime is within the start and end of this week
     * @@author A0121533W
     */
    public static boolean isWithinWeek(LocalDateTime endDateTime) {
        if (endDateTime == null) {
            return false;
        } else {
            LocalDateTime today = LocalDateTime.now();
            LocalDateTime startThisWeek = today.with(TemporalAdjusters.previous(DayOfWeek.SUNDAY));
            LocalDateTime endThisWeek = today.with(TemporalAdjusters.next(DayOfWeek.SUNDAY));
            return endDateTime.isAfter(startThisWeek) && endDateTime.isBefore(endThisWeek);
        }
    }

    /**
     * Checks if given endDateTime is before the end of today
     * @@author A0121533W
     */
    public static boolean isOverDue(LocalDateTime endDateTime) {
        if (endDateTime == null) {
            return false;
        } else {
            LocalDateTime now = LocalDateTime.now();
            return endDateTime.isBefore(now);
        }
    }
    
    /**
     * Checks whether the dateTimeQuery falls within the range of the
     * dateTimeSource
     * 
     * @@author A0124333U
     * @param dateTimeSource
     * @param dateTimeQuery
     */
    public static boolean isDateTimeWithinRange(DateTime dateTimeSource, DateTime dateTimeQuery) {
        boolean isTaskDateWithinRange = true;

        // Return false if task is a floating task (i.e. no start or end
        // dateTime
        if (dateTimeSource.getEndDate() == null) {
            return false;
        }

        // Case 1: dateTimeQuery has a range of date (i.e. startDateTime &
        // endDateTime != null)
        if (dateTimeQuery.getStartDate() != null) {

            if (dateTimeSource.getEndDate().isBefore(dateTimeQuery.getStartDate())) {
                return false;
            }
            
            // Case 1a: dateTimeSource has a range of date 
            if (dateTimeSource.getStartDate() != null) {
                if (dateTimeSource.getStartDate().isAfter(dateTimeQuery.getEndDate())) {
                    return false;
                }
            } else {  //Case 1b: dateTimeSource only has a endDateTime
                if (dateTimeSource.getEndDate().isAfter(dateTimeQuery.getEndDate())) {
                    return false;
                }
            }
        } else { // Case 2: dateTimeQuery only has a endDateTime

            // Case 2a: dateTimeSource has a range of date  
            if (dateTimeSource.getStartDate() != null) {
                if (dateTimeQuery.getEndDate().isBefore(dateTimeSource.getStartDate())
                        || dateTimeQuery.getEndDate().isAfter(dateTimeSource.getEndDate())) {
                    return false;
                }
            } else { //Case 2b: dateTimeSource only has a endDateTime
                if (!dateTimeQuery.getEndDate().equals(dateTimeSource.getEndDate())) {
                    return false;
                }
            }
        }

        return isTaskDateWithinRange;
    }
}
