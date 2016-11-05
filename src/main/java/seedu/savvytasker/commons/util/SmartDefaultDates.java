package seedu.savvytasker.commons.util;

import java.util.Calendar;
import java.util.Date;

import seedu.savvytasker.logic.parser.DateParser.InferredDate;

//@@author A0139915W
/**
 * Helper functions for handling dates.
 */

public class SmartDefaultDates {
    
    private Date startDateTime;
    private Date endDateTime;
    private final Calendar calendar;
    private final Calendar today;
    
    /**
     * Determines the smart defaults for the dates provided. Can set both
     * start and end dates as null to get a basic smart default.
     * @param startDateTime Starting date time
     * @param endDateTime Ending date time
     */
    public SmartDefaultDates(InferredDate startDateTime, InferredDate endDateTime) {
        calendar = Calendar.getInstance();
        today = Calendar.getInstance();
        today.setTime(new Date());
        if (startDateTime == null && endDateTime != null) {
            // apply smart default for endDateTime only
            parseEnd(endDateTime);
        } else if (startDateTime != null && endDateTime == null) {
            // apply smart default for startDateTime only
            parseStart(startDateTime);
        } else if (startDateTime != null && endDateTime != null) {
            parseStartAndEnd(startDateTime, endDateTime);
        }
    }
    
    /**
     * Gets the smart defaults for end date.
     * 
     * If the date is not supplied, the date will default to today.
     * If the time is not supplied, the time will default to 2359:59 on the specified date.
     * If both date and time are not supplied, the date returned will be null.
     * @param today the time now
     * @param endDateTime the end time to parse
     */
    public Date getEnd(InferredDate endDateTime) {
        if (endDateTime == null) return null;
        calendar.setTime(endDateTime.getInferredDateTime());
        if (endDateTime.isDateInferred() && endDateTime.isTimeInferred()) {
            // remove date field
            return null;
        } else if (endDateTime.isDateInferred()) {
            calendar.set(Calendar.DATE, today.get(Calendar.DATE));
            calendar.set(Calendar.MONTH, today.get(Calendar.MONTH));
            calendar.set(Calendar.YEAR, today.get(Calendar.YEAR));
        } else if (endDateTime.isTimeInferred()) {
            calendar.set(Calendar.HOUR_OF_DAY, 23);
            calendar.set(Calendar.MINUTE, 59);
            calendar.set(Calendar.SECOND, 59);
            calendar.set(Calendar.MILLISECOND, 0);
        }
        return calendar.getTime();
    }
    
    /**
     * Sets the starting and ending date/time based on defaults for providing only
     * the end time
     * @param endDateTime end time supplied
     */
    private void parseEnd(InferredDate endDateTime) {
        assert endDateTime.getInferredDateTime() != null;
        Date start = new Date();
        this.endDateTime = getEnd(endDateTime);
        
        // Since only end date is supplied, the task is considered to start at 12am today
        calendar.setTime(start);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        this.startDateTime = calendar.getTime();
        
        if (this.startDateTime.compareTo(this.endDateTime) > 0) {
            // end date is before today, leave start date as null
            this.startDateTime = null;
        }
    }

    
    /**
     * Gets the smart default for start date.
     * 
     * If the date is not supplied, the date will default to today.
     * If the time is not supplied, the time will default to 0000:00 on the specified date.
     * If both date and time are not supplied, the date returned will be null.
     * @param today the time now
     * @param startDateTime the start time to parse
     * @return
     */
    public Date getStart(InferredDate startDateTime) {
        if (startDateTime == null) return null;
        calendar.setTime(startDateTime.getInferredDateTime());
        if (startDateTime.isDateInferred() && startDateTime.isTimeInferred()) {
            // user didn't specify anything
            // remove date field
            return null;
        } else if (startDateTime.isDateInferred()) {
            calendar.set(Calendar.DATE, today.get(Calendar.DATE));
            calendar.set(Calendar.MONTH, today.get(Calendar.MONTH));
            calendar.set(Calendar.YEAR, today.get(Calendar.YEAR));
        } else if (startDateTime.isTimeInferred()) {
            calendar.set(Calendar.HOUR_OF_DAY, 0);
            calendar.set(Calendar.MINUTE, 0);
            calendar.set(Calendar.SECOND, 0);
            calendar.set(Calendar.MILLISECOND, 0);
        }
        return calendar.getTime();
    }

    
    /**
     * Sets the starting and ending date/time based on defaults for providing only
     * the start time
     * @param startDateTime start time supplied
     */
    private void parseStart(InferredDate startDateTime) {
        assert startDateTime.getInferredDateTime() != null;
        Date end = (Date)startDateTime.getInferredDateTime().clone();
        this.startDateTime = getStart(startDateTime);
        
        // Since only the start time is supplied, the task is considered to end today 2359 on the same day as start.
        calendar.setTime(end);
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MILLISECOND, 0);
        this.endDateTime = calendar.getTime();
    }

    /**
     * Sets the starting and ending date/time based on defaults for providing both
     * start and end times.
     * 
     * Note that this method has no restrictions on the starting and ending date/time.
     * i.e. the starting time is later than the ending time.
     * @param startDateTime start time supplied
     * @param endDateTime end time supplied
     */
    private void parseStartAndEnd(InferredDate startDateTime, InferredDate endDateTime) {
        assert endDateTime.getInferredDateTime() != null;
        assert startDateTime.getInferredDateTime() != null;
        Date start = getStart(startDateTime);
        Date end = getEnd(endDateTime);
        this.startDateTime = start;
        this.endDateTime = end;
    }
    
    public Date getStartDate() {
        return startDateTime;
    }
    
    public Date getEndDate() {
        return endDateTime;
    }
}
//@@author