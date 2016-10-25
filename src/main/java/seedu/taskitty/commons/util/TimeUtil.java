package seedu.taskitty.commons.util;

import java.time.DateTimeException;
import java.time.LocalDateTime;
import java.time.LocalTime;

//@@author A0139930B-unused
//Code was written before switching over to using Natty
/**
 * Converts a String to Date and vice versa.
 */
public class TimeUtil {
    private static final int TIME_COMPONENT_HOUR = 0;
    private static final int TIME_COMPONENT_MINUTE = 1;
    
    /**
     * Returns a LocalDate object representing the date from the string
     * 
     * @param dateString cannot be null
     * @throws DateTimeException if Date is invalid
     */
    public static LocalTime parseTime(String dateString) throws DateTimeException {
        assert dateString != null;
        
        LocalTime localTime;
        
        //TODO make clean (same as DateUtil)
        if (StringUtil.isInteger(dateString)) {
            localTime = parseFormat2(dateString);
        } else {
            String[] dateSplitByColon = dateString.split(":");
            localTime = parseFormat1(dateSplitByColon);
        }
        
        return localTime;
    }
    
    //TODO how to make clean?
    private static LocalTime parseFormat1(String[] timeComponents) throws DateTimeException {
        int hour = Integer.parseInt(timeComponents[TIME_COMPONENT_HOUR]);
        int minute = Integer.parseInt(timeComponents[TIME_COMPONENT_MINUTE]);
        
        return LocalTime.of(hour, minute);
    }
    
    private static LocalTime parseFormat2(String dateString) throws DateTimeException {
        assert dateString.length() == 4; //is it necessary to defend here?
        
        int hour = Integer.parseInt(dateString.substring(0, 2));
        int minute = Integer.parseInt(dateString.substring(2));
        
        return LocalTime.of(hour, minute);
    }
    
    //@@author A0130853L
    /**
     * creates a current Time object for comparison with current time.
     * @return
     */
    public static LocalDateTime createCurrentTime() {
		return LocalDateTime.now();
	}
}
