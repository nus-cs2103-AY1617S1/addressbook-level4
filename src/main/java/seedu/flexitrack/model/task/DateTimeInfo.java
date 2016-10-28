//@@author A0127686R
package seedu.flexitrack.model.task;

import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.joestelmach.natty.DateGroup;

import seedu.flexitrack.commons.exceptions.IllegalValueException;
import seedu.flexitrack.logic.commands.ListCommand;

/**
 * Represents a DateTimeInfo class in FlexiTrack
 */
public class DateTimeInfo implements Comparable<DateTimeInfo>{
    public static final String MESSAGE_DATETIMEINFO_CONSTRAINTS = "Invalid time inputed. Please check your spelling!";
    public static final String MESSAGE_FROM_IS_AFTER_TO = "Please check the timing inputed! The given starting time is after the ending time.";

    private static final Pattern TIME_TYPE_DATA_ARGS_FORMAT = Pattern.compile("(?<info>.+)");
    private static final int AVERAGE_DAYS_IN_A_MONTH = 30; 
    private static final int DAYS_IN_A_WEEK = 7;
    private static final boolean FAIL_DUE_TO_EXCEPTION = false; 
    
    private String setTime;
    private DateTimeInfoParser timeInfo;
    
    public DateTimeInfo(String givenTime) throws IllegalValueException {
        setDateGroupTime(givenTime);
    }

    /**
     * Set the setTime (DateGroup object) as the date inputed by the user
     * 
     * @param givenTime
     * @throws IllegalValueException 
     */
    public void setDateGroupTime(String givenTime) throws IllegalValueException {
        assert givenTime != null;
        final Matcher matcher = TIME_TYPE_DATA_ARGS_FORMAT.matcher(givenTime.trim());
        matcher.matches();
        DateTimeInfoParser parsedTiming;
        try {
            parsedTiming = new DateTimeInfoParser(matcher.group("info"));
            timeInfo = parsedTiming;
            this.setTime = parsedTiming.getParsedTimingInfo();
            formatTiming(parsedTiming.isInferred());
        } catch (IllegalValueException e) {
            throw new IllegalValueException (MESSAGE_DATETIMEINFO_CONSTRAINTS);
        }
    }
    
    public DateTimeInfoParser getTimeInfo() {
        return timeInfo;
    }

    /**
     * Change the format of the timing saved in setTime 
     * @param inferred
     */
    private void formatTiming(boolean inferred) {
        if (inferred) {
            setTime = getDateMonthYear() + " 07:59";
        } else {
            setTime = getDateMonthYear() + " " + setTime.substring(12, 17);
        }
    }

