package seedu.savvytasker.commons.util;

import java.util.Calendar;
import java.util.Date;

import seedu.savvytasker.logic.parser.DateParser.InferredDate;

//@@author A0139915W
/**
 * Helper functions for handling dates.
 * @author A0139915W
 */

public class SmartDefaultDates {
    
    private Date startDateTime;
    private Date endDateTime;
    private final Calendar calendar;
    private final Calendar today;
    
    /**
     * 
     * @param startDateTime Starting date time
     * @param endDateTime Ending date time
     * @throws InvalidDateException If endDateTime is earlier than startDateTime
     */
    public SmartDefaultDates(InferredDate startDateTime, InferredDate endDateTime) {
        calendar = Calendar.getInstance();
        today = Calendar.getInstance();
        today.setTime(new Date());
        if (startDateTime == null && endDateTime == null) {
            // dates not being supplied, nothing to parse
        } else if (startDateTime == null && endDateTime != null) {
            // apply smart default for endDateTime only
            parseEnd(endDateTime);
        } else if (startDateTime != null && endDateTime == null) {
            // apply smart default for startDateTime only
            parseStart(startDateTime);
        } else {
            parseStartAndEnd(startDateTime, endDateTime);
        }
    }
    
    /**
     * Gets the smart default for end date
     * @param today the time now
     * @param endDateTime the end time to parse
     * @return
     */
    public Date getEnd(InferredDate endDateTime) {
        if (endDateTime == null) return null;
        calendar.setTime(endDateTime.getInferredDateTime());
        if (endDateTime.isDateInferred()) {
            // date not supplied
            // defaults to today
            calendar.set(Calendar.DATE, today.get(Calendar.DATE));
            calendar.set(Calendar.MONTH, today.get(Calendar.MONTH));
            calendar.set(Calendar.YEAR, today.get(Calendar.YEAR));
        } else if (endDateTime.isTimeInferred()) {
            // time not supplied
            // defaults to 2359
            calendar.set(Calendar.HOUR_OF_DAY, 23);
            calendar.set(Calendar.MINUTE, 59);
            calendar.set(Calendar.SECOND, 59);
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
        this.startDateTime = calendar.getTime();
    }

    
    /**
     * Gets the smart default for start date
     * @param today the time now
     * @param startDateTime the start time to parse
     * @return
     */
    public Date getStart(InferredDate startDateTime) {
        if (startDateTime == null) return null;
        calendar.setTime(startDateTime.getInferredDateTime());
        if (startDateTime.isDateInferred()) {
            // date not supplied
            // defaults to today
            calendar.set(Calendar.DATE, today.get(Calendar.DATE));
            calendar.set(Calendar.MONTH, today.get(Calendar.MONTH));
            calendar.set(Calendar.YEAR, today.get(Calendar.YEAR));
        } else if (startDateTime.isTimeInferred()) {
            // time not supplied
            // defaults to 0000
            calendar.set(Calendar.HOUR_OF_DAY, 0);
            calendar.set(Calendar.MINUTE, 0);
            calendar.set(Calendar.SECOND, 0);
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
        this.endDateTime = calendar.getTime();
    }

    /**
     * Sets the starting and ending date/time based on defaults for providing both
     * start and end times
     * @param startDateTime start time supplied
     */
    private void parseStartAndEnd(InferredDate startDateTime, InferredDate endDateTime) {
        assert endDateTime.getInferredDateTime() != null;
        assert startDateTime.getInferredDateTime() != null;
        Date start = getStart(startDateTime);
        Date end = getEnd(endDateTime);
        this.startDateTime = start;
        this.endDateTime = end;
        if (this.startDateTime.compareTo(this.endDateTime) > 0) {
            calendar.setTime(this.endDateTime);
            calendar.add(Calendar.DATE, 7);
            this.endDateTime = calendar.getTime();
        }
    }
    
    public Date getStartDate() {
        return startDateTime;
    }
    
    public Date getEndDate() {
        return endDateTime;
    }
}
//@@author A0139915W