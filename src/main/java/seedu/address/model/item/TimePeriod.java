package seedu.address.model.item;

import java.util.HashMap;
import java.util.Optional;

//@@author A0139655U
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
     * Validates user input and converts it into TimePeriod.
     *
     * @return true if user input is recognised as a valid TimePeriod.
     */
    public static Optional<TimePeriod> validateTimePeriodInput(String timePeriodString) {
        assert timePeriodString != null;
        
        Optional<TimePeriod> timePeriod = Optional.empty();
        
        for (String key : INPUT_TO_TIME_PERIOD_MAP.keySet()) {
            if (key.equals(timePeriodString.toLowerCase())) {
                timePeriod = Optional.of(INPUT_TO_TIME_PERIOD_MAP.get(key));
            }
        }
        return timePeriod;
    }
}