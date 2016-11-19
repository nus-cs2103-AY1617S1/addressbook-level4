package seedu.address.model.item;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.lang.Class;
import java.lang.reflect.Field;

import com.joestelmach.natty.DateGroup;
import com.joestelmach.natty.Parser;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.exceptions.IllegalValueException;

//@@author A0139655U
/**
 * Contains methods that works with Date objects in addition to what is given in Java Date API.
 */
public abstract class DateTime {
    
    private final static Logger logger = LogsCenter.getLogger(DateTime.class);
    
    /** Name of key in map that maps to the start date of task */
    private static final String MAP_START_DATE_KEY = "startDate";
    /** Name of key in map that maps to the end date of task */
    private static final String MAP_END_DATE_KEY = "endDate";

    private static final String MONDAY = "monday";
    private static final String TUESDAY = "tuesday";
    private static final String WEDNESDAY = "wednesday";
    private static final String THURSDAY = "thursday";
    private static final String FRIDAY = "friday";
    private static final String SATURDAY = "saturday";
    private static final String SUNDAY = "sunday";
    
    private static final String TIME = "EXPLICIT_TIME";
    private static final String DATE_FORMAT_ONE = "EXPLICIT_DATE";
    private static final String DATE_FORMAT_TWO = "RELATIVE_DATE";

    private static final int NEGATIVE_ONE = -1;
    private static final int BASE_INDEX = 0;
    private static final int ONE = 1;

    private static final int NUMBER_OF_DAYS_IN_A_WEEK = 7;
    
    private static final String MESSAGE_VALUE_CONSTRAINTS = "DATE_TIME format: "
            + "DATE must be in one of the formats: "
            + "\"13th Sep 2015\", \"02-08-2015\" (mm/dd/yyyy) \n"
            + "TIME must be in one of the formats: "
            + "\"7:30am\", \"19:30\"";

    /**
     * Converts given String into a valid Date object.
     * 
     * @param dateString    User's input for date.
     * @return              Date representation converted from given String.
     * @throws IllegalValueException    If dateString cannot be converted into a Date object.
     */
    public static Date convertStringToDate(String dateString) throws IllegalValueException {
        assert dateString != null;
        
        if (isValidDate(dateString)) {
            List<DateGroup> dates = new Parser().parse(dateString);
            Date date = dates.get(BASE_INDEX).getDates().get(BASE_INDEX);
            return date;
        } else {
            logger.log(Level.FINE, "IllegalValueException thrown in DateTime, convertStringToDate, "
                    + "date is invalid");
            throw new IllegalValueException(MESSAGE_VALUE_CONSTRAINTS);
        }
    }

    /**
     * Returns true if given string contains date value (e.g, "30th Dec 2015").
     * 
     * @param dateString    User's input for date.
     * @return  true        If given string contains date value. Else, return false.
     * @throws IllegalValueException    If dateString cannot be converted into a Date object.
     */
    public static boolean hasDateValue(String dateString) throws IllegalValueException {
        assert dateString != null;
        
        if (isValidDate(dateString)) {
            List<DateGroup> dates = new Parser().parse(dateString);
            String syntaxTree = dates.get(BASE_INDEX).getSyntaxTree().toStringTree();

            if (syntaxTree.contains(DATE_FORMAT_ONE) || syntaxTree.contains(DATE_FORMAT_TWO)) {
                return true;
            } else {
                return false;
            }
        } else {
            logger.log(Level.FINE, "IllegalValueException thrown in DateTime, hasDateValue, "
                    + "date is invalid");
            throw new IllegalValueException(MESSAGE_VALUE_CONSTRAINTS);
        }
    }
    
    /**
     * Returns true if given string contains time value (e.g, "11:30am").
     * 
     * @param dateString    User's input for date.
     * @return              True if given string contains time value. Else, return false.
     * @throws IllegalValueException    If dateString cannot be converted into a Date object.
     */
    public static boolean hasTimeValue(String dateString) throws IllegalValueException {
        assert dateString != null;
        
        if (isValidDate(dateString)) {
            List<DateGroup> dates = new Parser().parse(dateString);
            String syntaxTree = dates.get(BASE_INDEX).getSyntaxTree().toStringTree();

            if (syntaxTree.contains(TIME)) {
                return true;
            } else {
                return false;
            }
        } else {
            logger.log(Level.FINE, "IllegalValueException thrown in DateTime, hasTimeValue, "
                    + "date is invalid");
            throw new IllegalValueException(MESSAGE_VALUE_CONSTRAINTS);
        }
    }
    
