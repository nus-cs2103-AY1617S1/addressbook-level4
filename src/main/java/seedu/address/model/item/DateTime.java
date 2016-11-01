package seedu.address.model.item;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.joestelmach.natty.DateGroup;
import com.joestelmach.natty.Parser;

import seedu.address.commons.exceptions.IllegalValueException;

//@@author A0139655U
/**
 * Contains methods that works with Date objects in addition to what is given in Java Date API.
 */
public abstract class DateTime {

    public static final String TIME = "EXPLICIT_TIME";
    private static final String DATE_FORMAT_ONE = "EXPLICIT_DATE";
    private static final String DATE_FORMAT_TWO = "RELATIVE_DATE";

    private static final int NEGATIVE_ONE = -1;
    private static final int BASE_INDEX = 0;
    private static final int ONE = 1;

    private static final int NUMBER_OF_DAYS_IN_A_WEEK = 7;
    
    public static final String MESSAGE_VALUE_CONSTRAINTS = "DATE_TIME format: "
            + "DATE must be in one of the formats: "
            + "\"13th Sep 2015\", \"02-08-2015\" (mm/dd/yyyy) \n"
            + "TIME must be in one of the formats: "
            + "\"7:30am\", \"19:30\"";

    /**
     * Converts given String into a valid Date object
     * 
     * @param dateString    user's input for date
     * @return Date representation converted from given String
     * @throws  IllegalValueException if dateString cannot be converted into a Date object.
     */
    public static Date convertStringToDate(String dateString) throws IllegalValueException {
        assert dateString != null;
        
        if (isValidDate(dateString)) {
            List<DateGroup> dates = new Parser().parse(dateString);
            Date date = dates.get(BASE_INDEX).getDates().get(BASE_INDEX);
            return date;
        } else {
            throw new IllegalValueException(MESSAGE_VALUE_CONSTRAINTS);
        }
    }

    /**
     * Returns true if given string contains date value (e.g, "30th Dec 2015").
     * 
     * @param dateString    user's input for date
     * @return  true if given string contains date value. Else, return false.
     * @throws IllegalValueException    if dateString cannot be converted into a Date object.
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
            throw new IllegalValueException(MESSAGE_VALUE_CONSTRAINTS);
        }
    }
    
    /**
     * Returns true if given string contains time value (e.g, "11:30am").
     * 
     * @param dateString    user's input for date
     * @return  true if given string contains time value. Else, return false.
     * @throws IllegalValueException    if dateString cannot be converted into a Date object.
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
            throw new IllegalValueException(MESSAGE_VALUE_CONSTRAINTS);
        }
    }
    
    /**
     * Sets endDate to have the same date, month and year as the startDate.
     * If upon setting and endDate is of earlier time than startDate, 
     * then set endDate to be startDate + 1.
     * 
     * @param startDate start date of Task
     * @param endDate   end date of Task
     * @return  updated value of endDate
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
     * "5pm tomorrow", "02/10/2016", "13 Sep".
     * 
     * @param dateString    user's input for date
     * @return  true if given String conforms to what was specified in User Guide. Else, return false.
     */
    public static boolean isValidDate(String dateString) {
        assert dateString != null;
        List<DateGroup> dates = new Parser().parse(dateString.trim());
        try {
            int positionOfMatchingValue = dates.get(BASE_INDEX).getPosition();
            String matchingValue = dates.get(BASE_INDEX).getText();
            
            /*if (positionOfMatchingValue > ONE || !matchingValue.equals(dateString)) {
                return false;
            }*/
            
            if (positionOfMatchingValue > ONE) {
                return false;
            }
        } catch (IndexOutOfBoundsException ioobe) {
            return false;
        }
        return true;
    }
    //TODO:
    /**
     * Assigns start date to a specified weekday
     * 
     * @param dateString    user's input for date
     * @throws IllegalValueException 
     */
    public static Date assignStartDateToSpecifiedWeekday(String dateString) throws IllegalValueException {
        assert dateString != null; 
                
        if (dateString.toLowerCase().equals("monday") || dateString.toLowerCase().equals("tuesday") ||
        dateString.toLowerCase().equals("wednesday") || dateString.toLowerCase().equals("thursday") || 
        dateString.toLowerCase().equals("friday") || dateString.toLowerCase().equals("saturday") || 
        dateString.toLowerCase().equals("sunday")) {
        
            List<DateGroup> dates = new Parser().parse(dateString);
            Date date = dates.get(BASE_INDEX).getDates().get(BASE_INDEX);
            date = setTimeToStartOfDay(date);
            date = correctDateIfSameDay(date);
        
            return date;
        } else {
            throw new IllegalValueException(MESSAGE_VALUE_CONSTRAINTS);
        }
    }

