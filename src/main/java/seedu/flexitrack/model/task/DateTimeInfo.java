package seedu.flexitrack.model.task;


import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.joestelmach.natty.DateGroup;
import com.joestelmach.natty.Parser;

import seedu.flexitrack.commons.exceptions.IllegalValueException;


/**
 * Represents a DateTimeInfo class in FlexiTrack
 */
public class DateTimeInfo {
    public static final String MESSAGE_DATETIMEINFO_CONSTRAINTS = "Invalid time inputed. Please check your spelling!";
    public static final boolean DUE_DATE_OR_START_TIME = true; 
    public static final boolean END_TIME = false; 
    
    private static final Pattern TIME_TYPE_DATA_ARGS_FORMAT = // '/' forward slashes are reserved for delimiter prefixes
            Pattern.compile("(?<info>.+)");

    private DateGroup setTime;
    private String timingInfo; 

    public DateTimeInfo(String givenTime, boolean isStartTimeOrDueDate)throws IllegalValueException {
        setDateGroupTime(givenTime);
        setTimingInfo(isStartTimeOrDueDate);
    }

    /**
     * Set the setTime (DateGroup object) as the date inputed by the user
     * @param givenTime
     */
    public void setDateGroupTime(String givenTime) throws IllegalValueException {
        assert givenTime != null;
        final Matcher matcher = TIME_TYPE_DATA_ARGS_FORMAT.matcher(givenTime.trim());
        matcher.matches();
        DateTimeInfoParser parsedTiming = new DateTimeInfoParser( matcher.group("info"));
        this.setTime = parsedTiming.getParsedTimingInfo();
    }

    /**
     * Set the timingInfo string as the format of the date that the user will see
     * @param isStartTimeOrDueDate
     */
    private void setTimingInfo(boolean isStartTimeOrDueDate) {
        timingInfo = getDateMonthYear();
        if (setTime.isTimeInferred()){
            setInferredTiming(isStartTimeOrDueDate);
        }else { 
            timingInfo = setTime.getDates().toString().substring(12, 17);
        }
    }

    /**
     * @return
     */
    private String getDateMonthYear() {
        return setTime.getDates().toString().substring(5, 12) + setTime.getDates().toString().substring(12, 17);
    }

    /**
     * Set the timing to be the default timing if timing is Inferred 
     * @param isStartTimeOrDueDate
     */
    private void setInferredTiming(boolean isStartTimeOrDueDate) {
        if (isStartTimeOrDueDate) {
            setTimeAsMorningDefault();
        } else { 
            setTimeAsEveningDefault();
        }
    }

    /**
     * Setting the timingInfo as default timing (8am) 
     */
    private void setTimeAsMorningDefault() {
        timingInfo = timingInfo.substring(5, 12);
        timingInfo = timingInfo + "08:00";
    }

    /**
     * Setting the timingInfo as default timing (8am) 
     */
    private void setTimeAsEveningDefault() {
        timingInfo = timingInfo.substring(5, 12);
        timingInfo = timingInfo + "17:00";
    }

    /** 
     * @param timingInfo
     * @return true when the timing is specified
     */
    public boolean isTimeSpecified(String timingInfo){ 
        Parser parser = new Parser(); 
        List<DateGroup> dateParser = parser.parse("now");
        String timingInfoNow = dateParser.get(0).getDates().toString();
        if (timingInfo.substring(12, 20).equals(timingInfoNow.substring(12, 20))){
            return false; 
        } else {
            return true;
        }
    }

    /** 
     * Validate the timing inputed 
     * @param test
     * @return true if it is a valid timing
     */
    public static boolean isValidDateTimeInfo(List<DateGroup> test) {
        if (!test.isEmpty()) {
            return true;
        } else {
            return false;
        }
    }

    /** 
     * Change the months which is specified in string to integer
     * @param month
     * @return month in integer
     */
    public static int whatMonth(String month){ 
        switch (month){
        case "Jan": 
            return 1; 
        case "Feb": 
            return 2;
        case "Mar": 
            return 3;
        case "Apr": 
            return 4;
        case "May": 
            return 5;
        case "Jun": 
            return 6;
        case "Jul": 
            return 7;
        case "Aug": 
            return 8;
        case "Sep": 
            return 9;
        case "Oct": 
            return 10;
        case "Nov": 
            return 11;
        case "Dec": 
            return 12;
        default: 
            return 0; 
        }
    }