    /**
     * Sets endDate to have the same date, month and year as the startDate.
     * If upon setting and endDate is of earlier time than startDate, 
     * then set endDate to be startDate + 1.
     * 
     * @param startDate Start date of Task.
     * @param endDate   End date of Task.
     * @return          Updated value of endDate.
     */
    public static Date setEndDateToStartDate(Date startDate, Date endDate) {
        assert startDate != null && endDate != null;
        Calendar calendarStartDate = Calendar.getInstance();
        calendarStartDate.setTime(startDate);
        int date = calendarStartDate.get(Calendar.DATE);
        int month = calendarStartDate.get(Calendar.MONTH);
        int year = calendarStartDate.get(Calendar.YEAR);

        Calendar calendarEndDate = Calendar.getInstance();
        calendarEndDate.setTime(endDate);
        calendarEndDate.set(Calendar.DATE, date);
        calendarEndDate.set(Calendar.MONTH, month);
        calendarEndDate.set(Calendar.YEAR, year);
        if (calendarEndDate.getTimeInMillis() <= calendarStartDate.getTimeInMillis()) {
            calendarEndDate.set(Calendar.DATE, date + ONE);
        } 
        
        Date updatedDate = calendarEndDate.getTime();
        return updatedDate;
    }

    /**
     * Returns true if given String conforms to what was specified in User Guide e.g 
     * "5pm tomorrow", "02/10/2016", "13 Sep 10pm".
     * 
     * @param dateString    User's input for date.
     * @return              True if given String conforms to what was specified in User Guide. Else, return false.
     */
    public static boolean isValidDate(String dateString) {
        assert dateString != null;
        List<DateGroup> dates = new Parser().parse(dateString.trim());
        try {
            int positionOfMatchingValue = dates.get(BASE_INDEX).getPosition();
            
            if (positionOfMatchingValue > ONE) {
                return false;
            }
        } catch (IndexOutOfBoundsException ioobe) {
            return false;
        }
        return true;
    }
    
    /**
     * Assigns start date to a specified weekday according to the given dateString.
     * 
     * @param dateString    User's input for date.
     * @throws IllegalValueException    If dateString is not a weekday.
     */
    public static Date assignStartDateToSpecifiedWeekday(String dateString) throws IllegalValueException {
        assert dateString != null; 
                
        String lowerCaseDateString = dateString.toLowerCase();
        
        if (lowerCaseDateString.equals(MONDAY) || lowerCaseDateString.equals(TUESDAY) || 
                lowerCaseDateString.equals(WEDNESDAY) || lowerCaseDateString.equals(THURSDAY) || 
                lowerCaseDateString.equals(FRIDAY) || lowerCaseDateString.equals(SATURDAY) || 
                lowerCaseDateString.equals(SUNDAY)) {
        
            List<DateGroup> dates = new Parser().parse(dateString);
            Date date = dates.get(BASE_INDEX).getDates().get(BASE_INDEX);
            date = setTimeToStartOfDay(date);
            date = correctDateIfSameDay(date);
        
            return date;
        } else {
            logger.log(Level.FINE, "IllegalValueException thrown in DateTime, assignStartDateToSpecifiedWeekday, "
                    + "dateString is not a week day");
            throw new IllegalValueException(MESSAGE_VALUE_CONSTRAINTS);
        }
    }

    /**
     * Corrects date if it is same day as today. For e.g, if today is Tuesday, and user input "foo repeat every Tuesday",
     * the starting date will be set to next Tuesday which is false. This method corrects the date to today.
     * 
     * @param date  Date value of Task.
     * @return      Same date if date not equals today's date. Else, return date minus 7 days.
     */
    private static Date correctDateIfSameDay(Date date) {
        assert date != null;
        
        Calendar temp = Calendar.getInstance();
        Calendar actual = Calendar.getInstance();
        actual.setTime(date);
        
        if (actual.get(Calendar.DAY_OF_WEEK) == temp.get(Calendar.DAY_OF_WEEK)) {
            actual.add(Calendar.DATE, NUMBER_OF_DAYS_IN_A_WEEK * NEGATIVE_ONE);
            date = actual.getTime();
        }
        
        return date;
    }
    
