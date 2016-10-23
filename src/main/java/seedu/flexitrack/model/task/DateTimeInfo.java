package seedu.flexitrack.model.task;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.joestelmach.natty.DateGroup;

import seedu.flexitrack.commons.exceptions.IllegalValueException;
import seedu.flexitrack.logic.commands.ListCommand;

/**
 * Represents a DateTimeInfo class in FlexiTrack
 */
public class DateTimeInfo {
    public static final String MESSAGE_DATETIMEINFO_CONSTRAINTS = "Invalid time inputed. Please check your spelling!";
    public static final boolean DUE_DATE_OR_START_TIME = true;
    public static final boolean END_TIME = false;
    private static final Pattern TIME_TYPE_DATA_ARGS_FORMAT = Pattern.compile("(?<info>.+)");
    private static final String MESSAGE_FROM_IS_AFTER_TO = "Please check the timing inputed! The given starting time is after the ending time.";

    private String setTime;

    public DateTimeInfo(String givenTime) throws IllegalValueException {
        setDateGroupTime(givenTime);
    }

    /**
     * Set the setTime (DateGroup object) as the date inputed by the user
     * 
     * @param givenTime
     */
    public void setDateGroupTime(String givenTime) throws IllegalValueException {
        assert givenTime != null;
        final Matcher matcher = TIME_TYPE_DATA_ARGS_FORMAT.matcher(givenTime.trim());
        matcher.matches();
        DateTimeInfoParser parsedTiming = new DateTimeInfoParser(matcher.group("info"));
        this.setTime = parsedTiming.getParsedTimingInfo();
        formatTiming(parsedTiming.isInferred());
    }

    private void formatTiming(boolean inferred) {
        if (inferred) {
            setTime = getDateMonthYear() + " 07:59";
        } else {
            setTime = getDateMonthYear() + " " + setTime.substring(12, 17);
        }
    }

    private String getDateMonthYear() {
        return setTime.substring(5, 12) + setTime.substring(25, 29);
    }