    /**
     * To check if the minute inputed in 'from' is before the minute inputed in 'to'
     * @param starting Time
     * @param ending Time
     * @return 0 if it is after, 1 if it is before and 2 if they are the same 
     */
    public static String durationOfTheEvent (String startingTime, String endingTime){
        int months = monthsOfTheEvent (startingTime,endingTime);
        int days = daysOfTheEvent (startingTime,endingTime);
        int hours = hoursOfTheEvent (startingTime,endingTime);
        int minutes = minutesOfTheEvent (startingTime,endingTime);

        return combineDuratingOfEvent(months,days,hours,minutes); 
    }


    private static String combineDuratingOfEvent(int months, int days, int hours, int minutes) {
        String duration = new String(""); 
        boolean lessThanAnHour=false; 
        boolean lessThanADay=false; 
        boolean lessThanAMonth=false; 

        if (minutes > 0 || minutes < 0) { 
            if (minutes < 0){
                minutes = Math.floorMod(minutes, 60); 
                lessThanAnHour = true; 
            }
            duration = " " + minutes + " minute" + ((minutes == 1)?"":"s"); 
        }
        if (hours > 0 || hours < 0){ 
            if (hours<0){ 
                hours = Math.floorMod(hours, 24);
                lessThanADay = true; 
            }
            if (lessThanAnHour) {
                hours = hours - 1; 
            }
            duration = " " + hours + " hour" + ((hours == 1)?"":"s" + duration); 
        }
        if (days != 0){ 
            if (days<0){ 
                days = Math.floorMod(days, 30);
                lessThanAMonth = true; 
            }
            if (lessThanADay) {
                days = days - 1; 
            }
            duration = " " + days + " day" + ((days == 1)?"":"s" + duration); 
        }
        if (months > 0 || months < 0){ 
            if (lessThanAMonth) {
                months = months - 1; 
            }
            duration = " " + months + " month" + ((months == 1)?"":"s" + duration ); 
        }
        if (minutes == 0 && hours == 0 && days == 0 && months == 0) { 
            duration = "Event starts and end at the same time."; 
        } else { 
            duration = "Duration of the event is: " + duration.trim() + ".";
        }
        return duration;

    }

    /** 
     * Calculate the minute difference between the end and the start
     * @param startingTime
     * @param endingTime
     * @return the minute difference 
     */
    private static int minutesOfTheEvent(String startingTime, String endingTime) {
        int startMinute = Integer.parseInt(startingTime.substring(10,12));
        int endMinute = Integer.parseInt(endingTime.substring(10,12)); 
        return endMinute - startMinute;
    }

    /** 
     * Calculate the hour difference between the end and the start
     * @param startingTime
     * @param endingTime
     * @return the hour difference 
     */
    private static int hoursOfTheEvent(String startingTime, String endingTime) {
        int startHours = Integer.parseInt(startingTime.substring(7,9));
        int endHours = Integer.parseInt(endingTime.substring(7,9)); 
        return endHours - startHours;
    }

    /** 
     * Calculate the day difference between the end and the start
     * @param startingTime
     * @param endingTime
     * @return the day difference 
     */
    private static int daysOfTheEvent(String startingTime, String endingTime) {
        int startDate = Integer.parseInt(startingTime.substring(4,6));
        int endDate = Integer.parseInt(endingTime.substring(4,6)); 
        return endDate - startDate;
    }

    /** 
     * Calculate the month difference between the end and the start
     * @param startingTime
     * @param endingTime
     * @return the month difference 
     */
    private static int monthsOfTheEvent(String startingTime, String endingTime) {
        String startMonth = startingTime.substring(0,3);
        String endMonth = endingTime.substring(0,3); 
        int monthDifference = whatMonth(endMonth) - whatMonth(startMonth); 
        monthDifference = Math.floorMod(monthDifference, 12);
        return monthDifference;
    }

    @Override
    public String toString() {
        return timingInfo;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DateTimeInfo // instanceof handles nulls
                        && this.setTime.equals(((DateTimeInfo) other).setTime)
                        && this.timingInfo.equals(((DateTimeInfo) other).timingInfo)); // state check
    }

    @Override
    public int hashCode() {
        return setTime.hashCode();
    }


    public boolean isDateNull() {
        return this.setTime.equals("Feb 29 23:23");
    }
}
