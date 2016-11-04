package seedu.address.model.item;

import java.util.HashMap;
import java.util.Optional;

//@@author A0139655U
/**
 * Represents a RecurrenceRate's time period.  
 * Guarantees: is valid as declared in {@link #convertStringToTimePeriod(String)}
 */
public enum TimePeriod {
    HOUR,
    DAY,
    WEEK,
    MONTH,
    YEAR,
    MONDAY,
    TUESDAY,
    WEDNESDAY,
    THURSDAY,
    FRIDAY,
    SATURDAY,
    SUNDAY;
    
    /** Name of key in map that maps to the time period of recurrence rate */
    private static final String MAP_TIME_PERIOD_KEY = "timePeriod";
    
    /**
     * Map containing valid user inputs and which values these inputs are mapped to.
     */
    public static final HashMap<String, TimePeriod> INPUT_TO_TIME_PERIOD_MAP = new HashMap<String, TimePeriod>() {{
        put("hour", TimePeriod.HOUR);
        put("hours", TimePeriod.HOUR);
        put("day", TimePeriod.DAY);
        put("days", TimePeriod.DAY);
        put("week", TimePeriod.WEEK);
        put("weeks", TimePeriod.WEEK);
        put("month", TimePeriod.MONTH);
        put("months", TimePeriod.MONTH);
        put("year", TimePeriod.YEAR);
        put("years", TimePeriod.YEAR);
        put("mon", TimePeriod.MONDAY);
        put("monday", TimePeriod.MONDAY);
        put("tues", TimePeriod.TUESDAY);
        put("tuesday", TimePeriod.TUESDAY);
        put("wed", TimePeriod.WEDNESDAY);
        put("wednesday", TimePeriod.WEDNESDAY);
        put("thur", TimePeriod.THURSDAY);
        put("thurs", TimePeriod.THURSDAY);
        put("thursday", TimePeriod.THURSDAY);
        put("fri", TimePeriod.FRIDAY);
        put("friday", TimePeriod.FRIDAY);
        put("sat", TimePeriod.SATURDAY);
        put("saturday", TimePeriod.SATURDAY);
        put("sun", TimePeriod.SUNDAY);
        put("sunday", TimePeriod.SUNDAY);
    }};
    
    /**
     * Converts user input into TimePeriod if valid.
     *
     * @param timePeriodString  user input of time period
     * @return  Optional.empty() if user input does not match any time period.
     * Else, returns the corresponding TimePeriod value.
     */
    public static Optional<TimePeriod> convertStringToTimePeriod(String timePeriodString) {
        assert timePeriodString != null;
        
        Optional<TimePeriod> timePeriod = Optional.empty();
        
        for (String key : INPUT_TO_TIME_PERIOD_MAP.keySet()) {
            if (key.equals(timePeriodString.toLowerCase())) {
                timePeriod = Optional.of(INPUT_TO_TIME_PERIOD_MAP.get(key));
            }
        }
        return timePeriod;
    }
    
    /** 
     * @return the key in map that maps to the time period of recurrence rate
     */
    public static String getTimePeriodKey() {
        return MAP_TIME_PERIOD_KEY;
    }
}