    /**
     * Extract the month, date and year of a particular date 
     * @return timing in MMM DD YYYY format 
     */
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
        return timeDifferenceInString(durationBetweenTwoTiming(startingTime,endingTime));
    }

    /** 
     * prepare variables needed to calculate the duration between two timing 
     * @param startingTime
     * @param endingTime
     * @return the duration of the event in an array. 0 represents minutes and 4 represents years
     */
    private static int[] durationBetweenTwoTiming(String startingTime, String endingTime) {
        int years = yearsOfTheEvent(startingTime, endingTime);
        int months = monthsOfTheEvent(startingTime, endingTime);
        int days = daysOfTheEvent(startingTime, endingTime);
        int hours = hoursOfTheEvent(startingTime, endingTime);
        int minutes = minutesOfTheEvent(startingTime, endingTime);

        return combineDuratingOfEvent(years, months, days, hours, minutes);
    }

    
    private static String timeDifferenceInString(int[] timeDifference) {
        String duration = new String ("");    
        if (timeDifference[0]==-1){
            return MESSAGE_FROM_IS_AFTER_TO; 
        } else if (timeDifference[0]>0){
            duration = " " + timeDifference[0] + " minute" + ((timeDifference[0] == 1) ? "" : "s");
        }
        if (timeDifference[1]>0){
            duration = " " + timeDifference[1] + " hour" + ((timeDifference[1] == 1) ? "" : "s" + duration);
        }
        if (timeDifference[2]>0){
            duration = " " + timeDifference[2] + " day" + ((timeDifference[2] == 1) ? "" : "s" + duration);  
        }
        if (timeDifference[3]>0){
            duration = " " + timeDifference[3] + " month" + ((timeDifference[3] == 1) ? "" : "s" + duration);
        }
        if (timeDifference[4]>0){
            duration = " " + timeDifference[4] + " year" + ((timeDifference[4] == 1) ? "" : "s" + duration);
        }
        if (duration.equals("")) {
            return "Event starts and end at the same time.";
        } else {
            return "Duration of the event is: " + duration.trim() + ".";
        }
    }

    /**
     * Calculate the duration of the event
     * @param years
     * @param months
     * @param days
     * @param hours
     * @param minutes
     * @return the duration of the event in a string 
     */
    private static int[] combineDuratingOfEvent(int years, int months, int days, int hours, int minutes) {
        int[] timeDifference = new int[5]; 

        if (minutes > 0 || minutes < 0) {
            if (minutes < 0) {
                minutes = Math.floorMod(minutes, 60);
                hours=hours-1;
            }
            timeDifference[0]=minutes; 
        }
        if (hours < 0) { 
            hours = Math.floorMod(hours, 24);
            days=days-1;
        } 
        timeDifference[1]=hours; 
        if (days < 0) {
            days = Math.floorMod(days, 31);
            months=months-1;
        }
        timeDifference[2]=days; 
        if (months < 0) {
            months = Math.floorMod(months, 12);
            years=years-1;
        }
        timeDifference[3]=months; 
        if (years < 0) {
            timeDifference[0]=-1;
            return timeDifference;
        } else if (years > 0) {
            timeDifference[4]=years; 
        }
        return timeDifference;

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
     * Calculate the year difference between the end and the start
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
        return setTime;
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

    /**
     * @return true if the date not specified; 
     */
    public boolean isDateNull() {
        return this.setTime.equals("Feb 29 2000 00:00");
    }

    /**
     * If the time is inferred, replace "07:59" with "16:59" 
     */
    public void isEndTimeInferred() {
        if (setTime.substring(12, 17).equals("07:59")) {
            setTime = setTime.substring(0, 12) + "16:59";
        }
    }

    /**
     * Process the task if the task is in the future
     * @param timeNow
     * @param Date
     * @return true if the timing timeNow is after the timing Date
     */
    public static boolean isInTheFuture(DateTimeInfo timeNow, DateTimeInfo Date) {
        int[] duration = durationBetweenTwoTiming(timeNow.toString(),Date.toString());
        return duration[0]!=-1;
    }

    /**
     * Process the task if the task is in the past
     * @param timeNow
     * @param Date
     * @return true if the timing timeNow is before the timing Date
     */
    public static boolean isInThePast(DateTimeInfo timeNow, DateTimeInfo Date) {
        return isInTheFuture(Date,timeNow);
    }
    
    /**
     * Prepare the keyword and process if the task is within the specified date. 
     * @param keyWords
     * @param task
     * @return 
     */
    public static boolean isOnTheDate(String dateInfo, ReadOnlyTask task) {
        try {
            dateInfo = new DateTimeInfo (dateInfo).toString().substring(0,11);
        } catch (IllegalValueException e) {
            new IllegalValueException (MESSAGE_DATETIMEINFO_CONSTRAINTS);
        }
        return isTaskOnTheSpecifiedDate(task, dateInfo);
    }

    /**
     * Process if the task given has any relation with the dateInfo. 
     * For a task relation is defined as the due date is the dateInfo date. 
     * For an event, the event duration (inclusive the starting and the ending date) is within the specified  DateInfo date. 
     * @param task
     * @param dateInfo
     * @return true if the task has anything to do with the day of interest
     */
    private static boolean isTaskOnTheSpecifiedDate(ReadOnlyTask task, String dateInfo) {
        return task.getDueDate().toString().contains(dateInfo) || task.getEndTime().toString().contains(dateInfo) 
                || task.getStartTime().toString().contains(dateInfo) 
                || isTaskAnEventPassingThisDate(task, dateInfo);
    }

    /**
     * Process the data if it the task is a event and it is passing through the date specified.
     * @param task
     * @param dateInfo
     * @return true if a task is an event and the day interest is within the starting date and the ending date 
     */
    private static boolean isTaskAnEventPassingThisDate(ReadOnlyTask task, String dateInfo) {
        if (!task.getIsEvent()){
            return false; 
        } 
        DateTimeInfo dateSpecified;
        try {
            dateSpecified = new DateTimeInfo (dateInfo);
            return isInTheFuture(task.getStartTime(), dateSpecified) && isInTheFuture(dateSpecified, task.getEndTime()); 
        } catch (IllegalValueException e) {
            new IllegalValueException (MESSAGE_DATETIMEINFO_CONSTRAINTS);
        }
        return FAIL_DUE_TO_EXCEPTION; 
    }

    /** 
     * Process if the a task specified is with in the duration stated.
     * @param keyWords
     * @param task
     * @return true if the date is within the duration 
     */
    public static boolean withInTheDuration(String keyWords, ReadOnlyTask task, String dateNow) {
        boolean isWithInTime = false; 
        if ( keyWords.contains(ListCommand.LIST_LAST_WEEK_COMMAND) ){
            return isNotFloatingTaskAndWithinTheTime(task, dateNow, -DAYS_IN_A_WEEK);
        } else if ( keyWords.contains(ListCommand.LIST_LAST_MONTH_COMMAND) ){
            return isNotFloatingTaskAndWithinTheTime(task, dateNow, -AVERAGE_DAYS_IN_A_MONTH);
        } else if ( keyWords.contains(ListCommand.LIST_NEXT_MONTH_COMMAND) ){
            return isNotFloatingTaskAndWithinTheTime(task, dateNow, AVERAGE_DAYS_IN_A_MONTH);
        } else if ( keyWords.contains(ListCommand.LIST_NEXT_WEEK_COMMAND) ){
            return isNotFloatingTaskAndWithinTheTime(task, dateNow, DAYS_IN_A_WEEK);
        }        
        return isWithInTime;
    }

    /**
     * Process if the task given is either a deadline task or an event within the specified timing
     * @param task
     * @param dateNow
     * @param expectedDays 
     * @return true if the task is not a floating task and it is within the specified timing
     */
    private static boolean isNotFloatingTaskAndWithinTheTime(ReadOnlyTask task, String dateNow, int expectedDays) {
        if (task.getIsNotFloatingTask()){
            return isTaskWithInTheDuration(task, dateNow, expectedDays); 
        } else { 
            return false; 
        }
    }

    /**
     * Process if a deadline task or an event is within the duration specified.
     * @param task
     * @param dateNow
     * @param expectedDays
     * @return true if the task is within the stated duration 
     */
    private static boolean isTaskWithInTheDuration(ReadOnlyTask task, String dateNow, int expectedDays) {
        boolean isTimeWithinExpectedTime=false;
        if (task.getIsNotFloatingTask()){
            isTimeWithinExpectedTime = isTimeDifferenceLessThanSpecified(dateNow, 
                    task.getStartingTimeOrDueDate().toString(), expectedDays);
        }
        return isTimeWithinExpectedTime;
    }
    
    /**
     * Provide an easy access to the current timing in String 
     * @return String of the current time MMM DD YYYY HH:MM format. 
     */
    public static DateTimeInfo getCurrentTimeInString() {
        DateTimeInfo dateNow = null;
        try {
            dateNow = new DateTimeInfo ("now");
        } catch (IllegalValueException e) {
            new IllegalValueException (MESSAGE_DATETIMEINFO_CONSTRAINTS);
        }
        return dateNow;
    }

    /**
     * Process if the two given time is less than the time duration.
     * @param startTime 
     * @param endTime 
     * @param limitTimeDuration 
     * @return  true if the time Difference is less than specified
     */
    private static boolean isTimeDifferenceLessThanSpecified(String startTime, String endTime, int limitTimeDuration) {
        if (limitTimeDuration<0){
            limitTimeDuration = limitTimeDuration*(-1);  
            return isTimeDifferenceLessThanSpecified (endTime, startTime, limitTimeDuration);
        }

        int years = yearsOfTheEvent(startTime, endTime);
        int months = monthsOfTheEvent(startTime, endTime);
        int days = daysOfTheEvent(startTime, endTime);
        int hours = hoursOfTheEvent(startTime, endTime);

        if (hours < 0) {
            hours = Math.floorMod(hours, 24);
            days=days-1;
        } 
        if (days < 0) {
            days = Math.floorMod(days, 30);
            months=months-1;
        }
        if (months < 0 || months>1) {
            return false; 
        }
        if (years < 0 || years > 0) {
            return false;
        } 
        if(days <= limitTimeDuration && months==0){
            return true; 
        } else if ( limitTimeDuration==AVERAGE_DAYS_IN_A_MONTH && months==1){
            return true; 
        } else {
            return false; 
        }
    }

    @Override
    public int compareTo(DateTimeInfo dateTimeInfo2) {
        Date dateObject1 = convertToDateObject(this.setTime);
        Date dateObject2 = convertToDateObject(dateTimeInfo2.setTime);
        return dateObject1.compareTo(dateObject2);
    }
    
    private Date convertToDateObject(String dateString){
        try{
            DateTimeInfoParser parsedTiming = new DateTimeInfoParser(dateString);
            return parsedTiming.getParsedTimingObject().get(0);
        }catch(IllegalValueException ive){
            return null;
        }
        
    }
    
}
