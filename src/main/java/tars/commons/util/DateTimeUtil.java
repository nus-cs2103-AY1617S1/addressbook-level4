package tars.commons.util;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import com.joestelmach.natty.DateGroup;
import com.joestelmach.natty.Parser;

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
        
        System.out.println(dateArgs.trim());
        System.out.println("after: " + dateArgs.trim()
                .replaceAll("(^[a-zA-Z_ ]*\\d{1,2})/(\\d{1,2})", "$2/$1")
                .replaceAll("(^[a-zA-Z_ ]*\\d{1,2})-(\\d{1,2})", "$2-$1"));
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
}