    /**
     * Corrects date if it is same day as today. For e.g, if today is Tuesday, and user input "foo repeat every Tuesday",
     * the starting date will be set to next Tuesday which is false. This method corrects the date to today.
     * 
     * @param date  date value of Task
     * @return same date if date not equals today's date. Else, return date minus 7 days
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
     * @param date  date value of Task
     * @return date with time values set to start of the day
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
     * @param date  date value of Task
     * @return date with time values set to end of the day
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
    
    //TODO: Not sure to put this here or where
    /**
     * Updates date according to recurrence rate.
     * 
     * @param date  date value of Task
     * @param recurrenceRate    recurrence rate of Task
     * @return date with updated values according to recurrence rate
     */
    public static Date updateDateByRecurrenceRate(Date date, RecurrenceRate recurrenceRate) {
        assert date != null && recurrenceRate != null;
        
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        
        switch (recurrenceRate.timePeriod) {
        case HOUR:
            calendar.add(Calendar.HOUR_OF_DAY, recurrenceRate.rate);
            break;
        case DAY:
            calendar.add(Calendar.DAY_OF_YEAR, recurrenceRate.rate);
            break;
        case WEEK:
            calendar.add(Calendar.WEEK_OF_YEAR, recurrenceRate.rate);
            break;
        case MONTH:
            calendar.add(Calendar.MONTH, recurrenceRate.rate);
            break;
        case YEAR:
            calendar.add(Calendar.YEAR, recurrenceRate.rate);
            break;
        case MONDAY:
            DateTime.updateCalendarToComingMondays(calendar, recurrenceRate.rate);
            break;
        case TUESDAY:
            DateTime.updateDateToComingTuesdays(calendar, recurrenceRate.rate);
            break;
        case WEDNESDAY:
            DateTime.updateDateToComingWednesdays(calendar, recurrenceRate.rate);
            break;
        case THURSDAY:
            DateTime.updateDateToComingThursdays(calendar, recurrenceRate.rate);
            break;
        case FRIDAY:
            DateTime.updateDateToComingFridays(calendar, recurrenceRate.rate);
            break;
        case SATURDAY:
            DateTime.updateDateToComingSaturdays(calendar, recurrenceRate.rate);
            break;
        case SUNDAY:
            DateTime.updateDateToComingSundays(calendar, recurrenceRate.rate);
            break;
        }
        
        date = calendar.getTime();
        return date;
    }
    
    /**
     * Updates calendar to coming Mondays depending on value of rate.
     * 
     * @param calendar  calendar representation of the date value of Task
     * @param rate  amount of Mondays to jump over
     */
    private static void updateCalendarToComingMondays(Calendar calendar, int rate) {
        assert calendar != null;
        updateDateRateMinusOneTimes(calendar, rate);

        calendar.add(Calendar.DATE, ONE);
        while (calendar.get(Calendar.DAY_OF_WEEK) != Calendar.MONDAY) {
            calendar.add(Calendar.DATE, ONE);
        }
    }