    /**
     * Validate the timing inputed
     * 
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
     * 
     * @param month
     * @return month in integer
     */
    public static int whatMonth(String month) {
        switch (month) {
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
     * To check if the minute inputed in 'from' is before the minute inputed in
     * 'to'
     * 
     * @param starting Time
     * @param ending Time
     * @return the duration of the event
     */
    public static String durationOfTheEvent(String startingTime, String endingTime) {
        return durationBetweenTwoTiming(startingTime,endingTime);
    }

    private static String durationBetweenTwoTiming(String startingTime, String endingTime) {
        int years = yearsOfTheEvent(startingTime, endingTime);
        int months = monthsOfTheEvent(startingTime, endingTime);
        int days = daysOfTheEvent(startingTime, endingTime);
        int hours = hoursOfTheEvent(startingTime, endingTime);
        int minutes = minutesOfTheEvent(startingTime, endingTime);

        return combineDuratingOfEvent(years, months, days, hours, minutes);        
    }

    private static String combineDuratingOfEvent(int years, int months, int days, int hours, int minutes) {
        String duration1 = new String("");

        if (minutes > 0 || minutes < 0) {
            if (minutes < 0) {
                minutes = Math.floorMod(minutes, 60);
                hours=hours-1;
            }
            duration1 = " " + minutes + " minute" + ((minutes == 1) ? "" : "s");
        }
        if (hours < 0) {
            hours = Math.floorMod(hours, 24);
            days=days-1;
        } 
        if(hours > 0){
            duration1 = " " + hours + " hour" + ((hours == 1) ? "" : "s" + duration1);
        }
        if (days < 0) {
            days = Math.floorMod(days, 31);
            months=months-1;
        }
        if(days>0){
            duration1 = " " + days + " day" + ((days == 1) ? "" : "s" + duration1);
        }
        if (months < 0) {
            months = Math.floorMod(months, 12);
            years=years-1;
        }
        if(months>0){
            duration1 = " " + months + " month" + ((months == 1) ? "" : "s" + duration1);
        }
        if (years < 0) {
            return MESSAGE_FROM_IS_AFTER_TO;
        } else if (years > 0) {
            duration1 = " " + years + " year" + ((years == 1) ? "" : "s" + duration1);
        }

        if (minutes == 0 && hours == 0 && days == 0 && months == 0) {
            duration1 = "Event starts and end at the same time.";
        } else {
            duration1 = "Duration of the event is: " + duration1.trim() + ".";
        }
        return duration1;

    }

    /**
     * Calculate the minute difference between the end and the start
     * 
     * @param startingTime
     * @param endingTime
     * @return the minute difference
     */
    private static int minutesOfTheEvent(String startingTime, String endingTime) {
        int startMinute = Integer.parseInt(startingTime.substring(15, 17));
        int endMinute = Integer.parseInt(endingTime.substring(15, 17));
        return endMinute - startMinute;
    }

    /**
     * Calculate the hour difference between the end and the start
     * 
     * @param startingTime
     * @param endingTime
     * @return the hour difference
     */
    private static int hoursOfTheEvent(String startingTime, String endingTime) {
        int startHours = Integer.parseInt(startingTime.substring(12, 14));
        int endHours = Integer.parseInt(endingTime.substring(12, 14));
        return endHours - startHours;
    }

    /**
     * Calculate the day difference between the end and the start
     * 
     * @param startingTime
     * @param endingTime
     * @return the day difference
     */
    private static int daysOfTheEvent(String startingTime, String endingTime) {
        int startDate = Integer.parseInt(startingTime.substring(4, 6));
        int endDate = Integer.parseInt(endingTime.substring(4, 6));
        return endDate - startDate;
    }

    /**
     * Calculate the day difference between the end and the start
     * 
     * @param startingTime
     * @param endingTime
     * @return the day difference
     */
    private static int yearsOfTheEvent(String startingTime, String endingTime) {
        int startYear = Integer.parseInt(startingTime.substring(7, 11));
        int endYear = Integer.parseInt(endingTime.substring(7, 11));
        return endYear - startYear;
    }

    /**
     * Calculate the month difference between the end and the start
     * 
     * @param startingTime
     * @param endingTime
     * @return the month difference
     */
    private static int monthsOfTheEvent(String startingTime, String endingTime) {
        String startMonth = startingTime.substring(0, 3);
        String endMonth = endingTime.substring(0, 3);
        int monthDifference = whatMonth(endMonth) - whatMonth(startMonth);
        return monthDifference;
    }

    @Override
    public String toString() {
        return setTime.substring(0, 17);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DateTimeInfo // instanceof handles nulls
                        && this.setTime.equals(((DateTimeInfo) other).setTime)); // state check
    }

    @Override
    public int hashCode() {
        return setTime.hashCode();
    }

    public boolean isDateNull() {
        return this.setTime.equals("Feb 29 2000 00:00");
    }

    public void isEndTimeInferred() {
        if (setTime.substring(12, 17).equals("07:59")) {
            setTime = setTime.substring(0, 12) + "16:59";
        }
    }

    public static boolean isInTheFuture(DateTimeInfo Date) {
        String result = MESSAGE_FROM_IS_AFTER_TO;
        try {
            result = durationBetweenTwoTiming(new DateTimeInfo ("now").toString(),Date.toString());
        } catch (IllegalValueException e) {
            e.printStackTrace();
        }
        return !result.equals(MESSAGE_FROM_IS_AFTER_TO);
    }

    public static boolean isInThePast(DateTimeInfo Date) {
        String result = MESSAGE_FROM_IS_AFTER_TO;
        try {
            result = durationBetweenTwoTiming(Date.toString(),new DateTimeInfo ("now").toString());
        } catch (IllegalValueException e) {
            e.printStackTrace();
        }
        return !result.equals(MESSAGE_FROM_IS_AFTER_TO);
    }

    public static boolean isOnTheDate(String keyWords, ReadOnlyTask task) {
        String dateInfo = keyWords.replace(ListCommand.LIST_MARK_COMMAND, "").replace(ListCommand.LIST_UNMARK_COMMAND, "").trim();
        try {
            dateInfo = new DateTimeInfo (dateInfo).toString().substring(0,11);
        } catch (IllegalValueException e) {
            e.printStackTrace();
        }
        if ( task.getDueDate().toString().contains(dateInfo) || task.getEndTime().toString().contains(dateInfo) 
                || task.getStartTime().toString().contains(dateInfo)){
            return true; 
        }
        return false;
    }
}
