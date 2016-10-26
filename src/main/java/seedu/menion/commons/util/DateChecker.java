package seedu.menion.commons.util;

import seedu.menion.commons.exceptions.IllegalValueException;
import seedu.menion.model.activity.ActivityDate;
import seedu.menion.model.activity.ActivityTime;

//@@author A0139164A
public class DateChecker {
    
    public static final String END_DATE_BEFORE_START_DATE_ERROR = "Oh no! Menion has detected that the end date/time of your event is before the " +
                                                                  "start date/time of your event. Please try again!";
    public DateChecker() {
        
    }
    
    /**
     * Checks if Time will exceed the 24hour format.
     */
    public void validTime(String time) throws IllegalValueException {
        int valueOfMinute = Integer.valueOf(time.substring(2));
        int valueOfTime = Integer.valueOf(time);
        
        if (valueOfTime > 2359 || valueOfTime < 0) {
            throw new IllegalValueException(ActivityTime.ACTIVITY_TIME_CONSTRAINTS);
        }
        if (valueOfMinute > 59 || valueOfMinute < 0) {
            throw new IllegalValueException(ActivityTime.ACTIVITY_TIME_CONSTRAINTS);
        }
    }
    
    public void validDate(String date) throws IllegalValueException {
        String[] valueOfDate = date.toString().split("-");

        int valueOfYear = Integer.valueOf(valueOfDate[2]);
        int valueofMonth = Integer.valueOf(valueOfDate[1]);
        int valueOfDay = Integer.valueOf(valueOfDate[0]);
        
        if (valueOfYear > 9999 || valueofMonth > 12 || valueOfDay > 31) {
            throw new IllegalValueException(ActivityDate.MESSAGE_ACTIVITYDATE_INVALID);
        }
    }
    /**
     * Checks if EndDate is after StartDate & EndTime is after startTime when creating event
     */
    public void validEventDate(ActivityDate startDate, ActivityTime startTime, ActivityDate endDate, ActivityTime endTime) throws IllegalValueException {
        
        String[] fromDate = startDate.toString().split("-");
        int fromYear = Integer.valueOf(fromDate[2]);
        int fromMonth = Integer.valueOf(fromDate[1]);
        int fromDay = Integer.valueOf(fromDate[0]);
        int fromTime = Integer.valueOf(startTime.toString());

        String[] toDate = endDate.toString().split("-");
        int toYear = Integer.valueOf(toDate[2]);
        int toMonth = Integer.valueOf(toDate[1]);
        int toDay = Integer.valueOf(toDate[0]);
        int toTime = Integer.valueOf(endTime.toString());
        
        boolean sameDate = false;
        
        // Compare date
        if (fromYear > toYear) {
            throw new IllegalValueException(END_DATE_BEFORE_START_DATE_ERROR);
        }
        if (fromYear == toYear && fromMonth > toMonth) {
            throw new IllegalValueException(END_DATE_BEFORE_START_DATE_ERROR);
        }
        if (fromYear == toYear && fromMonth == toMonth && fromDay > toDay) {
            throw new IllegalValueException(END_DATE_BEFORE_START_DATE_ERROR);
        }
        if (fromYear == toYear && fromMonth == toMonth && fromDay == toDay && fromTime > toTime) {
            throw new IllegalValueException(END_DATE_BEFORE_START_DATE_ERROR);
        }
        
        if (fromYear == toYear && fromMonth == toMonth && fromDay == toDay) {
            sameDate = true;
        }
        
        if (sameDate) {
            // Compare time
            if (fromTime > toTime) {
                throw new IllegalValueException(END_DATE_BEFORE_START_DATE_ERROR);
            }
        }
        return;
    }
    
}
