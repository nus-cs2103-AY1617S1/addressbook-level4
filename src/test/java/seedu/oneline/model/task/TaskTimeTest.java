//@@author A0138848M
package seedu.oneline.model.task;

import static org.junit.Assert.*;

import java.util.Calendar;
import java.util.Date;

import seedu.oneline.commons.exceptions.IllegalValueException;

import org.apache.commons.lang.time.DateUtils;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class TaskTimeTest {
    Calendar now;
    Calendar yesterday;
    Calendar tomorrow;
    int thisDay;
    int thisMonth;
    int thisYear;
    
    @Before
    public void setUp(){
        now = Calendar.getInstance();
        yesterday = Calendar.getInstance();
        yesterday.add(Calendar.DATE, -1);
        tomorrow = Calendar.getInstance();
        tomorrow.add(Calendar.DATE, 1);
        thisDay = now.get(Calendar.DAY_OF_MONTH);
        thisMonth = now.get(Calendar.MONTH);
        thisYear = now.get(Calendar.YEAR);
    }
    
    @Rule
    public ExpectedException thrown = ExpectedException.none();
    
    private void IllegalValueExceptionThrown(String inputDateTime, String errorMessage) throws Exception{
        thrown.expect(IllegalValueException.class);
        thrown.expectMessage(errorMessage);    
        new TaskTime(inputDateTime);
    }

    // Tests for invalid datetime inputs to TaskTime constructor
    
    /**
     * Invalid equivalence partitions for datetime: null, other strings
     */
    
    @Test
    public void constructor_nullDateTime_assertionThrown() {
        thrown.expect(AssertionError.class);
        try {
            new TaskTime(null);
        } catch (IllegalValueException e){
            // should throw an assertion error instead of an illegal value exception
            assert false;
        }
    }

    @Test
    public void constructor_unsupportedDateTimeFormats_assertionThrown() throws Exception {
        IllegalValueExceptionThrown("day after", TaskTime.MESSAGE_TASK_TIME_CONSTRAINTS);
        IllegalValueExceptionThrown("clearly not a time format", TaskTime.MESSAGE_TASK_TIME_CONSTRAINTS);
        IllegalValueExceptionThrown("T u e s d a y", TaskTime.MESSAGE_TASK_TIME_CONSTRAINTS);
    }
    
    // Tests for valid datetime inputs
    
    /**
     * Valid equivalence partitions for datetime:
     *  - day month and year specified
     *  - only day and month specified
     *  - only day specified
     *  - relative date specified
     *  - empty string
     */

    // test cases for date, month and year input
    @Test
    public void constructor_emptyDateTime_isAccepted() {
        try {
            TaskTime t = new TaskTime("");
            assert t.getDate() == null;
        } catch (Exception e) {
            assert false;
        }
    }
    
    @Test
    public void constructor_dateMonthYear_supportsDocumentedFormats() {
        String[] validFormats = new String[]{
                "5 October 2016", 
                "5 Oct 16", 
                "Oct 5 16",
                "10/5/16",
                "10/05/16",
                "oCt 5 16"};
        try {
            for (String t : validFormats) {
                TaskTime tTime = new TaskTime(t);
                Calendar tCal = DateUtils.toCalendar(tTime.getDate());
                assertTrue(tCal.get(Calendar.DAY_OF_MONTH) == 5);
                assertTrue(tCal.get(Calendar.MONTH) == Calendar.OCTOBER);
                assertTrue(tCal.get(Calendar.YEAR) == 2016);
            }
        } catch (Exception e) {
            assert false;
        }
    }
    
    // test cases for only date and month input
    /**
     * Boundary cases for fields with only date and month input
     *  - date and month has passed in current year
     *  - date and month has not passed in current year
     *  - date and month is the current day
     */
    
    @Test
    public void constructor_dateMonth_supportsDocumentedFormats() {
        String[] validFormats = new String[]{
                "5 October",
                "5 Oct",
                "10/5",
                "5 ocT"};
        try {
            for (String t : validFormats) {
                TaskTime tTime = new TaskTime(t);
                Calendar tCal = DateUtils.toCalendar(tTime.getDate());
                assertTrue(tCal.get(Calendar.DAY_OF_MONTH) == 5);
                assertTrue(tCal.get(Calendar.MONTH) == Calendar.OCTOBER);
                
                // checks if date refers to previous year if day has passed
                assertTrue(tCal.get(Calendar.YEAR) == thisYear);
            }
        } catch (Exception e) {
            assert false;
        }
    }

    /**
     * Tests whether inputting a date and month that has passed will result
     * in current year's value in task time object
     */
    @Test
    public void constructor_dateMonthPast_setsCorrectYear() {
        // construct a string that represents MM/DD 
        // where MM/DD is the month and date of yesterday
        String yesterdayString = Integer.toString(yesterday.get(Calendar.DAY_OF_MONTH)) 
                + " " + Integer.toString(yesterday.get(Calendar.MONTH));
        try {
            TaskTime tTime = new TaskTime(yesterdayString);
            Calendar tCal = DateUtils.toCalendar(tTime.getDate());
            assertTrue(tCal.get(Calendar.YEAR) == now.get(Calendar.YEAR));
        } catch (Exception e) {
            assert false;
        }
    }
    
    /**
     * Tests whether inputting today's date and month causes 
     * the year stored in TaskTime to remain the same as today's year
     */
    @Test
    public void constructor_dateMonthToday__setsCorrectYear() {
        String todayString = Integer.toString(thisMonth) 
                + " " + Integer.toString(thisDay);
        try {
            TaskTime tTime = new TaskTime(todayString);
            Calendar tCal = DateUtils.toCalendar(tTime.getDate());
            assertTrue(tCal.get(Calendar.YEAR) == thisYear);
        } catch (Exception e) {
            assert false;
        }
    }

    /**
     * Tests whether inputting tomorrow's date and month causes 
     * the year stored in TaskTime to remain the same as tomorrow's year
     */
    @Test
    public void constructor_dateMonthFuture_setsCorrectYear() {
        // construct a string that represents MM/DD 
        // where MM/DD is the month and date of tomorrow
        String tomorrowString = Integer.toString(tomorrow.get(Calendar.DAY_OF_MONTH)) 
                + " " + Integer.toString(tomorrow.get(Calendar.MONTH));
        try {
            TaskTime tTime = new TaskTime(tomorrowString);
            Calendar tCal = DateUtils.toCalendar(tTime.getDate());
            assertTrue(tCal.get(Calendar.YEAR) == tomorrow.get(Calendar.YEAR));
        } catch (Exception e) {
            assert false;
        }
    }
    
    // Tests for day specified only
    /**
     * Boundary cases for fields with only day input
     *  - day has passed from today
     *  - day has not passed from today
     *  - day is today
     */
    
    @Test
    public void constructor_day_supportsDocumentedFormats() {
        String[] validFormats = new String[]{
                "Monday",
                "this mon",
                "MoNday"};
        try {
            for (String t : validFormats) {
                Calendar tCal = new TaskTime(t).getCalendar();
                assertTrue(tCal.get(Calendar.DAY_OF_WEEK) == Calendar.MONDAY);
            }
        } catch (Exception e) {
            assert false;
        }
    }
    

    /**
     * Tests whether inputting a day that has passed will result in 
     * nearest upcoming day being stored in tasktime
     */
    @Test
    public void constructor_day_resultsInNextUpcomingDay() {
        String[] testDays = new String[]{
                "mon",
                "tue",
                "wed",
                "thu",
                "fri",
                "sat",
                "sun"
        };
        try {
            for (String t : testDays) {
                TaskTime tTime = new TaskTime(t);
                Calendar tCal = tTime.getCalendar();
                assertTrue(withinSevenDays(now, tCal));
            }
        } catch (Exception e) {
            assert false;
        }
    }

    /**
     * Returns true if the two dates are between 0 to 6 days apart (both inclusive)
     * @param d1
     * @param d2
     * @return true if difference between the two dates is 7
     */
    private boolean withinSevenDays(Calendar d1, Calendar d2) {
        Calendar nightOfd1 = (Calendar) d1.clone();
        nightOfd1.set(Calendar.HOUR, 23);
        nightOfd1.set(Calendar.MINUTE, 59);
        Calendar sevenDaysAfterd1 = (Calendar) nightOfd1.clone();
        sevenDaysAfterd1.add(Calendar.DAY_OF_MONTH, 8);
        boolean res = (nightOfd1.before(d2) || isSameDay(nightOfd1, d2)) && d2.before(sevenDaysAfterd1);
        return res;
    }
    
    // test cases for relative day entries (next DAY, today, tomorrow)
    @Test
    public void constructor_relativeDay_supportsDocumentedFormats() {
        try {
            Calendar tCal = new TaskTime("next mon").getCalendar();
            assertTrue(tCal.get(Calendar.DAY_OF_WEEK) == Calendar.MONDAY);
        } catch (IllegalValueException e) {
            assert false;
        }
    }
    
    /**
     * Employs exhaustive testing for all valid days to make sure that
     * the day referenced by "next DAY" is 
     * the DAY >= this coming Sunday and <= the nearest Sunday after this coming Sunday
     */
    @Test
    public void constructor_relativeDay_pointsToNearestDayAfterSunday() {
        Calendar thisSunday = (Calendar) now.clone(); // this past sunday (may include today)
        thisSunday.set(Calendar.DAY_OF_WEEK,Calendar.SUNDAY);
        thisSunday.set(Calendar.HOUR_OF_DAY,0);
        thisSunday.set(Calendar.MINUTE,0);
        thisSunday.set(Calendar.SECOND,0);
        thisSunday.add(Calendar.DATE,7);
        
        Calendar nextSunday = (Calendar) thisSunday.clone();
        nextSunday.add(Calendar.DATE,7);
        
        String[] days = new String[]{
            "next mon",
            "next tue",
            "next wed",
            "next thu",
            "next fri",
            "next sat",
            "next sun"
        };
        
        try {
            for (String day : days){
                Calendar tCal = new TaskTime(day).getCalendar();
                assertTrue(tCal.after(thisSunday) || isSameDay(tCal, thisSunday));
                assertTrue(tCal.before(nextSunday));
            }
        } catch (IllegalValueException e) {
            assert false;
        }
    }
    
    // test case for "today" and "tomorrow" inputs
    @Test
    public void constructor_today_supportsDocumentedFormats() {
        try {
            Calendar tCal = new TaskTime("today").getCalendar();
            assertTrue(isSameDay(tCal, now));
        } catch (IllegalValueException e) {
            assert false;
        }
    }

    @Test
    public void constructor_tomorrow_supportsDocumentedFormats() {
        try {
            Calendar tCal = new TaskTime("tomorrow").getCalendar();
            assertTrue(isSameDay(tCal, tomorrow));
        } catch (IllegalValueException e) {
            assert false;
        }
    }

    /**
     * Returns true if d1 and d2 are on the same day
     * @param d1
     * @param d2
     * @return true if d1.day == d2.day and d1.year == d2.year
     * 
     * Conditions: checks only if day and year are the same according to the
     * local system time zone
     */
    private boolean isSameDay(Calendar d1, Calendar d2) {
        return d1.get(Calendar.YEAR) == d2.get(Calendar.YEAR) &&
                d1.get(Calendar.DAY_OF_YEAR) == d2.get(Calendar.DAY_OF_YEAR);
    }
    
    // test if ordering of two task time is supported
    
    /**
     * Equivalent partition for dates comparison:
     *  - d1 < d2
     *  - d1 == d2
     *  - d1 > d2
     */
    
    /**
     * Pre-condition: TaskTime 
     * produces the exact same date when calendar.getTime().toString() is passed
     * as input in the constructor
     */
    @Test
    public void comparable_testCompareDates(){
        try {
            TaskTime past = new TaskTime(yesterday.getTime().toString());
            TaskTime present = new TaskTime(now.getTime().toString());
            TaskTime future = new TaskTime(tomorrow.getTime().toString());
            
            assertTrue(past.compareTo(present) < 0);
            assertTrue(present.compareTo(present) == 0);
            assertTrue(present.compareTo(future) < 0);
        } catch (IllegalValueException e){
            assert false;
        }
    }
    
    @SuppressWarnings("deprecation")
    @Test
    public void constructor_timeInferredResultsIn2349(){
        String[] tests = new String[]{
                "5 October 2016", 
                "5 October",
                "Monday",
                "next Monday",
                "today"
        };
        try {
            for (String test : tests){
                TaskTime t = new TaskTime(test);
                Date tCal = t.getDate();
                assertTrue(tCal.getHours() == 23);
                assertTrue(tCal.getMinutes() == 59);
                assertTrue(tCal.getSeconds() == 59);
            }
        } catch (IllegalValueException e){
            assert false;
        }
    }
}
