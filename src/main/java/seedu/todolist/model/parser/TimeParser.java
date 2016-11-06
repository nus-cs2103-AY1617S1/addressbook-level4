package seedu.todolist.model.parser;

import java.time.DateTimeException;
import java.time.LocalTime;

//@@author A0138601M
/**
 * Converts a String to Time and vice versa.
 */
public class TimeParser {
    
    private static final int TIME_COMPONENT_TOTAL = 2;
    
    private static final int TIME_COMPONENT_INDEX_MINUTE = 1;
    private static final int TIME_COMPONENT_INDEX_HOUR = 0;
    
    //Position of the start of hour in a string
    private static final int TIME_COMPONENT_HOUR_START_INDEX = 0;
    
    //am and pm are 2 characters long
    private static final int TIME_COMPONENT_PERIOD_LENGTH = 2;
    
    private static final int TIME_COMPONENT_MINUTE_DEFAULT = 0;
    
    private static final int TIME_COMPONENT_PERIOD_OFFSET = 12;
    
    /**
     * Parses string time input into LocalTime time.
     *
     * @param string time input
     * @return LocalTime time based on the string time input
     */
    public static LocalTime parseTime(String time) throws DateTimeException {
        assert time != null;
        LocalTime parsedTime;

        //trim all spaces away
        time = time.replaceAll("\\s+", "");
        
        if (time.toUpperCase().contains("AM")) {
            parsedTime = parseTimeWithAMFormat(time);
        }
        else if (time.toUpperCase().contains("PM")) {
            parsedTime = parseTimeWithPMFormat(time);
        }
        else {
            parsedTime = parseTimeWithContinentalFormat(time);
        }
        
        return parsedTime;
    }
    
    /**
     * Parses string time input with AM format into LocalTime time.
     *
     * @param string time input with AM format
     * @return LocalTime time based on the string time input
     */
    private static LocalTime parseTimeWithAMFormat(String time) throws DateTimeException {
        time = removePeriod(time);
        String[] timeComponents = time.split(":");
        
        int hour, minute;
        if (timeComponents.length < TIME_COMPONENT_TOTAL) {
            hour = Integer.parseInt(time);
            minute = TIME_COMPONENT_MINUTE_DEFAULT;
        }
        else {
            hour = Integer.parseInt(timeComponents[TIME_COMPONENT_INDEX_HOUR]);
            minute = Integer.parseInt(timeComponents[TIME_COMPONENT_INDEX_MINUTE]);  
        }
           
        if (hour == TIME_COMPONENT_PERIOD_OFFSET) {
            hour = hour - TIME_COMPONENT_PERIOD_OFFSET;
        }
        
        return LocalTime.of(hour, minute);
    }
    
    /**
     * Parses string time input with PM format into LocalTime time.
     *
     * @param string time input with PM format
     * @return LocalTime time based on the string time input
     */
    private static LocalTime parseTimeWithPMFormat(String time) throws DateTimeException {
        time = removePeriod(time);
        String[] timeComponents = time.split(":");
        
        int hour, minute;
        if (timeComponents.length < TIME_COMPONENT_TOTAL) {
            hour = Integer.parseInt(time);
            minute = TIME_COMPONENT_MINUTE_DEFAULT;
        }
        else {
            hour = Integer.parseInt(timeComponents[TIME_COMPONENT_INDEX_HOUR]);
            minute = Integer.parseInt(timeComponents[TIME_COMPONENT_INDEX_MINUTE]);  
        }
        
        if (hour != TIME_COMPONENT_PERIOD_OFFSET) {
            hour = hour + TIME_COMPONENT_PERIOD_OFFSET;
        }
        return LocalTime.of(hour, minute);
    }
    
    private static String removePeriod(String time) {
        return time.substring(TIME_COMPONENT_HOUR_START_INDEX, time.length() - TIME_COMPONENT_PERIOD_LENGTH);
    }
    
    /**
     * Parses string time input with continental (24hr) format into LocalTime time.
     *
     * @param string time input with 24hr format
     * @return LocalTime time based on the string time input
     */
    private static LocalTime parseTimeWithContinentalFormat(String time) throws DateTimeException {
        String[] timeComponents = time.split(":");

        int hour, minute;
        hour = Integer.parseInt(timeComponents[TIME_COMPONENT_INDEX_HOUR]);
        minute = Integer.parseInt(timeComponents[TIME_COMPONENT_INDEX_MINUTE]); 
        
        return LocalTime.of(hour, minute);
    }
    
}
