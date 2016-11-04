package seedu.task.logic.parser;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Date;
//@@author A0152958R
public class TimeParserResult {


    private String matchString;
    private LocalDate firstDate;
    private LocalDate secondDate;
    private LocalTime firstTime;
    private LocalTime secondTime;
    private boolean timeValid;
    private DateTimeStatus rawDateTimeStatus = DateTimeStatus.NONE;

    /**
     *  enum to represent all possible recognized time type
     */
    public enum DateTimeStatus {
        NONE, END_TIME, END_DATE, END_DATE_END_TIME,
        
        START_TIME, START_TIME_END_TIME, START_TIME_END_DATE, START_TIME_END_DATE_END_TIME,
        START_DATE, START_DATE_END_TIME, START_DATE_END_DATE, START_DATE_END_DATE_END_TIME,
        START_DATE_START_TIME, START_DATE_START_TIME_END_TIME,
        START_DATE_START_TIME_END_DATE, START_DATE_START_TIME_END_DATE_END_TIME,
    }

    /**
     * Check whether end time is after start time
     */
    public void checkInvalidTimeRange() {
        timeValid = true;
        DateTimeStatus status = getDateTimeStatus();
        switch (status) {
            case START_DATE_END_DATE:
                if (firstDate.isAfter(secondDate)) {
                    timeValid = false;
                }
                break;
            case START_DATE_START_TIME_END_DATE_END_TIME:
                if (firstDate.isAfter(secondDate)) {
                    timeValid = false;
                } else if (firstDate.isEqual(secondDate) && !firstTime.isBefore(secondTime)) {
                    timeValid = false;
                }
                break;
            default:
                break;
        }
    }

    /**
     * Update date and time with default setting
     * e.g. from today 1pm to 3pm. End date is initially null. The method will set end date as today.
     */
    public void updateDateTime() {
        rawDateTimeStatus = getDateTimeStatus();
    }

    /**
     * Cast the time result to an enum instance
     * This method is used to simplify further condition control
     */
    public DateTimeStatus getDateTimeStatus() {
        DateTimeStatus dateTimeStatus = DateTimeStatus.NONE;
        if (secondTime != null) {
            dateTimeStatus = DateTimeStatus.END_TIME;
        }
        if (secondDate != null) {
            switch (dateTimeStatus) {
                case NONE:
                    dateTimeStatus = DateTimeStatus.END_DATE;
                    break;
                case END_TIME:
                    dateTimeStatus = DateTimeStatus.END_DATE_END_TIME;
                    break;
                default:
                    break;
            }
        }
        if (firstTime != null) {
            switch (dateTimeStatus) {
                case NONE:
                    dateTimeStatus = DateTimeStatus.START_TIME;
                    break;
                case END_TIME:
                    dateTimeStatus = DateTimeStatus.START_TIME_END_TIME;
                    break;
                case END_DATE:
                    dateTimeStatus = DateTimeStatus.START_TIME_END_DATE;
                    break;
                case END_DATE_END_TIME:
                    dateTimeStatus = DateTimeStatus.START_TIME_END_DATE_END_TIME;
                    break;
                default:
                    break;
            }
        }
        if (firstDate != null) {
            switch (dateTimeStatus) {
                case NONE:
                    dateTimeStatus = DateTimeStatus.START_DATE;
                    break;
                case END_TIME:
                    dateTimeStatus = DateTimeStatus.START_DATE_END_TIME;
                    break;
                case END_DATE:
                    dateTimeStatus = DateTimeStatus.START_DATE_END_DATE;
                    break;
                case END_DATE_END_TIME:
                    dateTimeStatus = DateTimeStatus.START_DATE_END_DATE_END_TIME;
                    break;
                case START_TIME:
                    dateTimeStatus = DateTimeStatus.START_DATE_START_TIME;
                    break;
                case START_TIME_END_TIME:
                    dateTimeStatus = DateTimeStatus.START_DATE_START_TIME_END_TIME;
                    break;
                case START_TIME_END_DATE:
                    dateTimeStatus = DateTimeStatus.START_DATE_START_TIME_END_DATE;
                    break;
                case START_TIME_END_DATE_END_TIME:
                    dateTimeStatus = DateTimeStatus.START_DATE_START_TIME_END_DATE_END_TIME;
                    break;
                default:
                    break;
            }
        }
        return dateTimeStatus;
    }

    public DateTimeStatus getRawDateTimeStatus() {
        return rawDateTimeStatus;
    }

    public String getMatchString() {
        return matchString;
    }

    public void setMatchString(String matchString) {
        this.matchString = matchString;
    }

    public void setFirstDate(Date date) {
        firstDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    }

    public void setSecondDate(Date date) {
        secondDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    }

    public void setFirstTime(Date date) {
    	
        firstTime = date.toInstant().atZone(ZoneId.systemDefault()).toLocalTime();
    }

    public void setSecondTime(Date date) {
        secondTime = date.toInstant().atZone(ZoneId.systemDefault()).toLocalTime();
    }
    public LocalDate getFirstDate() {
        return firstDate;
    }

    public LocalDate getSecondDate() {
        return secondDate;
    }

    public LocalTime getFirstTime() {
        return firstTime;
    }

    public LocalTime getSecondTime() {
        return secondTime;
    }
    public void setFirstDate(LocalDate firstDate) {
        this.firstDate = firstDate;
    }

    public void setSecondDate(LocalDate secondDate) {
        this.secondDate = secondDate;
    }

    public void setFirstTime(LocalTime firstTime) {
        this.firstTime = firstTime;
    }

    public void setSecondTime(LocalTime secondTime) {
        this.secondTime = secondTime;
    }
    public boolean isTimeValid() {
        return timeValid;
    }

}