    /**
     * Updates calendar to coming Tuesdays depending on value of rate.
     * 
     * @param calendar  calendar representation of the date value of Task
     * @param rate  amount of Tuesdays to jump over
     */
    private static void updateDateToComingTuesdays(Calendar calendar, int rate) {
        assert calendar != null;
        updateDateRateMinusOneTimes(calendar, rate);
        
        calendar.add(Calendar.DATE, ONE);
        while (calendar.get(Calendar.DAY_OF_WEEK) != Calendar.TUESDAY) {
            calendar.add(Calendar.DATE, ONE);
        }
    }

    /**
     * Updates calendar to coming Wednesdays depending on value of rate.
     * 
     * @param calendar  calendar representation of the date value of Task
     * @param rate  amount of Wednesdays to jump over
     */
    private static void updateDateToComingWednesdays(Calendar calendar, int rate) {
        assert calendar != null;
        updateDateRateMinusOneTimes(calendar, rate);
        
        calendar.add(Calendar.DATE, ONE);
        while (calendar.get(Calendar.DAY_OF_WEEK) != Calendar.WEDNESDAY) {
            calendar.add(Calendar.DATE, ONE);
        }
    }

    /**
     * Updates calendar to coming Thursdays depending on value of rate.
     * 
     * @param calendar  calendar representation of the date value of Task
     * @param rate  amount of Thursdays to jump over
     */
    private static void updateDateToComingThursdays(Calendar calendar, int rate) {
        assert calendar != null;
        updateDateRateMinusOneTimes(calendar, rate);
        
        calendar.add(Calendar.DATE, ONE);
        while (calendar.get(Calendar.DAY_OF_WEEK) != Calendar.THURSDAY) {
            calendar.add(Calendar.DATE, ONE);
        }
    }

    /**
     * Updates calendar to coming Fridays depending on value of rate.
     * 
     * @param calendar  calendar representation of the date value of Task
     * @param rate  amount of Fridays to jump over
     */
    private static void updateDateToComingFridays(Calendar calendar, int rate) {
        assert calendar != null;
        updateDateRateMinusOneTimes(calendar, rate);
        
        calendar.add(Calendar.DATE, ONE);
        while (calendar.get(Calendar.DAY_OF_WEEK) != Calendar.FRIDAY) {
            calendar.add(Calendar.DATE, ONE);
        }
    }

    /**
     * Updates calendar to coming Saturdays depending on value of rate.
     * 
     * @param calendar  calendar representation of the date value of Task
     * @param rate  amount of Saturdays to jump over
     */
    private static void updateDateToComingSaturdays(Calendar calendar, int rate) {
        assert calendar != null;
        updateDateRateMinusOneTimes(calendar, rate);
        
        calendar.add(Calendar.DATE, ONE);
        while (calendar.get(Calendar.DAY_OF_WEEK) != Calendar.SATURDAY) {
            calendar.add(Calendar.DATE, ONE);
        }
    }

    /**
     * Updates calendar to coming Sundays depending on value of rate.
     * 
     * @param calendar  calendar representation of the date value of Task
     * @param rate  amount of Sundays to jump over
     */
    private static void updateDateToComingSundays(Calendar calendar, int rate) {
        assert calendar != null;
        updateDateRateMinusOneTimes(calendar, rate);
        
        calendar.add(Calendar.DATE, ONE);
        while (calendar.get(Calendar.DAY_OF_WEEK) != Calendar.SUNDAY) {
            calendar.add(Calendar.DATE, ONE);
        }
    }
    
    /**
     * Updates calendar by (rate - 1) * 7 days.
     * 
     * @param calendar  calendar representation of the date value of Task
     * @param rate  amount of weeks to jump over
     */
    private static void updateDateRateMinusOneTimes(Calendar calendar, int rate) {
        assert calendar != null;
        if (rate > ONE) {
            calendar.add(Calendar.DATE, (rate - ONE) * NUMBER_OF_DAYS_IN_A_WEEK);
        }
    }
}