    /**
     * Sets time of Date object to start of the day i.e "00:00:00" and returns it.
     * 
     * @param date  Date value of Task.
     * @return date With time values set to start of the day.
     */
    public static Date setTimeToStartOfDay(Date date) {
        assert date != null;
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        
        Date updatedDate = calendar.getTime();
        return updatedDate;
    }

    /**
     * Sets time of Date object to end of the day i.e "23:59:59"
     * 
     * @param date  Date value of Task.
     * @return date With time values set to end of the day.
     */
    public static Date setTimeToEndOfDay(Date date) {
        assert date != null;
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        
        Date updatedDate = calendar.getTime();
        return updatedDate;
    }
    
    /**
     * Updates date according to recurrence rate.
     * 
     * @param date              Date value of Task.
     * @param recurrenceRate    Recurrence rate of Task.
     * @return                  Date with updated values according to recurrence rate.
     */
    public static Date updateDateByRecurrenceRate(Date date, RecurrenceRate recurrenceRate) {
        assert date != null && recurrenceRate != null;
        
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int rate = recurrenceRate.getRate();
        
        switch (recurrenceRate.getTimePeriod()) {
        case HOUR :
            calendar.add(Calendar.HOUR_OF_DAY, rate);
            break;
        case DAY :
            calendar.add(Calendar.DAY_OF_YEAR, rate);
            break;
        case WEEK :
            calendar.add(Calendar.WEEK_OF_YEAR, rate);
            break;
        case MONTH :
            calendar.add(Calendar.MONTH, rate);
            break;
        case YEAR :
            calendar.add(Calendar.YEAR, rate);
            break;
        case MONDAY :
            updateCalendarToComingSpecifiedDay(calendar, MONDAY, rate);
            break;
        case TUESDAY :
            updateCalendarToComingSpecifiedDay(calendar, TUESDAY, rate);
            break;
        case WEDNESDAY :
            updateCalendarToComingSpecifiedDay(calendar, WEDNESDAY, rate);
            break;
        case THURSDAY :
            updateCalendarToComingSpecifiedDay(calendar, THURSDAY, rate);
            break;
        case FRIDAY :
            updateCalendarToComingSpecifiedDay(calendar, FRIDAY, rate);
            break;
        case SATURDAY :
            updateCalendarToComingSpecifiedDay(calendar, SATURDAY, rate);
            break;
        case SUNDAY :
            updateCalendarToComingSpecifiedDay(calendar, SUNDAY, rate);
            break;
        }
        
        date = calendar.getTime();
        return date;
    }
    
    /**
     * Updates calendar to coming Mondays depending on value of rate.
     * 
     * @param calendar  Calendar representation of the date value of Task.
     * @param rate      Amount of Mondays to jump over.
     */
    private static void updateCalendarToComingSpecifiedDay(Calendar calendar, String day, int rate) {
        assert calendar != null;
        updateDateRateMinusOneTimes(calendar, rate);

        calendar.add(Calendar.DATE, ONE);
        
        try {
            Class c = Class.forName("java.util.Calendar");
            Field specifiedDayOfWeek = c.getDeclaredField(day.toUpperCase());
            Integer valueOfSpecifiedDayOfWeek = (Integer) specifiedDayOfWeek.get(calendar);
            
            while (calendar.get(Calendar.DAY_OF_WEEK) != valueOfSpecifiedDayOfWeek) {
                calendar.add(Calendar.DATE, ONE);
            }
        } catch (ClassNotFoundException | NoSuchFieldException | SecurityException | IllegalAccessException e) {
            System.out.println("Here");
        }
    }
    
    /**
     * Updates calendar by (rate - 1) * 7 days.
     * 
     * @param calendar  Calendar representation of the date value of Task.
     * @param rate      Amount of weeks to jump over.
     */
    private static void updateDateRateMinusOneTimes(Calendar calendar, int rate) {
        assert calendar != null;
        if (rate > ONE) {
            calendar.add(Calendar.DATE, (rate - ONE) * NUMBER_OF_DAYS_IN_A_WEEK);
        }
    }
    
    public static String getMessageValueConstraints() {
        return MESSAGE_VALUE_CONSTRAINTS;
    }
    
    /** 
     * @return The key in map that maps to the start date of task.
     */
    public static String getMapStartDateKey() {
        return MAP_START_DATE_KEY;
    }
    
    /** 
     * @return The key in map that maps to the end date of task.
     */
    public static String getMapEndDateKey() {
        return MAP_END_DATE_KEY;
    }
}
